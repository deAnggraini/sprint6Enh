import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { DefaultThemeConfig, ThemeConfig } from '../../_metronic/configs/theme.config';
import { of, Subject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private _base_url = `${environment.apiUrl}/doc`;
  private theme: any = DefaultThemeConfig;
  private currentTheme: Subject<ThemeConfig> = new Subject();

  constructor(private apiService: ApiService) {
    // this.populate();
  }

  initialize(): Promise<any> {
    this.populate(); // catch data from server then implement
    return of(DefaultThemeConfig as ThemeConfig)
      .pipe(tap((themeConfig) => this.currentTheme.next(themeConfig)))
      .toPromise(); // default setting
  }

  private populate() {
    this.apiService.get(`${this._base_url}/theme`).subscribe(resp => {
      this.theme = resp;
    })
  }

  getConfig() {
    return this.theme;
  }
}
