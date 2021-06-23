import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class StrukturService {

  private _base_url = `${environment.apiUrl}/doc`;
  constructor(private apiService: ApiService) { }

  add(data) {
    console.log({ data });
    return this.apiService.post(`${this._base_url}/struktur-save`, data, this.apiService.getHeaders(false));
  }
}
