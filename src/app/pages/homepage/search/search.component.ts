import { Component, OnInit } from '@angular/core';
import { ArticleService } from 'src/app/modules/_services/article.service';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map, tap, switchMap } from 'rxjs/operators';

const states = ['Alabama', 'Alaska', 'American Samoa', 'Arizona', 'Arkansas', 'California', 'Colorado',
  'Connecticut', 'Delaware', 'District Of Columbia', 'Federated States Of Micronesia', 'Florida', 'Georgia',
  'Guam', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine',
  'Marshall Islands', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana',
  'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
  'Northern Mariana Islands', 'Ohio', 'Oklahoma', 'Oregon', 'Palau', 'Pennsylvania', 'Puerto Rico', 'Rhode Island',
  'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virgin Islands', 'Virginia',
  'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'];

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

  constructor(private apiArticle: ArticleService) {
  }

  ngOnInit(): void {
  }

  btnSearch(input: HTMLInputElement) {
    this.keyword = input.value;
    this.apiArticle.search(this.keyword).subscribe(
      res => {
        this.show = true;
        this.data = res;
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
            // return states;
          })
        )
      ),
      tap(() => this.searching = false)
    );

}
