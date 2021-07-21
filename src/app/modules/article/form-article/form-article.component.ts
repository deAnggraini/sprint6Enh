import { Component, OnInit, ViewChild, AfterViewInit, OnDestroy, ChangeDetectorRef, TemplateRef } from '@angular/core';
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
  title: '',
  desc: '',
  location: 0,
  locationOption: { id: '', value: '', text: '' },
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
  @ViewChild('formAddEdit') formAddEdit: TemplateRef<any>;

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
  tooltips = TOOL_TIPS;
  isAccEdit: boolean = false;
  accForm: FormGroup;

  constructor(
    private cdr: ChangeDetectorRef,
    private article: ArticleService,
    private skService: SkReferenceService,
    private router: Router,
    private auth: AuthService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private struktur: StrukturService) {
  }

  // node calculation
  private findMaxSort(children: ArticleContentDTO[]): number {
    return Math.max(...children.map(d => d.sort)) | 0;
  }
  private findNode(item) {
    // const { contents } = this.dataForm;
    // contents.
  }

  // Right icon event
  btnAddClick(e, data: ArticleContentDTO) {
    console.log({ e, data });
    const maxSort: number = this.findMaxSort(data.children);
    const listParent: ArticleParentDTO[] = JSON.parse(JSON.stringify(data.listParent));
    if (data.level >= 2) {
      listParent.push({ id: 0, no: data.no, title: data.title });
    }
    const newNode: ArticleContentDTO = {
      id: 0,
      title: 'New Data',
      level: data.level + 1,
      parent: data.id,
      sort: maxSort + 1,
      children: [],
      expanded: true,
      listParent,
      no: `${data.children.length + 1}`
    }
    data.expanded = true;
    data.children.push(newNode);
    e.stopPropagation();
    console.log('new data', { newNode });
    this.cdr.detectChanges();
    return false;
  }
  btnEditClick(e, data: ArticleContentDTO) {
    console.log({ e, data });
    e.stopPropagation();
    return false;
  }
  btnDeleteClick(e, data) {
    console.log({ e, data });
    e.stopPropagation();
    return false;
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
    // console.log({ data });
    const listParent = data.listParent;
    const strNoParent = listParent.map(d => d.no);
    strNoParent.push(data.no);
    return strNoParent.join(".");
  }
  accSaveAddEdit() {

  }

  private getArticle(id: number) {
    this.subscriptions.push(
      this.article.getById(id).subscribe((resp: ArticleDTO) => {
        this.setArticle(resp);
      })
    );
  }
  private setDefaultContentValue(children: ArticleContentDTO[], listParent: ArticleParentDTO[]) {
    if (children && children.length) {
      children.forEach((d, i) => {
        d.expanded = false;
        d.listParent = listParent;
        if (d.level == 1) {
          d.no = '';
          this.setDefaultContentValue(d.children, listParent.concat([]));
        } else {
          d.no = `${i + 1}`;
          const { id, title, no } = d;
          this.setDefaultContentValue(d.children, listParent.concat([{ id, title, no }]));
        }
      });
    }
  }
  private recalculateChildren(children: ArticleContentDTO, listParent: ArticleParentDTO[]) {

  }
  private setArticle(article: ArticleDTO) {
    console.log('setArticle', { article });
    if (article) {
      const locationSelected = this.struktur.findNodeById(article.location);
      if (locationSelected) {
        const { id, title, listParent } = locationSelected;
        const concatParent = listParent.map(d => d.title);
        concatParent.push(locationSelected.title);
        article.locationOption = { id: `${id}`, text: title, value: concatParent.join(' > ') };
      }

      // set expanded default value
      this.setDefaultContentValue(article.contents, []);

      console.log('result', { article });
      this.dataForm.reset(article);
      this.cdr.detectChanges();
    } else {
      this.goBackToAdd('Data article tidak valid');
    }
  }

  private goBackToAdd(msg: string) {
    alert(msg);
    this.router.navigate(['article/add']);
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
      location: [defaultValue.location, Validators.compose([Validators.required])],
      locationOption: [defaultValue.locationOption, Validators.compose([Validators.required])],
      contents: [defaultValue.contents],
      references: [defaultValue.references],
      related: [defaultValue.related],
      suggestions: [defaultValue.suggestions],
    });
    this.accForm = this.fb.group({
      id: [0],
      title: ['', Validators.compose([Validators.required])],
      level: [1, Validators.compose([Validators.required])],
      sort: [1, Validators.compose([Validators.required])],
    });
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
