import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { ArticleService } from '../../_services/article.service';
import { Location } from '@angular/common';
import { PaginationModel } from 'src/app/utils/_model/pagination';

@Component({
  selector: 'app-terbaru',
  templateUrl: './terbaru.component.html',
  styleUrls: ['./terbaru.component.scss']
})
export class TerbaruComponent implements OnInit {

  dataList: any[] = [];
  dataLists: any[] = [];
  length: number = 0;
  backend_img: string = environment.backend_img;

  // pagination
  dataLimit: number = 12;
  currentPage: number = 1;
  rowPage: number = 3;
  paging: PaginationModel = PaginationModel.createEmpty();

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef,
    private location: Location
    ) { }

  ngOnInit(): void {
    this.populateList();
    
  }

  populateList() {
    this.articleService.news().subscribe( resp => {
        resp.sort((a,b) => (a.id < b.id ? -1 : 1));
                    
        for (let i = 0; i < resp.length; i += 12) {
          var temporaryList = resp.slice(i, i + 12);
          this.dataLists.push(temporaryList);
        }
        this.dataList = this.dataLists[this.currentPage - 1];
        this.length = resp.length;

        this.paging = new PaginationModel(this.currentPage, this.length, this.dataLimit);
        setTimeout(() => this.changeDetectorRef.detectChanges(), 0);
      });
  }

  setPage(page: number) {
    this.currentPage = page;
    this.populateList();
  }
  
  back() {
    this.location.back();
  }

}
