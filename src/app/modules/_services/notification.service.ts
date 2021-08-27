import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

export declare interface NotificationDTO {
  id: number,
  status: string,
  type: string,
  isRead: boolean,
  date: Date,
  by: string,
  title: string,
  desc: string,
  refId?: string
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
    })
  }
  list(column: string = 'isRead', sort: string = 'desc'): Observable<any> {
    return this.api.post(`${this._base_url}/getNotification`, { keyword: '', page: 1, limit: 3, sorting: { column, sort } }).pipe(
      map((resp: ResponseNotificationDTO) => {
        if (resp) this.notif$.next(resp);
        return resp;
      })
    );
  }

  listNotif(keyword: string, page: number = 1, limit: number = 10, column: string = 'created_date', sort: string = 'desc') {
    const params = { keyword, page, limit, sorting: { column, sort } };
    return this.api.post(`${this._base_url}/getNotification`, params);
  }

  getNotif(): BehaviorSubject<ResponseNotificationDTO> {
    return this.notif$;
  }

  updateTopBarNotif(id: number) {
    const dto: ResponseNotificationDTO = this.notif$.value;
    dto.total_unread -= 1;
    const found = dto.list.find(d => d.id == id);
    if (found) {
      found.isRead = true;
      this.notif$.next(dto);
    } else {
      this.refresh();
    }
  }

  readAll(id: number[] = [], isAll: boolean = true) {
    return this.api.post(`${this._base_url}/updateStatusNotification`, { id, isAll });
  }
}
