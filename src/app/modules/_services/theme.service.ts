import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { DefaultThemeConfig, ThemeConfig } from '../../_metronic/configs/theme.config';
import { of, Subject, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private _base_url = `${environment.apiUrl}/v1/doc`;
  private theme: ThemeConfig = DefaultThemeConfig;
  currentTheme: Subject<ThemeConfig> = new Subject();
  homepageComponent$: BehaviorSubject<string[]> = new BehaviorSubject([]);

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
      if (resp) {
        this.theme = resp as ThemeConfig;
        this.currentTheme.next(this.theme);
        this.homepageComponent$.next(this.theme.homepage.component);
      }
    })
  }

  getConfig() {
    return this.theme;
  }
}
