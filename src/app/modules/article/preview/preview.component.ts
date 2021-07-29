import { ChangeDetectorRef, Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService, UserModel } from '../../auth';
import { StrukturDTO } from '../../_model/struktur.dto';
import { ArticleService } from '../../_services/article.service';
import { SkReferenceService } from '../../_services/sk-reference.service';
import { StrukturService } from '../../_services/struktur.service';
import { ArticleDTO, ArticleContentDTO } from '../../_model/article.dto';

@Component({
  selector: 'pakar-article-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.scss']
})
export class PreviewComponent implements OnInit {

  @Output() onCancelCallback = new EventEmitter<any>();
  articleDTO: ArticleDTO;

  categoryId: number = 0;
  struktur$: BehaviorSubject<StrukturDTO> = new BehaviorSubject<StrukturDTO>(null);

  hideTable: boolean = true;
  hideFAQ: boolean = true;
  hideVideo: boolean = true;
  videoUrl: string;
  hideImage: boolean = true;
  imageName: string;
  imageSrc: string;
  dataForm: FormGroup;
  user$: Observable<UserModel>;
  aliasName: string = 'AA';
  fullName: string;
  changed_date: Date = new Date();
  skReferences = [];
  relatedArticle = [];
  id: number;

  //faq carousel
  backend_img: string = environment.backend_img;
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
    private struktutService: StrukturService,
    private auth: AuthService,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef,
    private skService: SkReferenceService,
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

  ngOnInit(): void {
    this.loadData();
    console.log("Article DTO >>> ", this.articleDTO);
    this.id = this.articleDTO.id;
    this.skReferences = this.articleDTO.references;
    this.relatedArticle = this.articleDTO.related;
    this.getImage(this.articleDTO.image);
    this.getVideo(this.articleDTO.video);


    //user
    this.user$.subscribe(u => {
      console.log("user$$$ >>", u);
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
    } else {
      this.route.params.subscribe(params => {
        this.categoryId = params.category;
        // this.loadData();
      });
    }
    const node = this.struktutService.findNodeById(this.categoryId);
    this.struktur$.next(node);
    console.log({ article: this.articleDTO });
  }

  numberingFormat(data: ArticleContentDTO): string {
    const listParent = data.listParent;
    const strNoParent = listParent.map(d => d.no);
    strNoParent.push(data.no);
    return strNoParent.join(".");
  }

  //image 
  getImage(event) {
    if (event) {
      this.imageName = event.name.split(".")[0];
      this.hideImage = false;
    } else {
      this.hideImage = true;
    }
  }

  getVideo(event) {
    if (event) {
      this.videoUrl = event
      this.hideVideo = false;
    } else {
      this.hideVideo = true;
    }
  }

  // SK/SE 
  // getDataSkSeReference(keyword: string) {
  //   this.skService.search(keyword).subscribe(resp => {
  //     if (resp) {
  //       this.skReferences = [...resp];
  //     }
  //   })
  // }

  //Artikel Terkait
  // getDataRelatedArticle(keyword: string) {
  //   this.articleService.searchArticle(keyword).subscribe(resp => {
  //     if (resp) {
  //       this.relatedArticle = [...resp];
  //     }
  //   })
  // }

}
