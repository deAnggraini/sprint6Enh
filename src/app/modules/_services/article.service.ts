import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private _base_url = `${environment.apiUrl}/article`;

  constructor(private apiService: ApiService) { }

  search(keyword: string) {
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
