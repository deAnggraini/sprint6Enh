import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { ArticleService } from 'src/app/modules/_services/article.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-terbaru',
  templateUrl: './terbaru.component.html',
  styleUrls: ['./terbaru.component.scss']
})
export class TerbaruComponent implements OnInit {
  
  public dataList: any[] = [];
  public backend_img: string = environment.backend_img;
  slides = [];
  slideConfig = {
    "slidesToShow": 3,
    "slidesToScroll": 1,
    "infinite": false,
  };

  constructor(private articleService: ArticleService, private changeDetectorRef: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.articleService.news().subscribe(resp => {
      this.slides = (resp.slice(0, 6));
      setTimeout(() => this.changeDetectorRef.detectChanges(), 0);
    });
  }

}
