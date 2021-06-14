import { Component, OnInit, NgZone, ChangeDetectorRef } from '@angular/core';
import { ArticleService } from 'src/app/modules/_services/article.service';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map, tap, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  public model: any;
  public show: boolean = false;
  searching: boolean = false;
  keyword: string = '';
  data: any[] = [];

  formatter = (title: string, keyword) => {
    const regEx = new RegExp(keyword, "ig");
    const replace = `<span style="color: red;" class="fount-text">${keyword}</span>`;
    return title.replace(regEx, replace);
  }

  constructor(private apiArticle: ArticleService,
    private changeDetectorRef: ChangeDetectorRef) {
  }

  ngOnInit(): void {
  }

  btnSearch(input: HTMLInputElement) {
    this.keyword = input.value;
    this.apiArticle.search(this.keyword).subscribe(
      (res) => {
        this.data = res;
        this.show = true;
        console.log({ show: this.show, data: this.data });
        setTimeout(_ => { this.changeDetectorRef.detectChanges() }, 0);
      }
    )
  }

  hideResult() {
    this.show = false;
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
          map(res => {
            this.data = res;
            this.show = true;
            setTimeout(_ => { this.changeDetectorRef.detectChanges() }, 0);
            // return sesuatu kalo ingin dropdown bawaan
          })
        )
      ),
      tap(() => this.searching = false)
    );

}
