import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { ArticleService } from 'src/app/modules/_services/article.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.scss'],
})
export class RecommendationComponent implements OnInit {

  public dataList: any[] = [];
  public backend_img: string = environment.backend_img;

  constructor(private config: NgbCarouselConfig, private articleService: ArticleService, private changeDetectorRef: ChangeDetectorRef) {
    config.showNavigationArrows = true;
    config.showNavigationIndicators = false;
    config.interval = 0;
  }

  ngOnInit(): void {
    this.articleService.recommendation().subscribe(resp => {
      this.parsePer3(resp.slice(0, 6));
      setTimeout(() => this.changeDetectorRef.detectChanges(), 0);
    });
  }

  parsePer3(dataList: any[]) {
    const per = 3;
    const len = dataList.length;
    const loop = Math.ceil(len / per);
    for (let i = 0; i < loop; i++) {
      const start = i * per;
      const end = start + per;
      const data = dataList.slice(start, end);
      this.dataList.push(data);
    }
  }

}
