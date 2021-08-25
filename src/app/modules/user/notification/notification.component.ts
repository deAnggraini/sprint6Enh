import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { NotificationDTO, NotificationService } from '../../_services/notification.service';
import { Subscription } from 'rxjs';
import { PaginationModel } from 'src/app/utils/_model/pagination';
import { Router } from '@angular/router';

const REQ_LIMIT = 5;

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit, OnDestroy {

  keyword: string = '';
  dataList: NotificationDTO[] = [];
  unread: number = 0;
  subscriptions: Subscription[] = [];
  pagination: PaginationModel = new PaginationModel(1, 0, REQ_LIMIT);

  constructor(private notifService: NotificationService,
    private cdr: ChangeDetectorRef,
    private router: Router) { }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  ngOnInit(): void {
    this.onSearch('', 1);
  }

  getIcon(item: NotificationDTO) {
    switch (item.status) {
      case 'informasi': return 'notif-info.svg';
      case 'konflik': return 'notif-conflict.svg';
      case 'terima': return 'notif-to-me.svg';
      case 'publikasi': return 'notif-delete.svg';
      case 'edit': return 'notif-to-me.svg';
    }
    return 'notif-info.svg';
  }

  setPage(page: number) {
    this.onSearch(this.keyword, page);
  }
  doSearch(value: string) {
    this.onSearch(this.keyword, 1);
  }
  onSearch(keyword: string, page: number, updateTopBar: boolean = false, limit: number = REQ_LIMIT) {
    this.subscriptions.push(
      this.notifService.listNotif(keyword, page, limit).subscribe((resp) => {
        if (resp) {
          this.dataList = resp.list;
          this.unread = resp.total_unread;
          this.pagination = new PaginationModel(page, resp.totalElements, REQ_LIMIT);
          if (updateTopBar) this.notifService.getNotif().next(resp);
        } else {
          this.dataList = [];
          this.unread = 0;
          this.pagination = new PaginationModel(page, 0, REQ_LIMIT);
        }
        this.cdr.detectChanges();
      })
    );
  }
  readAll(_) {
    this.subscriptions.push(
      this.notifService.readAll().subscribe(_ => {
        this.onSearch(this.keyword, this.pagination.page, true);
      })
    );
    return false;
  }
  gotoNotif(item: NotificationDTO) {
    if (!item.isRead) {
      this.subscriptions.push(
        this.notifService.readAll([item.id], false).subscribe(_ => {
          item.isRead = true;
          this.unread -= 1;
          this.notifService.updateTopBarNotif(item.id);
          this.gotoUrl(item);
          this.cdr.detectChanges();
        })
      );
    } else {
      this.gotoUrl(item);
    }
    return false;
  }
  private gotoUrl(item: NotificationDTO) {
    const { status } = item;
    if (status.toLowerCase() == "ubah" || status.toLowerCase() == "terima") {
      if (item.type.toLowerCase() == "artikel") {
        this.router.navigate([`/article/list/${item.refId}`, { isEdit: true, contentId: 0 }]);
      }
    } else {
    }
  }

}
