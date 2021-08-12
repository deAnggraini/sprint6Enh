import { ChangeDetectorRef, Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService, UserModel } from '../../auth';
import { StrukturDTO } from '../../_model/struktur.dto';
import { ArticleService } from '../../_services/article.service';
import { StrukturService } from '../../_services/struktur.service';
import { ArticleDTO, ArticleContentDTO } from '../../_model/article.dto';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'pakar-article-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.scss']
})
export class PreviewComponent implements OnInit {

  @Input() hideTopbar: boolean = true;
  @Input() alert: boolean = false;
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
    private _sanitizer: DomSanitizer
  ) {
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

  ngOnInit(): void {
    this.loadData();
    console.log("Article DTO >>> ", this.articleDTO);
    this.skReferences = this.articleDTO.references;
    this.relatedArticle = this.articleDTO.related;
    this.getVideo(this.articleDTO.video);

    setTimeout(() => {document.getElementById('alert').hidden = true }, 3000);

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

  private loadData() {
    if (this.articleService.formData != null) {
      this.articleDTO = this.articleService.formData;
      this.categoryId = this.articleDTO.structureId;

      // HQ tampilkan gambar disini
      const { image } = this.articleDTO;
      console.log({ image });
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

    } else {
      this.route.params.subscribe(params => {
        this.categoryId = params.category;
        // this.loadData();
      });
    }
    const node = this.strukturService.findNodeById(this.categoryId);
    this.struktur$.next(node);
    console.log({ article: this.articleDTO });
  }

  numberingFormat(data: ArticleContentDTO): string {
    const listParent = data.listParent;
    const strNoParent = listParent.map(d => d.no);
    strNoParent.push(data.no);
    return strNoParent.join(".");
  }

  getVideo(event) {
    console.log({ event });
    if (event) {
      this.videoUrl = this._sanitizer.bypassSecurityTrustResourceUrl(event);
      this.showVideo = true;
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
  }

  closeAlert() {
    console.log("masuk close alert");
    // var alert1 = document.getElementById('alerts');
    // var display = document.getElementById('name');

    // alert1.innerHTML = "";
    
    this.alert = false;
  }

}
