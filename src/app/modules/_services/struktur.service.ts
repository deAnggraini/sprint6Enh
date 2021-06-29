import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { of, Observable, BehaviorSubject } from 'rxjs';
import { StrukturDTO } from '../_model/struktur.dto';

@Injectable({
  providedIn: 'root'
})
export class StrukturService {

  private _base_url = `${environment.apiUrl}/doc`;
  categories$: BehaviorSubject<any[]> = new BehaviorSubject([]);

  constructor(private apiService: ApiService) { }

  list() {
    this.apiService.get(`${this._base_url}/category`)
      .subscribe(
        (resp: any[]) => {
          if (resp)
            this.categories$.next(resp);
        }
      );
  }

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

  /*
   * Fungsional pencarian node di struktur tree, dengan menggunakan rekursif pencarian
   */
  findNodeById(id: number, category: any[] = null): StrukturDTO {
    if (category === null) category = this.categories$.value;
    let found = category.find(d => d.id == id);
    if (found) {
      return found as StrukturDTO;
    }
    category.forEach(d => {
      if (found) return;
      if (d.menus && d.menus.length) {
        found = this.findNodeById(id, d.menus);
      }
    })
    return found;
  }
}
