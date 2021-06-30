import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../_services/article.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.scss']
})
export class RecommendationComponent implements OnInit {

  dataList: any[] = [];
  total: number = 0;
  length: number = 0;
  backend_img: string = environment.backend_img;

  // pagination
  dataPerPage: number = 6;
  page: number = 1;

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.articleService.recommendation().subscribe(
      resp => {
        this.dataList = resp.slice(0, 12);
        this.total = Math.floor(Math.random() * 100 + 10);
        this.length = resp.length;
        setTimeout(() => this.changeDetectorRef.detectChanges(), 0);

      }
    );
  }

}
