import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService, UserModel } from '../../auth';
import { StrukturDTO } from '../../_model/struktur.dto';
import { ArticleService } from '../../_services/article.service';
import { SkReferenceService } from '../../_services/sk-reference.service';
import { StrukturService } from '../../_services/struktur.service';

@Component({
  selector: 'app-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.scss']
})
export class PreviewComponent implements OnInit {

  categoryId: number = 0;
  struktur$: BehaviorSubject<StrukturDTO> = new BehaviorSubject<StrukturDTO>(null);

  hideTable: boolean = false;
  hideVideo: boolean = false;
  title: string = 'Tahapan';
  dataForm: FormGroup;
  user$: Observable<UserModel>;
  aliasName: string = 'AA';
  fullName: string;
  changed_date: Date = new Date();
  skReferences = [];
  relatedArticle = [];
  id = 100; //hardcode

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
    private route: ActivatedRoute,
    private struktutService: StrukturService,
    private auth: AuthService,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef,
    private skService: SkReferenceService,
  ) {
    this.user$ = this.auth.currentUserSubject.asObservable();
  }

  ngOnInit(): void {
    this.loadData();

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

    this.getDataSkSeReference("aaa");
    this.getDataRelatedArticle("aaa");
  }

  private loadData() {
    this.route.params.subscribe(params => {
      this.categoryId = params.category;
      this.loadData();
    });
    const node = this.struktutService.findNodeById(this.categoryId);
    this.struktur$.next(node);
  }

  // SK/SE 
  getDataSkSeReference(keyword: string) {
    this.skService.search(keyword).subscribe(resp => {
      if (resp) {
        this.skReferences = [...resp];
      }
    })
  }

  //Artikel Terkait
  getDataRelatedArticle(keyword: string) {
    this.articleService.searchArticle(keyword).subscribe(resp => {
      if (resp) {
        this.relatedArticle = [...resp];
      }
    })
  }
}
