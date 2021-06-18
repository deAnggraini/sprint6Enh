import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { ArticleService } from '../../_services/article.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-terbaru',
  templateUrl: './terbaru.component.html',
  styleUrls: ['./terbaru.component.scss']
})
export class TerbaruComponent implements OnInit {

  dataList: any[] = [];
  total: number = 0;
  length: number = 0;
  backend_img: string = environment.backend_img;
  keyword: string = ''

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef,
    private location: Location) { }

  ngOnInit(): void {
    this.keyword = this.articleService.keyword$.value;
    this.articleService.news().subscribe(
      resp => {
        this.dataList = resp;
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
