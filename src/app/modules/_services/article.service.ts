import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { environment } from 'src/environments/environment';
import { of, BehaviorSubject, Observable } from 'rxjs';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  // parameter article form add
  public formParam: any = null;

  private _base_url = `${environment.apiUrl}/doc`;
  private empty_search: any[] = [
    { state: 0, text: 'Ketentuan Time Loan' },
    { state: 0, text: 'Prosedur Giro' },
    { state: 0, text: 'Open Payment' },
    { state: 0, text: 'Pembukaan Rekening Tahapan' },
    { state: 0, text: 'Flazz' },
  ]

  // share state
  keyword$: BehaviorSubject<string> = new BehaviorSubject<string>('');
  lastKeywords$: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
  lastTimeGetKeyword: Date;

  constructor(private apiService: ApiService) { }

  lastKeywords() {
    if (!this.lastTimeGetKeyword || moment(this.lastTimeGetKeyword).diff(moment(), 'd') > 0) {
      this.apiService.post(`${this._base_url}/keyword`, {}).subscribe(
        resp => {
          this.lastKeywords$.next(resp);
          this.lastTimeGetKeyword = new Date();
        }
      );
    }
  }

  suggestion(keyword) {
    if (keyword === "") {
      return of(this.empty_search);
    }
    return this.apiService.post(`${this._base_url}/suggestion`, { keyword });
  }

  search(params: { keyword: string, page: number } = null): Observable<any> {
    if (params) {
      return this.apiService.post(`${this._base_url}/search`, params);
    } else {
      return of(false);
    }
  }

  news() {
    return this.apiService.post(`${this._base_url}/news`, {});
  }

  recommendation() {
    return this.apiService.post(`${this._base_url}/recommendation`, {});
  }

  popular() {
    return this.apiService.post(`${this._base_url}/popular`, {});
  }

}
