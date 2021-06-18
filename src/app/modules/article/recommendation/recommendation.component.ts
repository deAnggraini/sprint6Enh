import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../_services/article.service';
import { Location } from '@angular/common';
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
  keyword: string = '';

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef,
    private location: Location) { }

  ngOnInit(): void {
    this.keyword = this.articleService.keyword$.value;
    this.articleService.recommendation().subscribe(
      resp => {
        this.dataList = resp.slice(0, 12);
        this.total = Math.floor(Math.random() * 100 + 10);
        this.length = resp.length;
        setTimeout(() => this.changeDetectorRef.detectChanges(), 0);

      }
    );
  }

  back() {
    this.location.back();
  }

  search() {
    this.articleService.keyword$.next(this.keyword);
    this.router.navigate(['/article/search', { keyword: this.keyword }]);
  }

}
