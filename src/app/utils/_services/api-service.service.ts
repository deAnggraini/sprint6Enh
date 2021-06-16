import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { CommonHttpResponse } from '../http-response';
import { environment } from 'src/environments/environment';
import { AuthModel } from 'src/app/modules/auth/_models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private authLocalStorageToken = `${environment.appVersion}-${environment.USERDATA_KEY}`;

  constructor(private http: HttpClient) { }

  private getHeaders() {
    let token = 'empty';
    const str = localStorage.getItem(this.authLocalStorageToken);
    if (str) {
      const auth: AuthModel = JSON.parse(str);
      token = `Bearer ${auth.authToken}`;
    }
    return {
      headers: new HttpHeaders(
        {
          'Content-Type': 'application/json',
          'Authorization': token
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
      switchMap((res: CommonHttpResponse) => {
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
      switchMap((res: CommonHttpResponse) => {
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
