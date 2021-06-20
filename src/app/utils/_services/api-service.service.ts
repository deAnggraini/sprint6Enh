import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, switchMap, concatMap } from 'rxjs/operators';
import { CommonHttpResponse } from '../http-response';
import { environment } from 'src/environments/environment';
import { AuthModel } from 'src/app/modules/auth/_models/auth.model';
import { ToastService } from './toast.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private authLocalStorageToken = `${environment.appVersion}-${environment.USERDATA_KEY}`;

  constructor(private http: HttpClient, private toast : ToastService) { }

  private getHeaders() {
    let token = 'empty';
    let username = 'empty';
    const str = localStorage.getItem(this.authLocalStorageToken);
    if (str) {
      const auth: AuthModel = JSON.parse(str);
      token = `Bearer ${auth.authToken}`;
      username = auth.username
    }
    return {
      headers: new HttpHeaders(
        {
          'Content-Type': 'application/json',
          'Authorization': token,
          'X-USERNAME': username
        }
      )
    }
  }

  post(url: string, body: any | null, options?: {
    headers?: HttpHeaders | {
      [header: string]: string | string[];
    };
    observe?: 'body';
    params?: HttpParams | {
      [param: string]: string | string[];
    };
    reportProgress?: boolean;
    responseType?: 'json';
    withCredentials?: boolean;
  }): Observable<any> {
    return this.http.post(url, body, this.getHeaders()).pipe(
      concatMap((res: CommonHttpResponse) => {
        if (res.error === true) throw Error(res.msg);
        const { data } = res;
        return of(data);
      }),
      catchError((err) => {
        this.toast.showDanger('Call API error');
        console.error('ApiService', err);
        return of(undefined);
      }),
    );
  }

  get(url: string, options?: {
    headers?: HttpHeaders | {
      [header: string]: string | string[];
    };
    observe?: 'body';
    params?: HttpParams | {
      [param: string]: string | string[];
    };
    reportProgress?: boolean;
    responseType?: 'json';
    withCredentials?: boolean;
  }): Observable<any> {
    return this.http.get(url, this.getHeaders()).pipe(
      concatMap((res: CommonHttpResponse) => {
        if (res.error === true) throw Error(res.msg);
        const { data } = res;
        return of(data);
      }),
      catchError((err) => {
        console.error('ApiService', err);
        return of(undefined);
      }),
    );
  }
}
