import { Component, OnInit, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../../_services/article.service';
import { Subscription } from 'rxjs';
import { PaginationModel } from 'src/app/utils/_model/pagination';

interface ResponseSearch {
  list: DataItem[],
  paging?: PaginationModel,
  suggestion?: string
}

interface DataItem {
  id: number,
  title: string,
  type: string,
  location: string,
  desc: string,
}

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];

  suggestion: string = '';
  keyword: string = '';
  pakar: ResponseSearch = { list: [], paging: PaginationModel.createEmpty() };
  faq: ResponseSearch = { list: [], paging: PaginationModel.createEmpty() };

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private changeDetectorRef: ChangeDetectorRef) {
  }

  public setPage(key: string, page: number) {
    const resp: ResponseSearch = key == "pakar" ? this.pakar : this.faq;
    resp.paging.setPage(page);
    this.search(key);
  }

  emptyDataItem(): ResponseSearch {
    return {
      paging: PaginationModel.createEmpty(),
      list: [],
      suggestion: '',
    }
  }

  formatterCategory(category: any[]): string {
    const result = [];
    const listCategory = category.map(d => d.title);
    listCategory.forEach(d => {
      result.push(`<a routerLink="" class="cursor-pointer">${d}</a>`);
    });
    return result.join(' > ');
  }

  private getQParams(paging: PaginationModel, type: string) {
    return {
      keyword: this.keyword,
      page: paging.page,
      limit: paging.limit,
      sorting: { column: 'title', sort: 'asc' },
      type,
      state: 'PUBLISHED'
    }
  }
  private getObjectData(key: string): ResponseSearch {
    if (key == "faq") return this.faq;
    else return this.pakar;
  }

  search(key: string, type: string = 'ALL') {
    const _data: ResponseSearch = this.getObjectData(key);
    const searchSubscr = this.articleService.search(this.getQParams(_data.paging, type)).subscribe(resp => {
      if (resp) {
        const _data: ResponseSearch = this.getObjectData(key);
        _data.list = resp.list;
        _data.paging = PaginationModel.create(resp);
        _data.suggestion = resp.suggestion;
        this.changeDetectorRef.detectChanges();
      }
    });
    this.subscriptions.push(searchSubscr);
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params.keyword) {
        this.articleService.keyword$.next(params.keyword);
        this.keyword = params.keyword;
      }
      this.search('pakar');
      this.search('faq', 'faq');
      this.changeDetectorRef.detectChanges();
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

}
