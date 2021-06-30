import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TemplateArticleService {

  private _base_url = `${environment.apiUrl}/doc`;
  templates: BehaviorSubject<any[]> = new BehaviorSubject([]);

  constructor(private http: ApiService) { }

  list() {
    const params = {};
    return this.http.post(`${this._base_url}/`, params).subscribe(resp => {
      if (resp) {
        this.templates.next(resp);
      }
    });
  }

  // searching from local variables
  getLocalById(id, templates: any[] = null) {
    if (templates == null) templates = this.templates.value;
    return templates.find(d => d.id == id);
  }
}
