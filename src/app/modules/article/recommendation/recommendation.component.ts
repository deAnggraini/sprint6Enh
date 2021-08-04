import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../_services/article.service';
import { environment } from 'src/environments/environment';
import { PaginationModel } from 'src/app/utils/_model/pagination';
import { Location } from '@angular/common';

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
  dataPerPage: number = 12;
  page: number = 1;
  rowPage: number = 3;
  paging: PaginationModel = PaginationModel.createEmpty();


  constructor(
    private router: Router,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef,
    private location: Location) { }

  ngOnInit(): void {
    this.articleService.recommendation().subscribe(
      resp => {
        // this.dataList = resp;
        this.dataList = resp.slice(0, 12);
        // this.total = Math.floor(Math.random() * 100 + 10);
        this.length = resp.length;
        setTimeout(() => this.changeDetectorRef.detectChanges(), 0);

        this.paging = new PaginationModel(this.page, this.length, this.dataPerPage, this.rowPage);
      }
    );
  }

  back() {
    this.location.back();
  }

}
