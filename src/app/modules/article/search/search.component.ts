import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../../_services/article.service';

interface Response {
  result?: DataItem,
  keys?: string[],
  group?: Grouping,
  suggestion?: string
}

interface Grouping {
  [key: string]: DataItem
}

interface DataItem {
  total?: number,
  length?: number,
  data?: any[]
}

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  suggestion: string = '';
  keyword: string = '';
  pakar: DataItem = {
    total: 0,
    data: [],
    length: 0
  };
  keys: string[] = [];
  faq: DataItem = {
    total: 0,
    data: [],
    length: 0
  };

  constructor(
    private route: ActivatedRoute, 
    private articleService: ArticleService, 
    private changeDetectorRef: ChangeDetectorRef) {
    this.route.params.subscribe(params => {
      this.keyword = params.keyword
      if (this.keyword) {
        this.search();
      }
    });
  }

  emptyDataItem(): DataItem {
    return {
      total: 0,
      data: [],
      length: 0
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

  parseResponse(resp: Response) {
    this.suggestion = resp.suggestion || '';
    if (resp.result) {
      if (resp.result.length === 0) {
        this.keys = [];
        this.faq = this.emptyDataItem();
      }
      this.pakar = resp.result;
    }
    if (resp.keys) {
      this.keys = resp.keys;
    }
    if (resp.group?.faq) {
      this.faq = resp.group.faq as Grouping;
    }
    setTimeout(_ => { this.changeDetectorRef.detectChanges() }, 0);
  }

  search() {
    this.articleService.search(this.keyword).subscribe(resp => {
      this.parseResponse(resp as Response);
    })
  }

  ngOnInit(): void {
  }

}
