import { Component, OnInit, OnDestroy, ChangeDetectorRef, ViewChild } from '@angular/core';
import { PaginationModel } from 'src/app/utils/_model/pagination';
import { ArticleService } from '../../_services/article.service';
import { Subscription } from 'rxjs';
import { NgbDropdown } from '@ng-bootstrap/ng-bootstrap';

interface TabDTO {
  dataList: any[],
  pagination: PaginationModel
}
interface FormBean {
  approved: TabDTO,
  pending: TabDTO,
  draft: TabDTO,
}
const PAGE_LIMIT = 5;
const EMPTY_FORM_BEAN: FormBean = {
  approved: {
    dataList: [],
    pagination: new PaginationModel(1, 0)
  },
  pending: {
    dataList: [],
    pagination: new PaginationModel(1, 0)
  },
  draft: {
    dataList: [],
    pagination: new PaginationModel(1, 0)
  },
}

@Component({
  selector: 'app-my-pages',
  templateUrl: './my-pages.component.html',
  styleUrls: ['./my-pages.component.scss']
})
export class MyPagesComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  dataForm: FormBean = JSON.parse(JSON.stringify(EMPTY_FORM_BEAN));
  listHeader: string[] = ['approved', 'pending', 'draft'];
  listStatus: string[] = ['PUBLISHED', 'PENDING', 'DRAFT'];

  // filter component
  keyword: string = '';
  type: string = 'ALL';

  constructor(
    private cdr: ChangeDetectorRef,
    private articleService: ArticleService) { }

  ngOnInit(): void {
    this.listHeader.forEach((d, i) => {
      this.search(d, this.listStatus[i]);
    })
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  getIcon(type: string) {
    switch (type.toLowerCase()) {
      case 'article': return 'article.svg';
      case 'microinformation': return 'vp-1.svg';
      case 'atribut': return 'vp-2.svg';
      case 'formulir': return 'formulir.svg';
    }
    return "doc-empty.svg"
  }

  setPage(key: string, item: TabDTO, page: number) {

  }

  // search data
  search(key: string, status: string, page: number = 1, limit: number = 10) {
    this.subscriptions.push(
      this.articleService.searchMyPages(this.keyword, status, page, limit).subscribe(resp => {
        console.log({ resp });
        if (resp) {
          const { totalElements, totalPages, currentPage } = resp;
          const tabDto: TabDTO = this.dataForm[key]
          tabDto.dataList = resp.list;
          tabDto.pagination = new PaginationModel(currentPage, totalElements, PAGE_LIMIT);

          switch (key.toLowerCase()) {
            case 'approved': break;
            case 'pending': break;
            case 'draft': break;
          }
          this.cdr.detectChanges();
        }
      })
    );
  }

  // table ... event
  onClickRevision(item: any, index: number) {
    console.log('onClickRevision', { item, index });
    return false;
  }
  onClickEdit(item, index: number) {
    console.log('onClickEdit', { item, index });
    return false;
  }
  onClickCancel(item, index: number) {
    console.log('onClickCancel', { item, index });
    return false;
  }

}
