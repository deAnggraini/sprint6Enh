import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StrukturDTO } from '../../_model/struktur.dto';
import { BehaviorSubject, Subscription } from 'rxjs';
import { StrukturService } from '../../_services/struktur.service';
import { environment } from 'src/environments/environment';
import { SearchArticleParam, ArticleService } from '../../_services/article.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  categoryId: number = 0;
  struktur$: BehaviorSubject<StrukturDTO> = new BehaviorSubject<StrukturDTO>(null);
  image_path: string = environment.backend_img;
  listArticle: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private struktutService: StrukturService,
    private article: ArticleService,
    private cdr: ChangeDetectorRef) {

  }

  image_uri(image: string, article: boolean = false) {
    if (article) return `url(${this.image_path}/${image})`;
    return `url(${this.image_path}/${image})`;
  }

  private loadData() {
    const node = this.struktutService.findNodeById(this.categoryId);
    this.struktur$.next(node);
  }
  private checkArticle() {
    if (!this.categoryId) return;
    this.listArticle = [];
    const params: SearchArticleParam = {
      keyword: '', page: 1, limit: 10, sorting: { column: 'approved_date', sort: 'asc' },
      type: 'article', state: 'PUBLISHED', structureId: this.categoryId, isLatest: false
    };
    this.subscriptions.push(this.article.search(params).subscribe(resp => {
      resp.list.forEach(d => { this.listArticle.push(d); });
      this.cdr.detectChanges();
    }));
  }

  ngOnInit(): void {
    this.subscriptions.push(this.route.params.subscribe(params => {
      this.categoryId = params.category;
      this.loadData();
    }));
    this.subscriptions.push(this.struktutService.categories$.subscribe(() => {
      this.loadData();
    }));
    this.subscriptions.push(this.struktur$.subscribe(resp => {
      if (resp) {
        if (resp.menus?.length == 0) { this.checkArticle() }
      }
    }));
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

}
