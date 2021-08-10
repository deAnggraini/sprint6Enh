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

  private _base_url = `${environment.apiUrl}/auth`;
  private notif$: BehaviorSubject<ResponseNotificationDTO> = new BehaviorSubject(null);

  constructor(private api: ApiService) { }

  list(): Observable<any> {
    return this.api.post(`${this._base_url}/getNotification`, {}).pipe(
      map((resp: ResponseNotificationDTO) => {
        this.notif$.next(resp);
        return resp;
      })
    );
  }

  getNotif(): BehaviorSubject<ResponseNotificationDTO> {
    return this.notif$;
  }
}
