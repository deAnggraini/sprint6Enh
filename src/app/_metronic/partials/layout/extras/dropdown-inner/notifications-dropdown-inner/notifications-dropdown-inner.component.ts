import { Component, OnInit, OnDestroy } from '@angular/core';
import { NotificationService, NotificationDTO } from 'src/app/modules/_services/notification.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-notifications-dropdown-inner',
  templateUrl: './notifications-dropdown-inner.component.html',
  styleUrls: ['./notifications-dropdown-inner.component.scss'],
})
export class NotificationsDropdownInnerComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  dataList: NotificationDTO[] = [];

  constructor(
    private notifService: NotificationService,
    private router: Router) { }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  ngOnInit(): void {
    this.subscriptions.push(
      this.notifService.getNotif().subscribe(resp => {
        if (resp) this.dataList = resp.list.slice(0, 3);
      })
    );
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

  readAll(_) {
    this.subscriptions.push(
      this.notifService.readAll().subscribe(_ => {
        this.notifService.refresh()
      })
    );
    return false;
  }

  gotoNotif(item: NotificationDTO) {
    if (!item.isRead) {
      this.subscriptions.push(
        this.notifService.readAll([item.id], false).subscribe(_ => {
          item.isRead = true;
          this.notifService.updateTopBarNotif(item.id);
          this.gotoUrl(item);
          // this.cdr.detectChanges();
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
      if (item.type.toLowerCase() == "Artikel") {
        this.router.navigate([`/article/list/${item.refId}`, { isEdit: true, contentId: 0 }]);
      }
    } else {
    }
  }
}
