import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { DefaultThemeConfig, ThemeConfig } from '../../_metronic/configs/theme.config';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private _base_url = `${environment.apiUrl}/theme`;
  private theme: any = DefaultThemeConfig;

  constructor(private apiService: ApiService) {
    // this.populate();
  }

  initialize(): Promise<any> {
    this.populate(); // catch data from server then implement
    return of(DefaultThemeConfig as ThemeConfig).toPromise(); // default setting
  }

  private populate() {
    this.apiService.get(this._base_url).subscribe(resp => {
      this.theme = resp;
    })
  }

  getConfig() {
    return this.theme;
  }
}
