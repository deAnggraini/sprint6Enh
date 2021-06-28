import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { of, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StrukturService {

  private _base_url = `${environment.apiUrl}/doc`;
  constructor(private apiService: ApiService) { }

  save(data: FormData): Observable<any> {
    if (parseInt(data.get('id').toString()) > 0) {
      return this.update(data);
    } else {
      return this.add(data);
    }
  }

  private add(data: FormData) {
    return this.apiService.post(`${this._base_url}/saveStructure`, data, this.apiService.getHeaders(false));
  }

  private update(data: FormData) {
    return this.apiService.post(`${this._base_url}/updateStructure`, data, this.apiService.getHeaders(false));
  }

  delete(data: any) {
    return this.apiService.post(`${this._base_url}/deleteStructure`, data);
  }

  updateSection(data: any) {
    return this.apiService.post(`${this._base_url}/saveBatchStructure`, data);
  }
}
