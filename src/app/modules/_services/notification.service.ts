import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

export declare interface NotificationDTO {
  id: number,
  status: string,
  type: string,
  isUnread: boolean,
  date: Date,
  by: string,
  title: string,
  desc: string,
}

export declare interface ResponseNotificationDTO {
  total_notification: number,
  total_unread: number,
  total_read: number,

  totalElements: number,
  totalPages: number,
  list: NotificationDTO[]
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private _base_url = `${environment.apiUrl}/doc`;
  private notif$: BehaviorSubject<ResponseNotificationDTO> = new BehaviorSubject(null);

  constructor(private api: ApiService) { }

  refresh() {
    this.list().subscribe(resp => {
      console.log('refresh notif', resp);
    })
  }
  list(): Observable<any> {
    return this.api.post(`${this._base_url}/getNotification`, {}).pipe(
      map((resp: ResponseNotificationDTO) => {
        if (resp) this.notif$.next(resp);
        return resp;
      })
    );
  }

  listNotif(keyword: string, page: number = 1, limit: number = 10) {
    const params = { keyword, page, limit };
    return this.api.post(`${this._base_url}/getNotification`, params);
  }

  getNotif(): BehaviorSubject<ResponseNotificationDTO> {
    return this.notif$;
  }

  readAll() {
    return this.api.post(`${this._base_url}/updateStatusNotification`, { id: [], isAll: true });
  }
}
