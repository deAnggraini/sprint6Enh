import { ChangeDetectorRef, Component, OnInit, Output, EventEmitter, Input, AfterViewInit, OnDestroy } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router, NavigationEnd, NavigationStart } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService, UserModel } from '../../auth';
import { StrukturDTO } from '../../_model/struktur.dto';
import { ArticleService } from '../../_services/article.service';
import { StrukturService } from '../../_services/struktur.service';
import { ArticleDTO, ArticleContentDTO, ArticleParentDTO } from '../../_model/article.dto';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { NgbModalConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PlatformLocation } from '@angular/common';
import DiffMatchPatch from 'diff-match-patch';

@Component({
  selector: 'pakar-article-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.scss']
})
export class PreviewComponent implements OnInit, AfterViewInit, OnDestroy {

  @Input() hideTopbar: boolean = true;
  @Input() readerView: boolean = false;
  @Input() alert: boolean = false;
  @Input() isAllowEdit: boolean = false;
  @Input() btnSaveEnabled: boolean = false;
  @Input() btnSaveSendEnabled: boolean = false;
  @Input() alertMessage: string;
  @Input() showVideo: boolean = false;
  @Input() isCompare: boolean = false;
  @Input() originArticle: ArticleDTO = null;

  @Output() onCancelCallback = new EventEmitter<any>();
  @Output() onSaveCallback = new EventEmitter<any>();
  @Output() onSaveSendCallback = new EventEmitter<any>();

  articleDTO: ArticleDTO;

  categoryId: number = 0;
  struktur$: BehaviorSubject<StrukturDTO> = new BehaviorSubject<StrukturDTO>(null);

  hideTable: boolean = true;
  hideFAQ: boolean = true;
  noVideoPreview: string;
  videoUrl: SafeResourceUrl;
  imageTitle: string;
  imageSrc: string;
  dataForm: FormGroup;
  user$: Observable<UserModel>;
  aliasName: string = 'AA';
  fullName: string = 'Empty';
  role: string = 'READER';
  changed_date: Date = new Date();
  skReferences = [];
  relatedArticle = [];
  imageFile: string;
  sourceImg: string;
  backend_img: string = environment.backend_img;

  onPreview: boolean = false;
  isExpand: boolean = false;
  editable: boolean = true;

  diff = new DiffMatchPatch();
  popstate: boolean = false;

  //faq carousel
  slides = [];
  slideConfig = {
    "slidesToShow": 3,
    "slidesToScroll": 1,
    "infinite": false,
    "prevArrow": "<img class='a-left control-c prev slick-prev' src='./assets/media/svg/bca/homepage/carousel-prev.svg'>",
    "nextArrow": "<img class='a-right control-c next slick-next' src='./assets/media/svg/bca/homepage/carousel-next.svg'>",
    responsive: [
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
    ]
  };

