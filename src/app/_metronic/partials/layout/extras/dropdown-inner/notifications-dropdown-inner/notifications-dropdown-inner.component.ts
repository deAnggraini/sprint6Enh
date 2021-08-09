import { Component, OnInit, OnDestroy } from '@angular/core';
import { NotificationService, NotificationDTO } from 'src/app/modules/_services/notification.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-notifications-dropdown-inner',
  templateUrl: './notifications-dropdown-inner.component.html',
  styleUrls: ['./notifications-dropdown-inner.component.scss'],
})
export class NotificationsDropdownInnerComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  dataList: NotificationDTO[] = [
    {
      id: 1,
      status: 'informasi',
      type: 'Informasi',
      isUnread: true,
      date: new Date(),
      by: '',
      title: 'PAKAR Info',
      desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
    },
    {
      id: 2,
      status: 'konflik',
      type: 'Artikel',
      isUnread: false,
      date: new Date(),
      by: 'Thalia Sari Landi',
      title: 'Tahapan',
      desc: 'Artikel Tahapan yang sedang kamu ubah sudah rilis versi terbaru. Segera sesuaikan dengan versi terbaru.'
    },
    {
      id: 3,
      status: 'terima',
      type: 'Virtual Pages',
      isUnread: true,
      date: new Date(),
      by: 'Stacia Marella',
      title: 'Joint Account',
      desc: 'Mohon review atas pembuatan Virtual Page.'
    }
  ];

  constructor(private notifService: NotificationService) { }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  ngOnInit(): void {
    this.subscriptions.push(
      this.notifService.getNotif().subscribe(resp => {
        console.log({ resp });
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
}
