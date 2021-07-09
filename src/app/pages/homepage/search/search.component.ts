import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { ArticleService } from 'src/app/modules/_services/article.service';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map, tap, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  show: boolean = false;
  searching: boolean = false;
  keyword: string = '';
  data: any[] = [];
  emptySearch: boolean = false;

  formatter = (title: string, keyword) => {
    const regEx = new RegExp(keyword, "ig");
    const replace = `<span style="color: red;" class="fount-text">${keyword}</span>`;
    return title.replace(regEx, replace);
  }

  @HostListener('document:click', ['$event'])
  clickout() {
    this.show = false;
  }

  constructor(private apiArticle: ArticleService,
    private changeDetectorRef: ChangeDetectorRef, private router: Router) {
  }

  ngOnInit(): void {
    this.keyword = this.apiArticle.keyword$.value;
  }

  setKeyword(value: string, goto: boolean = false) {
    this.populateData(value || '', goto);
  }

  private populateData(keyword: string, goto: boolean = false) {
    this.apiArticle.keyword$.next(keyword);
    this.keyword = keyword;
    if (goto) {
      this.router.navigate(['/article/search', { keyword }]);
    } else {
      this.apiArticle.suggestion(keyword).subscribe(
        res => this.setData(res)
      )
    }
  }

  btnSearch(input: HTMLInputElement) {
    this.populateData(input.value, true);
  }

  hideResult() {
    this.show = false;
  }

  private setData(data: any[]) {
    this.emptySearch = data.length && data[0].hasOwnProperty('state');
    this.data = data;
    this.show = true;
    setTimeout(_ => { this.changeDetectorRef.detectChanges() }, 0);
  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap((keyword) => {
        this.searching = true;
        this.keyword = keyword;
      }),
      switchMap(term =>
        this.apiArticle.suggestion(term).pipe(
          map(res => this.setData(res))
        )
      ),
      tap(() => this.searching = false)
    );

}