  // scroll 
  scrollLevels: { [navigationId: number]: number } = {};
  lastId = 0;
  restoredId: number;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private strukturService: StrukturService,
    private auth: AuthService,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef,
    private confirm: ConfirmService,
    private configModel: NgbModalConfig,
    private _sanitizer: DomSanitizer,
    private modalService: NgbModal,
    private platformLocation: PlatformLocation,
  ) {
    this.configModel.backdrop = 'static';
    this.configModel.keyboard = false;
    this.user$ = this.auth.currentUserSubject.asObservable();

    history.pushState(null, null, window.location.href);
    this.platformLocation.onPopState(() => {
      if (this.modalService.hasOpenModals()) {
        history.pushState(null, null, window.location.href);
        this.modalService.dismissAll();
      }
    });
  }

  onCancel(e) {
    if (this.onCancelCallback.observers.length) {
      this.onCancelCallback.emit(false);
    } else {
      this.router.navigate([`/article/form/${this.articleDTO.id}`]);
    }
    return false;
  }
  onSave(e) {
    if (this.onSaveCallback.observers.length) {
      this.onSaveCallback.emit(true);
    } else {
      this.router.navigate([`/article/form/${this.articleDTO.id}`]);
    }
    return false;
  }
  onSaveSend(e) {
    if (this.onSaveSendCallback.observers.length) {
      this.onSaveSendCallback.emit(false);
    } else {
      this.router.navigate([`/article/form/${this.articleDTO.id}`]);
    }
    return false;
  }
  onEdit(e, contentId: number = 0, contentTitle: string = '') {
    if (this.role == 'READER') return false;
    const item = this.articleDTO;
    this.articleService.checkArticleEditing(item.id).subscribe((resp: UserModel[]) => {
      let editingMsg: string = '';
      if (resp && resp.length) {
        editingMsg = '<br><br>';
        editingMsg += `Saat ini artikel sedang di edit juga oleh :
            <ul>`;
        resp.forEach(d => {
          editingMsg += `<li>${d.fullname}</li>`;
        });
        editingMsg += '</ul>'
      }

      let msg: string = '';
      if (contentId === 0) {
        msg = `artikel “<b>${item.title}</b>”`
      } else {
        msg = `topik  “<b>${contentTitle}</b>” pada artikel ${item.title}`
      }

      this.confirm.open({
        title: `Ubah ${contentId == 0 ? "Artikel" : "Topik"}`,
        message: `<p>Apakah Kamu yakin ingin mengubah ${msg}?${editingMsg}`,
        btnOkText: 'Ubah',
        btnCancelText: 'Batal'
      }).then((confirmed) => {
        if (confirmed === true) {
          this.router.navigate(
            [`/article/form/${item.id}`,
            { isEdit: true, contentId, needClone: item.isPublished === true && item.isClone === false }
            ], { fragment: `acc-id-${contentId}` }
          );
        }
      });
    });
    e.stopPropagation();
    return false;
  }

  ngOnInit(): void {
    this.loadData();
    this.editable = this.hideTopbar;

    //user
    this.user$.subscribe(u => {
      // const aliasNameArr: string[] = [u.firstname, u.lastname];
      // this.aliasName = aliasNameArr.map(d => d ? d[0] : '').join('');
      // this.fullName = u.fullname;
      this.role = u.roles[0];
    });

    //carousel test
    this.articleService.recommendation().subscribe(resp => {
      this.slides = resp.slice(0, 6);
      setTimeout(() => this.changeDetectorRef.detectChanges(), 0);
    });
  }

  ngAfterViewInit(): void {
    document.querySelectorAll('a').forEach(element => {
      if (element.attributes.getNamedItem('target') && element.attributes.getNamedItem('rel')) {
        element.removeAttribute('target')
      }
    })
  }

  private loadData() {
    if (this.articleService.formData != null) {
      this.setArticle(this.articleService.formData);
      if (this.articleService.formAlert) {
        this.alert = true;
        this.alertMessage = this.articleService.formAlert;
        setTimeout(() => {
          document.getElementById('alert').hidden = true;
          this.articleService.formAlert = null;
        }, 3000);
      }
    } else {
      this.route.params.subscribe(params => {
        if (params.id) {
          this.articleService.getById(params.id, false).subscribe(resp => {
            this.setArticle(resp);
            if (this.articleService.formAlert) {
              this.alert = true;
              this.alertMessage = this.articleService.formAlert;
              setTimeout(() => {
                document.getElementById('alert').hidden = true;
                this.articleService.formAlert = null;
              }, 3000);
            }
            this.changeDetectorRef.detectChanges();
          })
        }
      });
    }
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
  private compareContent(contents: ArticleContentDTO[]): boolean {
    if (!contents) return;
    let result: boolean = false;
    contents.forEach(d => {
      const old = this.articleService._findNode(d.id, this.originArticle.contents);
      const { text, hasNew } = this.compareDesc(old.topicContent, d.topicContent);
      if (hasNew) {
        result = true;
        d.title = d.topicTitle = `<span class="bg-new">${d.title} <sup class="fb7 fs10 txt-new">(new)</sup></span>`;
        d.topicContent = text;
      }

      const childHasNew = this.compareContent(d.children);
      if (childHasNew) {
        result = true;
        d.title = d.topicTitle = `<span class="bg-new">${d.title} <sup class="fb7 fs10 txt-new">(new)</sup></span>`;
      }
    })
    return result;
  }
  private removeHTMLTag(value: string): string {
    return value.replace(/(<([^>]+)>)/gi, "");
  }
  private splitParagraph(value: string): string[] {
    const result = [];

    let _value = value.slice();
    _value = _value.replace(/<ul/gi, '<p><ul');
    _value = _value.replace(/<\/ul>/gi, '</ul></p>');
    _value = _value.replace(/<ol/gi, '<p><ol');
    _value = _value.replace(/<\/ol>/gi, '</ol></p>');

    const split = _value.split('</p>');
    split.forEach(d => {
      if (d) {
        result.push(d.replace('<p>', ''));
      }
    })
    return result;
  }
  private compareDesc(old: string, _new: string): { text: string, hasNew: boolean } {
    if (!old) old = '';
    if (!_new) _new = '';

    const result = [];
    let hasNew: boolean = false;
    const oldArr = this.splitParagraph(old);
    const newArr = this.splitParagraph(_new);

    newArr.forEach((d, i) => {
      let tagStart = 'span';
      let addClass = '';
      if (d.startsWith("<ol") || d.startsWith("<ul")) { tagStart = 'div'; addClass = 'tag-ul' }
      result.push('<p>');
      const diff = this.diff.diff_main(oldArr[i] || '', d);
      diff.forEach(d => {
        const code = d[0];
        const str = d[1];
        if (code == 0) result.push(str);
        if (code == 1) {
          result.push(`<${tagStart} class="bg-new ${addClass}">${str} <sup class="fb7 fs10 txt-new">(new)</sup></${tagStart}>`);
          hasNew = true;
        }
      });
      result.push('</p>');
    })
    return { text: result.join(''), hasNew };
  }
  private getCompareArticle(article: ArticleDTO): ArticleDTO {
    const _article: ArticleDTO = JSON.parse(JSON.stringify(article));
    const _old = this.originArticle;

    _article.desc = this.compareDesc(_old.desc, _article.desc).text;
    this.compareContent(_article.contents);

    return _article;
  }
  setArticle(article: ArticleDTO) {
    this.articleDTO = article;
    if (this.isCompare) {
      this.articleDTO = this.getCompareArticle(article);
    }
    this.categoryId = this.articleDTO.structureId;

    // HQ tampilkan gambar disini
    const { image } = this.articleDTO;
    if (image) {
      if (typeof (image) == "string") { // image string artinya sudah diupload
        this.imageSrc = `${environment.backend_img}/${image}`;
        this.imageTitle = image.split("/")[2].split(".")[0];
      } else { // image bukan string, kemungkinan object file
        const reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = () => {
          this.imageSrc = reader.result as string;
        };
        this.imageTitle = image.name?.split(".")[0];
      }
    } else {
      this.imageSrc = this.backend_img + '/articles/artikel-no-image.jpg';
      this.imageTitle = "Ini adalah judul dari infografis"
    }

    const node = this.strukturService.findNodeById(this.categoryId);
    this.struktur$.next(node);

    this.skReferences = this.articleDTO.references;
    this.relatedArticle = this.articleDTO.related;
    this.getVideo(this.articleDTO.video);

    // HQ, mapping backend ke UI DTO
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

    // set fullaname, alias
    this.fullName = article.modified_name || '';
    this.changed_date = article.modified_date || new Date();
    const _split = this.fullName.split(' ');
    this.aliasName = [
      (_split[0][0]) || 'A',
      (_split[_split.length - 1][0]) || 'Z',
    ].join('');

    this.changeDetectorRef.detectChanges();
  }

  numberingFormat(data: ArticleContentDTO): string {
    const listParent = data.listParent || [];
    const strNoParent = listParent.map(d => d.no);
    strNoParent.push(data.no);
    return strNoParent.join(".");
  }

  getVideo(videoUrl) {
    if (this.showVideo && videoUrl) {
      this.videoUrl = this._sanitizer.bypassSecurityTrustResourceUrl(videoUrl);
    } else if (videoUrl) {
      this.showVideo = false;
      this.noVideoPreview = this.backend_img + '/articles/poster-myvideo.png';
    } else {
      this.showVideo = false;
      this.noVideoPreview = this.backend_img + '/articles/poster-myvideo.png';
    }
  }

  expandCollapse() {
    if (this.isExpand == false) {
      this.isExpand = true;
    } else {
      this.isExpand = false;
    }
    this.expandCollapseAccordion(this.articleDTO.contents, this.isExpand);
  }
  expandCollapseAccordion(contents: ArticleContentDTO[], expanded: boolean) {
    if (contents && contents.length) {
      contents.forEach(d => {
        d.expanded = expanded;
        this.expandCollapseAccordion(d.children, expanded);
      })
    }
  }

  closeAlert() {
    // var alert1 = document.getElementById('alerts');
    // var display = document.getElementById('name');

    // alert1.innerHTML = "";

    this.alert = false;
  }

  ngOnDestroy(): void {
  }

}
