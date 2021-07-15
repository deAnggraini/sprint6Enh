import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { DefaultThemeConfig, ThemeConfig } from '../../_metronic/configs/theme.config';
import { of, Subject, BehaviorSubject, Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';

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

  initialize(): Observable<any> {
    this.populate();
    return this.apiService.get(`${this._base_url}/theme`).pipe(map((theme: ThemeConfig) => {
      if (theme) {
        this.theme = theme;
        this.currentTheme.next(this.theme);
        this.homepageComponent$.next(this.theme.homepage.component);
      }
      return theme;
    }));
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
