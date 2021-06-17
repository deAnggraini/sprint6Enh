import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { ArticleService } from 'src/app/modules/_services/article.service';
import { Observable, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, map, tap, switchMap } from 'rxjs/operators';

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
    private changeDetectorRef: ChangeDetectorRef) {
  }

  ngOnInit(): void {
  }

  setKeyword(value: string) {
    this.populateData(value || '');
  }

  private populateData(keyword: string) {
    this.keyword = keyword;
    this.apiArticle.search(keyword).subscribe(
      res => this.setData(res)
    )
  }

  btnSearch(input: HTMLInputElement) {
    this.populateData(input.value);
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
        this.apiArticle.search(term).pipe(
          map(res => this.setData(res))
        )
      ),
      tap(() => this.searching = false)
    );

}
