import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { NotificationDTO, NotificationService } from '../../_services/notification.service';
import { Subscription } from 'rxjs';
import { PaginationModel } from 'src/app/utils/_model/pagination';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit, OnDestroy {

  keyword: string = '';
  dataList: NotificationDTO[] = [];
  subscriptions: Subscription[] = [];
  pagination: PaginationModel = new PaginationModel(1, 0);

  constructor(private notifService: NotificationService,
    private cdr: ChangeDetectorRef) { }

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
  doSearch() {
    this.onSearch(this.keyword, 1);
  }
  onSearch(keyword: string, page: number = 1, limit: number = 10) {
    this.subscriptions.push(
      this.notifService.listNotif(keyword, page, limit).subscribe((resp) => {
        if (resp) {
          this.dataList = resp.list;
          this.pagination = new PaginationModel(page, resp.totalElements);
        }
        this.cdr.detectChanges();
      })
    );
  }

}
