import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { environment } from 'src/environments/environment';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private _base_url = `${environment.apiUrl}/article`;
  private empty_search: any[] = [
    { state: 0, text: 'Ketentuan Time Loan' },
    { state: 0, text: 'Prosedur Giro' },
    { state: 0, text: 'Open Payment' },
    { state: 0, text: 'Pembukaan Rekening Tahapan' },
    { state: 0, text: 'Flazz' },
  ]

  constructor(private apiService: ApiService) { }

  search(keyword: string) {
    if (keyword === "") {
      return of(this.empty_search);
    }
    return this.apiService.post(`${this._base_url}/search`, { keyword });
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
