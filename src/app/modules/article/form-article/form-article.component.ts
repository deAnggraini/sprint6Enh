import { Component, OnInit, ViewChild, AfterViewInit, OnDestroy, ChangeDetectorRef, TemplateRef, Inject } from '@angular/core';
import * as CustomEditor from './../../../ckeditor/build/ckeditor';
import { ArticleService } from '../../_services/article.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ChangeEvent, CKEditorComponent } from '@ckeditor/ckeditor5-angular';
import { CdkDragDrop, CdkDragEnter, CdkDragExit, CdkDragMove, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { BehaviorSubject, Subscription, of, Observable, Subject } from 'rxjs';
import { Option } from 'src/app/utils/_model/option';
import { SkReferenceService } from '../../_services/sk-reference.service';
import { AuthService, UserModel } from '../../auth';
import { ArticleDTO, ArticleContentDTO, ArticleParentDTO } from '../../_model/article.dto';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { StrukturService } from '../../_services/struktur.service';
import { catchError, debounceTime, distinctUntilChanged, elementAt, map, tap } from 'rxjs/operators';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { ToastService } from 'src/app/utils/_services/toast.service';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../_services/user.service';
import { DOCUMENT } from '@angular/common';

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
  isHasSend: false,
}

// function alphaNumericValidator(control: FormControl): ValidationErrors | null {
//   const ALPHA_NUMERIC_REGEX = /^(?:[a-zA-Z0-9\s\-\/]+)?$/;
//   return ALPHA_NUMERIC_REGEX.test(control.value) ? null : { alphaNumericError: 'Hanya angka dan huruf yang diperbolehkan' };
// }

