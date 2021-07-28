import { Component, OnInit, ViewChild, AfterViewInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import * as CustomEditor from './../../../ckeditor/build/ckeditor';
import { ArticleService } from '../../_services/article.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ChangeEvent, CKEditorComponent } from '@ckeditor/ckeditor5-angular';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { BehaviorSubject, Subscription, of } from 'rxjs';
import { Option } from 'src/app/utils/_model/option';
import { SkReferenceService } from '../../_services/sk-reference.service';
import { AuthService, UserModel } from '../../auth';
import { ArticleDTO, ArticleContentDTO, ArticleParentDTO } from '../../_model/article.dto';
import { FormGroup, FormBuilder, Validators, FormControl, ValidationErrors } from '@angular/forms';
import { StrukturService } from '../../_services/struktur.service';
import { catchError, map } from 'rxjs/operators';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';

const TOOL_TIPS = [
  'Berisi aturan/kaidah/ketetapan/syarat/kriteria atas produk/aplikasi yang harus dipahami pembaca sebelum melakukan prosedur atas produk/aplikasi tersebut; dapat dituangkan dalam bentuk kalimat ataupun tabel.',
  'Berisi proses/alur kerja/tahapan/cara kerja yang terkait atas suatu produk/aplikasi, biasanya dijelaskan dalam bentuk diagram alur.',
  'Berisi list-list formulir apa saja yang digunakan atas suatu produk/aplikasi.',
  'Berisi aturan/kaidah/ketetapan/syarat/kriteria atas produk/aplikasi yang harus dipahami pembaca sebelum melakukan prosedur atas produk/aplikasi tersebut; dapat dituangkan dalam bentuk kalimat ataupun tabel.',
  'Berisi proses/alur kerja/tahapan/cara kerja yang terkait atas suatu produk/aplikasi, biasanya dijelaskan dalam bentuk diagram alur.',
  'Berisi list-list formulir apa saja yang digunakan atas suatu produk/aplikasi.'
];

const defaultValue: ArticleDTO = {
  id: 0,
  isEmptyTemplate: false,
  title: '',
  desc: '',
  structureId: 0,
  structureOption: { id: '', value: '', text: '' },
  image: '',
  video: '',
  contents: [],
  references: [],
  related: [],
  suggestions: [],
}

function alphaNumericValidator(control: FormControl): ValidationErrors | null {
  const ALPHA_NUMERIC_REGEX = /^(?:[a-zA-Z0-9\s\-\/]+)?$/;
  return ALPHA_NUMERIC_REGEX.test(control.value) ? null : { alphaNumericError: 'Hanya angka dan huruf yang diperbolehkan' };
}

@Component({
  selector: 'app-form-article',
  templateUrl: './form-article.component.html',
  styleUrls: ['./form-article.component.scss']
})
export class FormArticleComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('editorDesc') editorComponent: CKEditorComponent;

  subscriptions: Subscription[] = [];

  editor = CustomEditor;
  config = {
    toolbar: {
      items: [
        'fontFamily',
        'fontSize',
        'fontColor',
        'fontBackgroundColor',
        'heading',
        '|',
        'undo',
        'redo',
        '|',
        'bold',
        'italic',
        'underline',
        'strikethrough',
        'alignment',
        'subscript',
        'superscript',
        '|',
        'bulletedList',
        'numberedList',
        'todoList',
        '|',
        'outdent',
        'indent',
        'findAndReplace',
        '|',
        'link',
        'imageUpload',
        'blockQuote',
        'insertTable',
        'mediaEmbed',
        '|',
        'code',
        'codeBlock',
        'htmlEmbed',
        'specialCharacters',
      ],
      shouldNotGroupWhenFull: true
    },
    language: 'en',
    image: {
      toolbar: [
        'imageTextAlternative',
        'imageStyle:full',
        'imageStyle:side',
        'linkImage'
      ]
    },
    table: {
      contentToolbar: [
        'tableColumn',
        'tableRow',
        'mergeTableCells',
        'tableCellProperties',
        'tableProperties'
      ]
    },
    fontFamily: {
      options: ['Calibri, sans-serif', 'Arial, Helvetica, sans-serif', 'Segoe UI, Open Sans'],
      supportAllValues: false
    },
    fontSize: {
      options: [11, 13, 16, 18],
      supportAllValues: false
    },
    placeholder: `Berisi penjelasan singkat tentang produk/aplikasi, <br>dapat berupa definisi atas produk/aplikasi tersebut.\r\n
    \r\n
    Contoh : \r\n
    Time Loan - SME merupakan salah satu produk kredit produktif untuk modal kerja kepada debitur segmen Small dan Medium Enterprises (SME) dalam mata uang rupiah ataupun valas yang penarikannya menggunakan Surat Permohonan Penarikan Fasilitas Kredit/Perpanjangan Pembayaran untuk jangka waktu tertentu.`
  };

  logs: Map<number, ArticleContentDTO[]> = new Map();
  hasError: boolean = false;
  errorMsg: string = '';
  user: UserModel;
  isEdit: boolean = false;
  dataForm: FormGroup;
  // dataForm: ArticleDTO;
  finishRender: boolean = false;
  skReferences: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  relatedArticle$: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  suggestionArticle$: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  locationOptions: BehaviorSubject<Option[]> = new BehaviorSubject([]);

  // accordion
  selectedAccordion: ArticleContentDTO;
  tooltips = TOOL_TIPS;
  isAccEdit: boolean = false;
  // accForm: FormGroup;

  //cdk drag and drop
  allIds: Array<string> = []
  public get allIdConnected(): Array<string> {
    return this.allIds
  }

  constructor(
    private cdr: ChangeDetectorRef,
    private article: ArticleService,
    private skService: SkReferenceService,
    private router: Router,
    private auth: AuthService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private struktur: StrukturService,
    private confirm: ConfirmService) {
  }

  //cdk drag and drop
  dropListsId(item: { id: number, no: string }[]): string {
    const id = item[item.length - 1].id.toString()
    this.allIds.some(x => x === id) ? '' : this.allIds.push(id)
    return id
  }
  getMaxLevel(dropList: ArticleContentDTO[]) {
    return dropList.reduce((depth, child) => {
      return Math.max(depth, 1 + this.getMaxLevel(child.children));
    }, 0);
  }

  // node calculation
  private findMaxSort(children: ArticleContentDTO[]): number {
    return Math.max(...children.map(d => d.sort)) | 0;
  }
  private findNode(id, dataList: ArticleContentDTO[]): ArticleContentDTO {
    let found = dataList.find(d => d.id == id);
    if (!found) {
      dataList.forEach(d => {
        if (found) return;
        found = this.findNode(id, d.children);
      });
    }
    return found;
  }

  // article action button
  private parseToSingleArray() {

  }
  private parseToFormObject(data) {
    let formData = new FormData();
    for (let key in data) {
      if (Array.isArray(data[key])) {
        data[key].forEach((obj, index) => {
          let keyList = Object.keys(obj);
          keyList.forEach((keyItem) => {
            let keyName = [key, "[", index, "]", ".", keyItem].join("");
            formData.append(keyName, obj[keyItem]);
          });
        });
      } else if (typeof data[key] === "object") {
        for (let innerKey in data[key]) {
          formData.append(`${key}.${innerKey}`, data[key][innerKey]);
        }
      } else {
        formData.append(key, data[key]);
      }
    }
    return formData;
  }
  onSave(e) {
    this.confirm.open({
      title: `Simpan`,
      message: `<p>Apakah Kamu yakin ingin menyimpan halaman ini? Halaman akan tersimpan kedalam draft Kamu`,
      btnOkText: 'Simpan',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        // this.subscriptions.push(
        //   this.article.deleteContent(data.id).subscribe(resp => {
        //     if (resp) this.deleteNode(data);
        //   })
        // );
      }
    });
    return false;
  }
  onCancel(e) {
    console.log(this.dataForm.valid, this.dataForm.value);
    console.log(this.dataForm.value.contents);
    console.log(this.logs);
    this.confirm.open({
      title: `Batal Tambah Artikel`,
      message: `<p>Apakah Kamu yakin ingin keluar dan membatalkan membuat artikel baru?`,
      btnOkText: 'Ya, Batal Tambah',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.subscriptions.push(
          this.article.cancelArticle(this.dataForm.value.id).subscribe(resp => {
            if (resp) {
              this.article.formParam = null;
              this.article.formData = null;
              this.router.navigate(['/homepage']);
            }
          })
        );
      }
    });
    return false;
  }
  onSaveAndSend(e) {

  }
  onPreview(e) {

  }

  // Right icon event
  btnAddClick(e, data: ArticleContentDTO) {
    this.subscriptions.push(
      this.article.getContentId().subscribe(resp => {
        if (resp) {
          if (data == null) { // craete new level 1
            const _contents = this.dataForm.get('contents').value as ArticleContentDTO[];
            const maxSort: number = this.findMaxSort(_contents);
            const newNode: ArticleContentDTO = {
              id: resp,
              title: '',
              intro: '',
              topicContent: '',
              topicTitle: '',
              level: 1,
              parent: 0,
              sort: maxSort + 1,
              children: [],
              expanded: true,
              listParent: [],
              no: '',
              isEdit: false,
            }
            _contents.push(newNode);
            this.addLog(newNode);
          } else { // add childe level > 1
            const maxSort: number = this.findMaxSort(data.children);
            const listParent: ArticleParentDTO[] = JSON.parse(JSON.stringify(data.listParent));
            if (data.level >= 2) {
              listParent.push({ id: 0, no: data.no, title: data.title });
            }
            const newNode: ArticleContentDTO = {
              id: resp,
              title: '',
              intro: '',
              topicContent: '',
              topicTitle: '',
              level: data.level + 1,
              parent: data.id,
              sort: maxSort + 1,
              children: [],
              expanded: true,
              listParent,
              no: `${data.children.length + 1}`,
              isEdit: false,
            }
            data.expanded = true;
            data.children.push(newNode);
            this.addLog(newNode);
          }
          this.cdr.detectChanges();
        }
      })
    );
    e.stopPropagation();
    return false;
  }
  btnEditClick(e, data: ArticleContentDTO) {
    data.isEdit = !data.isEdit;
    this.cdr.detectChanges();
    e.stopPropagation();
    return false;
  }
  btnDeleteClick(e, data: ArticleContentDTO) {
    this.confirm.open({
      title: `Hapus Topik`,
      message: `<p>Apakah Kamu yakin ingin menghapus topik “<b>${data.title}</b>”?`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.subscriptions.push(
          this.article.deleteContent(data.id).subscribe(resp => {
            this.deleteNode(data);
          })
        );
      }
    });
    e.stopPropagation();
    return false;
  }
  private deleteNode(data: ArticleContentDTO) {
    let _contents: ArticleContentDTO[] = JSON.parse(JSON.stringify(this.dataForm.get('contents').value));
    const _parent = this.findNode(data.parent, _contents);
    if (data.level > 1) {
      _parent.children = _parent.children.filter(d => d.id != data.id);
    } else {
      _contents = _contents.filter(d => d.id != data.id);
    }
    this.recalculateChildren(_contents, []);
    this.dataForm.get('contents').setValue(_contents);
    this.removeLog(data);
    this.cdr.detectChanges();
  }

  checkUniq(value) {
    const checkUniqSubrcr = this.article.checkUniq(value.trim())
      .pipe(
        catchError((err) => {
          this.hasError = true;
          this.errorMsg = JSON.parse(err.message).message;
          return of(null);
        }),
        map(resp => resp),
      )
      .subscribe(
        resp => {
          if (resp === false) {
            this.hasError = false;
            this.errorMsg = '';
          }
          this.cdr.detectChanges();
        }
      );
    this.subscriptions.push(checkUniqSubrcr);
  }

  drop(event: CdkDragDrop<any[]>) {
    console.log({ event });
    const level = event.container.data[0].level
    if (this.getMaxLevel(event.container.data) + (event.container.data[0].level - 1) + (this.getMaxLevel([event.previousContainer.data[event.previousIndex]]) - 1) > 5) {
      return
    }
    if (event.previousContainer === event.container) {
      console.log('move dalam satu list');
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      console.log('pindah list');
      console.log('dari', event.previousContainer.data);
      console.log('ke', event.container.data);
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
    this.recalculateChildren(event.previousContainer.data, event.previousContainer.data.length > 0 ? event.previousContainer.data[0].listParent : []);
    this.recalculateChildren(event.container.data, event.container.data.filter((x, i) => i !== event.currentIndex)[0].listParent);
    this.recalculateLevelChildren(event.container.data, level)
    console.log(level)
  }

  onHidden(panelId) {
    const header = document.getElementById(panelId + "-header");
    if (header) {
      header.classList.remove('panel-open');
    }
  }

  onShow(panelId) {
    const header = document.getElementById(panelId + "-header");
    if (header) {
      header.classList.add('panel-open');
    }
  }

  // accordion event
  numberingFormat(data: ArticleContentDTO): string {
    const listParent = data.listParent;
    const strNoParent = listParent.map(d => d.no);
    strNoParent.push(data.no);
    return strNoParent.join(".");
  }
  accSaveAddEdit(content: ArticleContentDTO) {
    this.subscriptions.push(
      this.article.saveContent(content).subscribe(resp => {
        if (resp) {
          this.addLog(content);
        }
      })
    );
  }
  accCancel(content: ArticleContentDTO) {
    if (this.logs.has(content.id)) {
      const _values: ArticleContentDTO[] = this.logs.get(content.id);
      const _value = _values[_values.length - 1];
      content.title = _value.title;
      content.topicTitle = _value.topicTitle;
      content.topicContent = _value.topicContent;
    } else {
      content.title = '';
      content.topicTitle = '';
      content.topicContent = '';
    }
    this.cdr.detectChanges();
  }

  private getArticle(id: number) {
    this.subscriptions.push(
      this.article.getById(id).subscribe((resp: ArticleDTO) => {
        console.log({ resp });
        this.setArticle(resp);
      })
    );
  }
  private recalculateChildren(children: ArticleContentDTO[], listParent: ArticleParentDTO[]) {
    if (children && children.length) {
      children.forEach((d, i) => {
        if (!d.expanded) d.expanded = false;
        if (!d.isEdit) d.isEdit = false;
        d.listParent = listParent;
        if (d.level == 1) {
          d.no = '';
          d.sort = 0;
          this.recalculateChildren(d.children, listParent.concat([]));
        } else {
          d.no = `${i + 1}`;
          d.sort = i + 1;
          const { id, title, no } = d;
          this.recalculateChildren(d.children, listParent.concat([{ id, title, no }]));
        }
      });
    }
  }
  private recalculateLevelChildren(children: ArticleContentDTO[], level: number) {
    if (children && children.length) {
      children.forEach((d, i) => {
        d.level = level
        this.recalculateLevelChildren(d.children, level + 1)
      });
    }
  }
  private setArticle(article: ArticleDTO) {
    console.log({ article });
    if (article) {
      const { structureParentList, structureId } = article;
      article.structureOption = {
        id: `${structureId}`,
        text: structureParentList[structureParentList.length - 1].title,
        value: structureParentList.map(d => d.title).join(' > ')
      };

      // set expanded default value
      this.recalculateChildren(article.contents, []);

      this.dataForm.reset(article);
      this.addLogs(article.contents);
      this.cdr.detectChanges();
    } else {
      this.goBackToAdd('Data article tidak valid');
    }
  }
  private addLogs(values: ArticleContentDTO[] = null) {
    if (values == null) return;
    values.forEach(d => {
      this.addLog(d);
      this.addLogs(d.children);
    });
  }
  private addLog(v: ArticleContentDTO) {
    if (v) { // add all
      const { id } = v;
      const _newData: ArticleContentDTO = Object.assign({}, v, {
        children: [], listParent: v.listParent
      });
      if (this.logs.has(id)) {
        this.logs.get(id).push(_newData);
      } else {
        this.logs.set(id, [_newData]);
      }
    }
  }
  private removeLog(value: ArticleContentDTO) {
  }
  onImageChange(e) {
    if (e.target.files && e.target.files.length) {
      const [file] = e.target.files;
      // const { size, name, type } = file;
      this.dataForm.patchValue({
        image: file
      });

      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function (e) {
        var image = new Image();
        image.src = e.target.result as string;
        image.onload = function (_) {
          var height = image.height;
          var width = image.width;
          console.log({ width, height }, this);
          // if ((height >= 1024 || height <= 1100) && (width >= 750 || width <= 800)) {
          //   alert("Height and Width must not exceed 1100*800.");
          //   return false;
          // }
          // alert("Uploaded image has valid Height and Width.");
          return true;
        };
      }
    };
  }
  showImageName() {
    if (typeof (this.dataForm.value.image) === "string") {
      return this.dataForm.value.image;
    } else {
      return this.dataForm.value.image?.name;
    }
  }

  private goBackToAdd(msg: string) {
    alert(msg);
    // this.router.navigate(['article/add']);
  }

  // SK SE Event
  private convertSkToOption(dataList: any[]): Option[] {
    const result = [];
    if (dataList && dataList.length) {
      dataList.forEach(d => {
        const { id, title, no } = d;
        result.push({ id, value: no, text: `${no} - ${title}` })
      })
    }
    return result;
  }
  getDataSkSeReference(keyword: string) {
    this.subscriptions.push(
      this.skService.search(keyword).subscribe(resp => {
        if (resp) {
          this.skReferences.next(this.convertSkToOption(resp));
        }
      })
    );
  }

  // Related Article Event
  private convertArticleToOption(dataList: any[]): Option[] {
    const result = [];
    if (dataList && dataList.length) {
      dataList.forEach(d => {
        const { id, title } = d;
        result.push({ id, value: id, text: title })
      })
    }
    return result;
  }
  getDataRelatedArticle(keyword: string) {
    this.subscriptions.push(
      this.article.searchArticle(keyword).subscribe(resp => {
        if (resp) this.relatedArticle$.next(this.convertArticleToOption(resp));
      })
    );
  }
  getDataSuggestionArticle(keyword: string) {
    this.subscriptions.push(
      this.article.searchArticle(keyword).subscribe(resp => {
        if (resp) this.suggestionArticle$.next(this.convertArticleToOption(resp));
      })
    );
  }

  // CKEDITOR5 function
  public onReady(editor) {
    this.finishRender = true;
    console.log('ckeditor onReady');
  }
  public onChange({ editor }: ChangeEvent) {
    console.log('onChange', { editor });
  }

  // Angular
  private initForm() {
    this.dataForm = this.fb.group({
      id: [0],
      title: [defaultValue.title, Validators.compose([Validators.required, Validators.maxLength(50), alphaNumericValidator])],
      structureId: [defaultValue.structureId, Validators.compose([Validators.required])],
      structureOption: [defaultValue.structureOption, Validators.compose([Validators.required])],
      image: [defaultValue.image],
      video: [defaultValue.video],
      contents: [defaultValue.contents],
      references: [defaultValue.references],
      related: [defaultValue.related],
      suggestions: [defaultValue.suggestions],
      isEmptyTemplate: [defaultValue.isEmptyTemplate, Validators.compose([Validators.required])],
    });
    // this.accForm = this.fb.group({
    //   articleId: [0, Validators.compose([Validators.required])],
    //   id: [0, Validators.compose([Validators.required])],
    //   title: ['', Validators.compose([Validators.required])],
    //   level: [1, Validators.compose([Validators.required])],
    //   sort: [1, Validators.compose([Validators.required])],
    // });
  }
  ngOnInit(): void {
    this.initForm();
    if (this.article.formData == null) {
      this.subscriptions.push(
        this.route.params.subscribe(params => {
          if (params['id']) {
            this.getArticle(parseInt(params['id']));
          } else {
            this.goBackToAdd('Silahkan tambah artikel terlebih dahulu');
          }
        })
      );
    } else {
      this.setArticle(this.article.formData);
    }
    this.subscriptions.push(
      this.auth.currentUserSubject.subscribe((resp: UserModel) => {
        this.user = resp;
      })
    );
    this.subscriptions.push(
      this.struktur.categories$.subscribe(resp => {
        if (resp) {
          const locationOptions = this.struktur.parseToOptions(resp);
          this.locationOptions.next(locationOptions);
          this.cdr.detectChanges();
        }
      })
    );
  }
  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }
  ngAfterViewInit(): void {
    this.finishRender = true;
  }

}
