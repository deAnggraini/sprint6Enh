import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';

@Injectable({
  providedIn: 'root'
})
export class SkReferenceService {

  private _base_url = `${environment.apiUrl}/doc`;

  constructor(private apiService: ApiService) { }

  search(keyword: string) {
    return this.apiService.post(`${this._base_url}/searchSkRefference`, { keyword });
  }
}
