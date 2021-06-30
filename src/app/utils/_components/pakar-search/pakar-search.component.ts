import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { ArticleService } from 'src/app/modules/_services/article.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap, switchMap, map } from 'rxjs/operators';

@Component({
  selector: 'pakar-search',
  templateUrl: './pakar-search.component.html',
  styleUrls: ['./pakar-search.component.scss']
})
export class PakarSearchComponent implements OnInit {

  keyword: string = '';
  show: boolean = false;
  lastKeywords: any[] = [];
  data: any[];
  searching: boolean = false;

  constructor(private articleService: ArticleService, private router: Router,
    private changeDetectorRef: ChangeDetectorRef) { }

  @HostListener('document:click', ['$event'])
  clickout() { this.show = false; }

  ngOnInit(): void {
    this.articleService.keyword$.subscribe(resp => {
      this.keyword = resp;
    });
    this.articleService.lastKeywords$.subscribe(resp => {
      this.lastKeywords = resp;
    });
    this.articleService.lastKeywords();
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
        this.articleService.suggestion(term).pipe(
          map(resp => {
            this.data = resp;
            this.show = true;
            setTimeout(_ => { this.changeDetectorRef.detectChanges() }, 0);
          })
        )
      ),
      tap(() => this.searching = false)
    );

  formatter = (title: string, keyword) => {
    const regEx = new RegExp(keyword, "ig");
    const replace = `<span class="fount-text">${keyword}</span>`;
    return title.replace(regEx, replace);
  }

  setKeyword(value: string, goto: boolean = false) {
    this.populateData(value || '', goto);
  }

  populateData(keyword: string, goto: boolean = false) {
    this.articleService.keyword$.next(keyword);
    this.keyword = keyword;
    if (goto) {
      this.router.navigate(['/article/search', { keyword }]);
    } else {
      if (keyword == '') {
        this.show = true;
        return this.articleService.lastKeywords();
      }
      this.articleService.suggestion(keyword).subscribe(
        data => {
          this.data = data;
          this.show = true;
          setTimeout(_ => { this.changeDetectorRef.detectChanges() }, 0);
        }
      )
    }
  }

  doSearch() {
    this.populateData(this.keyword, true);
  }

}