@Component({
  selector: 'app-form-article',
  templateUrl: './form-article.component.html',
  styleUrls: ['./form-article.component.scss']
})
export class FormArticleComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('editorDesc') editorComponent: CKEditorComponent;
  @ViewChild('formSaveAndSend') formSaveAndSend: TemplateRef<any>;

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
    wordCount: { maxLimit: 1000 }
  };

  configTopic = {
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
    placeholder: 'Masukkan kalimat pengantar terkait Ketentuan disini.'
  };

  // error manual
  hasError: boolean = false;
  errorMsg: string = '';
  hasImageError: boolean = false;
  errorImageMsg: string = '';

  logs: Map<number, ArticleContentDTO[]> = new Map();
  user: UserModel;
  isEdit: boolean = false;
  dataForm: FormGroup;
  finishRender: boolean = false;
  skReferences: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  relatedArticle$: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  suggestionArticle$: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  locationOptions: BehaviorSubject<Option[]> = new BehaviorSubject([]);

  // preview property
  isPreview: boolean = false;
  previewHideTopbar: boolean = false;
  previewAlert: boolean = false;
  previewAlertMessage: string = '';

  // save and send
  userOptions: Option[] = [];
  isHasSend: boolean = false;
  saveAndSend = {
    sendTo: {
      username: '',
      email: '',
    },
    sendNote: ''
  }

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
  dragMovedSubject = new Subject<any>();
  idAccordionSelected: string
  fakeDragAndDropStatus: boolean = false

  constructor(
    private cdr: ChangeDetectorRef,
    private article: ArticleService,
    private skService: SkReferenceService,
    private router: Router,
    private auth: AuthService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private struktur: StrukturService,
    private confirm: ConfirmService,
    private toast: ToastService,
    private modalService: NgbModal,
    private configModel: NgbModalConfig,
    private userService: UserService,
    @Inject(DOCUMENT) private document: Document
  ) {
    this.configModel.backdrop = 'static';
    this.configModel.keyboard = false;
  }

  // validation
  isContentsPass(contents: ArticleContentDTO[]): boolean {
    let result: boolean = true;
    contents.forEach(d => {
      if (!result) return;
      // if (d.level > 1) {
      if (!d.title) result = false;
      if (!d.topicTitle) result = false;
      if (!d.topicContent) result = false;
      // } else {
      // if (!d.intro) result = false;
      // }
      if (d.children && d.children.length && result)
        result = this.isContentsPass(d.children);
    });
    return result;
  }
  isAllPass(): boolean {
    const _dataForm: ArticleDTO = this.dataForm.value;
    if (!(this.dataForm.valid && this.hasError === false && this.hasImageError === false)) return false;
    if (!_dataForm.desc) return false;
    if (!_dataForm.image) return false;
    const contents = _dataForm.contents;
    const len = contents.length;
    if (len < 1) {
      return false;
    }
    const result = this.isContentsPass(contents);
    return result;
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
  onSave(e) {
    console.log(this.dataForm.value);
    this.confirm.open({
      title: `Simpan`,
      message: `<p>Apakah Kamu yakin ingin menyimpan halaman ini? Halaman akan tersimpan kedalam draft Kamu`,
      btnOkText: 'Simpan',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        const _dataForm = this.dataForm.value;
        // const formData = new FormData(this.formArticle.nativeElement);
        this.subscriptions.push(
          this.article.saveArticle(_dataForm).subscribe(resp => {
            if (resp) {
              this.article.formData = _dataForm;
              this.article.formAlert = 'Artikel berhasil disimpan ke dalam draft.';
              this.router.navigate(
                [
                  `/article/preview/${_dataForm.id}`,
                ], { replaceUrl: true });
            }
          })
        );
      }
    });
    return false;
  }
  onCancel(e) {
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
              this.router.navigate(['/homepage'], { replaceUrl: true });
            }
          })
        );
      }
    });
    return false;
  }
  onSaveAndSend(e) {
    // reset form
    this.saveAndSend.sendTo.username = '';
    this.saveAndSend.sendTo.email = '';
    this.saveAndSend.sendNote = '';

    this.modalService.open(this.formSaveAndSend);
    return false;
  }
  doSaveAndSend(e) {
    const _dataForm = this.dataForm.value;
    this.subscriptions.push(
      this.article.saveArticle(_dataForm, true, this.saveAndSend).subscribe(resp => {
        if (resp) {
          this.modalService.dismissAll();
          // this.onPreview(true, true, true, 'Artikel berhasil disimpan dan dikirim.');
          // this.cdr.detectChanges();
          this.article.formData = _dataForm;
          this.article.formAlert = 'Artikel berhasil disimpan dan dikirim.';
          this.router.navigate(
            [
              `/article/preview/${_dataForm.id}`,
            ], { replaceUrl: true });
        }
      })
    );
    return false;
  }
  onPreview(show: boolean, hideTopbar: boolean = false, alert: boolean = false, msg: string = '') {
    this.article.formData = this.dataForm.value as ArticleDTO;
    this.previewHideTopbar = hideTopbar;
    this.previewAlert = alert;
    this.previewAlertMessage = msg;
    this.isPreview = show;
    return false;
  }

  // Right icon event
  btnAddClick(e, data: ArticleContentDTO) {
    this.subscriptions.push(
      this.article.getContentId().subscribe(resp => {
        if (resp) {
          const _contents = this.dataForm.get('contents').value as ArticleContentDTO[];
          let maxSort: number = 0;
          let listParent: ArticleParentDTO[] = [];
          let level: number = 1;
          let parent: number = 0;
          let no: string = '';
          if (data == null) {
            maxSort = this.findMaxSort(_contents);
          } else {
            maxSort = this.findMaxSort(data.children);
            level = data.level + 1;
            listParent = JSON.parse(JSON.stringify(data.listParent));
            if (data.level >= 2) {
              listParent.push({ id: 0, no: data.no, title: data.title });
            }
            parent = data.id;
            no = `${data.children.length + 1}`;
          }
          const newNode: ArticleContentDTO = {
            id: resp,
            articleId: this.dataForm.value.id,
            title: '',
            intro: '',
            topicContent: '',
            topicTitle: '',
            level,
            parent,
            sort: maxSort + 1,
            children: [],
            expanded: true,
            listParent,
            no,
            isEdit: true,
            isNew: true,
          }
          if (data) {
            data.expanded = true;
            data.children.push(newNode);
          } else {
            _contents.push(newNode);
          }
          this.addLog(newNode);
          this.cdr.detectChanges();
        }
      })
    );
    e.stopPropagation();
    return false;
  }
  btnEditClick(e, data: ArticleContentDTO) {
    data.isEdit = true;
    data.expanded = true;
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
            // this.toast.showSuccess('Hapus Data Accordion Berhasil');
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
    this.hasError = false;
    if (!value) return;
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

  drop(event: CdkDragDrop<any[]>, levelParent: number, parent) {
    const level = event.container.data.length === 0 ? levelParent : event.container.data[0].level
    console.log(level, this.getMaxLevel(event.item.data.children))
    console.log({ event });
    this.clearFakePlaceholder()
    if (this.fakeDragAndDropStatus) {
      this.addArray(this.dataForm.controls.contents.value, this.idAccordionSelected, event.item.data, level, parent)
      transferArrayItem(event.previousContainer.data, [], event.previousIndex, 0)
      this.recalculateChildren(event.previousContainer.data, event.previousContainer.data.length > 0 ? event.previousContainer.data[0].listParent : [])
      this.fakeDragAndDropStatus = false
      return
    }
    if (level + this.getMaxLevel(event.item.data.children) > 5) {
      return
    }
    if (event.previousContainer === event.container) {
      console.log('move dalam satu list');
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      console.log('pindah list');
      console.log('dari', event.previousContainer.data, event.previousIndex);
      console.log('ke', event.container.data, event.currentIndex);
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
    this.recalculateChildren(event.previousContainer.data, event.previousContainer.data.length > 0 ? event.previousContainer.data[0].listParent : []);
    this.recalculateChildren(event.container.data, event.container.data.filter((x, i) => i !== event.currentIndex)[0] === undefined ? parent : event.container.data.filter((x, i) => i !== event.currentIndex)[0].listParent);
    this.recalculateLevelChildren(event.container.data, level)
  }

  dragMoved(event) {
    let e = this.document.elementFromPoint(event.pointerPosition.x, event.pointerPosition.y);
    console.log('e', e)
    if (!e) {
      this.clearFakePlaceholder()
      this.fakeDragAndDropStatus = false
      return
    }
    let container = e.classList.contains("drop-area-accordion") ? e : e.closest(".drop-area-accordion");
    console.log('container', container)
    if (!container) {
      this.clearFakePlaceholder()
      this.fakeDragAndDropStatus = false
      return
    }
    const id = container.getAttribute("data-id")
    if (!id) {
      this.clearFakePlaceholder()
      this.fakeDragAndDropStatus = false
      return
    }
    this.fakeDragAndDropStatus = true
    this.document.getElementById('plc' + id).classList.add('fake-drag-placeholder-show')
    this.document.querySelectorAll('.drag-placeholder').forEach(element => element.classList.add('drag-placeholder-hidden'))
    this.idAccordionSelected = id
  }

  addArray(arr, id, value, level, parent) {
    arr.forEach(element => {
      if (element.id.toString() === id) {
        element.children.push(value)
        console.log(element.children)
        this.recalculateChildren(element.children, parent)
        this.recalculateLevelChildren(element.children, level)
        return
      }
      if (element.children.length > 0) {
        this.addArray(element.children, id, value, level, parent)
      }
    });
  }

  clearFakePlaceholder() {
    this.document.querySelectorAll('.fake-drag-placeholder').forEach(element => element.classList.remove('fake-drag-placeholder-show'))
    this.document.querySelectorAll('.drag-placeholder').forEach(element => element.classList.remove('drag-placeholder-hidden'))
  }

  dragMovedDebounce(event) {
    this.dragMovedSubject.next(event)
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

  // User saerch
  searchUser(keyword) {
    if (keyword) {
      this.subscriptions.push(
        this.userService.searchUserNotReader(keyword).subscribe(resp => {
          if (resp) {
            this.userOptions = this.userService.usersToOptions(resp);
          }
        })
      );
    } else {
      this.userOptions = [];
    }
  }
  userChange(item: Option) {
    this.saveAndSend.sendTo.email = item.value;
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
          content.isEdit = false;
          content.isNew = false;
          // this.toast.showSuccess('Simpan Data Accordion Berhasil');
          this.addLog(content);
        }
        this.cdr.detectChanges();
      })
    );
  }
  accCancel(content: ArticleContentDTO) {
    if (content.isNew) {
      // data baru belum pernah simpan, langsung hapus
      this.deleteNode(content);
    } else
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
    content.isEdit = false;
    this.cdr.detectChanges();
  }

  private getArticle(id: number, isEdit: boolean) {
    this.subscriptions.push(
      this.article.getById(id, isEdit).subscribe((resp: ArticleDTO) => {
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
          d.sort = i + 1;
          this.recalculateChildren(d.children, listParent.concat([]));
        } else {
          d.no = `${i + 1}`;
          d.sort = i + 1;
          const { id, title, no } = d;
          this.recalculateChildren(d.children, listParent.concat([{ id, title, no }]));
        }
        if (!d.topicTitle && d.title) d.topicTitle = d.title;
        if (!d.topicContent && d.intro) d.topicContent = d.intro;
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
    if (article) {
      console.log('setArticle', { article });
      const { structureParentList, structureId } = article;
      article.structureOption = {
        id: `${structureId}`,
        text: structureParentList[structureParentList.length - 1].title,
        value: structureParentList.map(d => d.title).join(' > ')
      };

      // set expanded default value
      this.recalculateChildren(article.contents, []);

      // parsing referances to options
      if (article.references && article.references.length) {
        article.references.forEach(d => {
          d.value = d.no;
          d.text = `${d.no} - ${d.title}`;
        })
      }

      // parsing related article to options
      if (article.related && article.related.length) {
        article.related.forEach(d => {
          d.value = `${d.id}`;
          d.text = `${d.title}`;
        })
      }

      // parsing suggestions to options
      if (article.suggestions && article.suggestions.length) {
        article.suggestions.forEach(d => {
          d.value = `${d.id}`;
          d.text = `${d.title}`;
        })
      }

      this.isEdit = !article.isNew;
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
      this.dataForm.patchValue({
        image: file
      });
      const $this = this;

      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function (e) {
        var image = new Image();
        image.src = e.target.result as string;
        image.onload = function (_) {
          const { width, height } = image;
          const { size } = file;
          console.log({ width, height, size });
          if (width < 307 || height < 425) {
            $this.hasImageError = true;
            $this.errorImageMsg = 'Resolusi gambar minimal 307x425 px';
            $this.cdr.detectChanges();
            return false;
          }
          if (size > 700000) {
            $this.hasImageError = true;
            $this.errorImageMsg = 'Ukuran gambar maksimal 700kb';
            $this.cdr.detectChanges();
            return false;
          }

          $this.hasImageError = false;
          $this.errorImageMsg = '';
          $this.cdr.detectChanges();
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
  changeLocation(value) {
    if (value) this.dataForm.get('structureId').setValue(value.id);
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
      this.article.searchArticle(keyword, this.dataForm.value.id).subscribe(resp => {
        if (resp) this.relatedArticle$.next(this.convertArticleToOption(resp.list));
      })
    );
  }
  getDataSuggestionArticle(keyword: string) {
    const exclude = [this.dataForm.value.id].concat(this.dataForm.value.suggestions.map(d => d.id));
    const structureId = this.dataForm.value.structureId;
    this.subscriptions.push(
      this.article.suggestionArticle(keyword, structureId, exclude).subscribe(resp => {
        if (resp) this.suggestionArticle$.next(this.convertArticleToOption(resp.list));
      })
    );
  }
  onSelectSuggestionArticle(arg: { item: any, keyword: string }) {
    // console.log('onSelectSuggestionArticle', { arg });
    const { keyword, item } = arg;
    const exclude = [this.dataForm.value.id].concat(this.dataForm.value.suggestions.map(d => d.id)).concat([item.id]);
    const structureId = this.dataForm.value.structureId;
    this.subscriptions.push(
      this.article.suggestionArticle(keyword, structureId, exclude).subscribe(resp => {
        if (resp) this.suggestionArticle$.next(this.convertArticleToOption(resp.list));
      })
    );
  }

  // CKEDITOR5 function
  public onReady(editor, value: string = '') {
    if (value) editor.setData(value); // cara paksa isi ckeditor
    this.finishRender = true;
  }
  public onChange({ editor }: ChangeEvent) {
  }

  // Angular
  private initForm() {
    this.dataForm = this.fb.group({
      id: [0],
      title: [defaultValue.title, Validators.compose([Validators.required, Validators.maxLength(50)])],
      structureId: [defaultValue.structureId, Validators.compose([Validators.required])],
      structureOption: [defaultValue.structureOption, Validators.compose([Validators.required])],
      desc: [defaultValue.desc],
      image: [defaultValue.image],
      video: [defaultValue.video],
      contents: [defaultValue.contents],
      // contents: this.fb.array([
      // this.fb.group({
      //   articleId: [0, Validators.compose([Validators.required])],
      //   id: [0, Validators.compose([Validators.required])],
      //   title: ['', Validators.compose([Validators.required])],
      //   topicTitle: ['', Validators.compose([Validators.required])],
      //   topicContent: ['', Validators.compose([Validators.required])],
      //   level: [1, Validators.compose([Validators.required])],
      //   sort: [1, Validators.compose([Validators.required])],
      // })
      // ]),
      references: [defaultValue.references],
      related: [defaultValue.related],
      suggestions: [defaultValue.suggestions],
      isEmptyTemplate: [defaultValue.isEmptyTemplate, Validators.compose([Validators.required])],
    });
    // this.controls = <FormArray>this.dataForm.controls['contents'];
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

    // if (this.article.formData == null) {
    this.subscriptions.push(
      this.route.params.subscribe(params => {
        const isEdit: boolean = params.isEdit;
        if (params['id']) {
          this.getArticle(parseInt(params['id']), isEdit);
        } else {
          this.goBackToAdd('Silahkan tambah artikel terlebih dahulu');
        }
      })
    );
    // } else {
    //   this.setArticle(this.article.formData);
    // }

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
    this.subscriptions.push(this.dragMovedSubject.pipe(debounceTime(100)).pipe(distinctUntilChanged()).subscribe(event => {
      this.dragMoved(event)
    }))
  }
  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }
  ngAfterViewInit(): void {
    this.finishRender = true;
  }

}
