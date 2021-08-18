import { ChangeDetectorRef, Component, OnInit, Output, EventEmitter, Input, AfterViewInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService, UserModel } from '../../auth';
import { StrukturDTO } from '../../_model/struktur.dto';
import { ArticleService } from '../../_services/article.service';
import { StrukturService } from '../../_services/struktur.service';
import { ArticleDTO, ArticleContentDTO, ArticleParentDTO } from '../../_model/article.dto';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'pakar-article-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.scss']
})
export class PreviewComponent implements OnInit, AfterViewInit {

  @Input() hideTopbar: boolean = true;
  @Input() readerView: boolean = false;
  @Input() alert: boolean = false;
  @Input() isAllowEdit: boolean = false;
  @Input() btnSaveEnabled: boolean = false;
  @Input() btnSaveSendEnabled: boolean = false;
  @Input() alertMessage: string;
  @Output() onCancelCallback = new EventEmitter<any>();
  @Output() onSaveCallback = new EventEmitter<any>();
  @Output() onSaveSendCallback = new EventEmitter<any>();

  articleDTO: ArticleDTO;

  categoryId: number = 0;
  struktur$: BehaviorSubject<StrukturDTO> = new BehaviorSubject<StrukturDTO>(null);

  hideTable: boolean = true;
  hideFAQ: boolean = true;
  showVideo: boolean = false;
  noVideoPreview: string;
  videoUrl: SafeResourceUrl;
  imageTitle: string;
  imageSrc: string;
  dataForm: FormGroup;
  user$: Observable<UserModel>;
  aliasName: string = 'AA';
  fullName: string;
  changed_date: Date = new Date();
  skReferences = [];
  relatedArticle = [];
  imageFile: string;
  sourceImg: string;
  backend_img: string = environment.backend_img;

  onPreview: boolean = false;
  isExpand: boolean = false;
  editable: boolean = true;


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

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private strukturService: StrukturService,
    private auth: AuthService,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef,
    private confirm: ConfirmService,
    private configModel: NgbModalConfig
  ) {

    this.configModel.backdrop = 'static';
    this.configModel.keyboard = false;
    this.user$ = this.auth.currentUserSubject.asObservable();
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
  onEdit(contentId: number = 0) {
    const item = this.articleDTO;
    this.articleService.checkArticleEditing(item.id).subscribe((resp: UserModel[]) => {
      let editing: UserModel[] = [];
      let editingMsg: string = '';
      if (resp) {
        editingMsg = '<br><br>';
        editingMsg += `Saat ini artikel sedang di edit juga oleh :
            <ul>`;
        resp.forEach(d => {
          editingMsg += `<li>${d.fullname}</li>`;
        });
        editingMsg += '</ul>'
      }

      this.confirm.open({
        title: `Ubah Artikel`,
        message: `<p>Apakah Kamu yakin ingin mengubah artikel “<b>${item.title}</b>”?${editingMsg}`,
        btnOkText: 'Ubah',
        btnCancelText: 'Batal'
      }).then((confirmed) => {
        if (confirmed === true) {
          this.router.navigate([`/article/form/${item.id}`, { isEdit: true, contentId }]);
        }
      });
    });
    return false;
  }

  ngOnInit(): void {
    this.loadData();
    this.editable = this.hideTopbar;

    //user
    this.user$.subscribe(u => {
      const aliasNameArr: string[] = [u.firstname, u.lastname];
      this.aliasName = aliasNameArr.map(d => d ? d[0] : '').join('');
      this.fullName = u.fullname;
    });

    //carousel test
    this.articleService.recommendation().subscribe(resp => {
      this.slides = resp.slice(0, 6);
      setTimeout(() => this.changeDetectorRef.detectChanges(), 0);
    });

  }

  ngAfterViewInit(): void {
    // setTimeout(() => { document.getElementById('alert').hidden = true }, 3000);
  }

  private loadData() {
    if (this.articleService.formData != null) {
      this.setArticle(this.articleService.formData);
      if (this.articleService.formAlert) {
        this.alert = true;
        this.alertMessage = this.articleService.formAlert;
        setTimeout(() => {
          console.log('trigger hidden alert')
          document.getElementById('alert').hidden = true;
          this.articleService.formAlert = null;
        }, 3000);
      }
    } else {
      this.route.params.subscribe(params => {
        console.log('loadData', { params });
        if (params.id) {
          this.articleService.getById(params.id, false).subscribe(resp => {
            this.setArticle(resp);
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
  setArticle(article: ArticleDTO) {
    this.articleDTO = article;
    this.categoryId = this.articleDTO.structureId;

    // HQ tampilkan gambar disini
    const { image } = this.articleDTO;
    if (image) {
      if (typeof (image) == "string") { // image string artinya sudah diupload
        this.imageSrc = image;
      } else { // image bukan string, kemungkinan object file
        const reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = () => {
          this.imageSrc = reader.result as string;
        };
      }
      this.imageTitle = image.name?.split(".")[0];
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
  }

  numberingFormat(data: ArticleContentDTO): string {
    const listParent = data.listParent || [];
    const strNoParent = listParent.map(d => d.no);
    strNoParent.push(data.no);
    return strNoParent.join(".");
  }

  getVideo(event) {
    if (event) {
      // this.videoUrl = this._sanitizer.bypassSecurityTrustResourceUrl(event);
      // this.showVideo = true;

      // temporary show default image
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

}
