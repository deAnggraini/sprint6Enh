import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { of, Observable, BehaviorSubject } from 'rxjs';
import { StrukturDTO } from '../_model/struktur.dto';
import { Option } from 'src/app/utils/_model/option';

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

  // global helper
  public parseToOptions(listStructure: any[]) {
    function parseListParent(item: any): string {
      let parents : string[] = [];
      if (item.listParent && item.listParent.length) {
        parents = parents.concat(item.listParent.map(d => d.title));
      }
      parents.push(item.title);
      return parents.join(' > ');
    }
    function parseItem(structures): Option[] {
      const newOptions: Option[] = [];
      structures.forEach(d => {
        const option: Option = {
          id: d.id,
          value: parseListParent(d),
          text: d.title,
          children: []
        };
        newOptions.push(option);
        if (d.menus && d.menus.length) {
          option.children = parseItem(d.menus);
        }
      });
      return newOptions;
    }
    return parseItem(listStructure);
  }
}
