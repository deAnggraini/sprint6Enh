import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { concatMap, catchError } from 'rxjs/operators';
import { CommonHttpResponse } from '../http-response';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

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
    return this.http.post(url, body, this.httpOptions).pipe(
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
