import { Component, OnInit, OnDestroy, ChangeDetectorRef, ViewChildren, QueryList, TemplateRef, ViewChild } from '@angular/core';
import { PaginationModel } from 'src/app/utils/_model/pagination';
import { ArticleService } from '../../_services/article.service';
import { Subscription, of } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from './sortable.directive';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { Router } from '@angular/router';
import { catchError, map } from 'rxjs/operators';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';

export declare interface MyPageRowItem {
  type: string,
  id: number,
  title: string,
  location: string,
  modified_date: Date,
  modified_by: string,
  approved_date?: Date,
  approved_by?: string,
  affective_date?: Date,
  send_to?: string,
  current_by: string,
  state: string,
  isNew: boolean
}

interface TabDTO {
  dataList: MyPageRowItem[],
  pagination: PaginationModel,
  sort?: SortEvent
}
interface FormBean {
  approved: TabDTO,
  pending: TabDTO,
  draft: TabDTO,
}
const PAGE_LIMIT = 10;
const EMPTY_FORM_BEAN: FormBean = {
  approved: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'desc' }
  },
  pending: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'desc' }
  },
  draft: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'desc' }
  },
}

@Component({
  selector: 'app-my-pages',
  templateUrl: './my-pages.component.html',
  styleUrls: ['./my-pages.component.scss']
})
export class MyPagesComponent implements OnInit, OnDestroy {

  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  @ViewChild('riwayatVersiModal') riwayatVersiModal: TemplateRef<any>;

  subscriptions: Subscription[] = [];
  dataForm: FormBean = JSON.parse(JSON.stringify(EMPTY_FORM_BEAN));
  activeId: number = 2;
  listHeader: string[] = ['approved', 'pending', 'draft'];
  listStatus: string[] = ['PUBLISHED', 'PENDING', 'DRAFT'];

  // table
  tableHeader = {
    draft: ['', 'Judul', 'Lokasi', 'Modifikasi Terakhir', 'Modifikasi Oleh', 'Sedang di Edit Oleh', ''],
    pending: ['', 'Judul', 'Lokasi', 'Modifikasi Terakhir', 'Reviewer/Publisher', 'Sedang di Edit Oleh', ''],
    approved: ['', 'Judul', 'Lokasi', 'Tanggal Approve', 'Approver', 'Tanggal Berlaku', ''],
  }
  tableColumn = {
    draft: ['type', 'title', 'location', 'modified_date', 'modified_by', 'current_by', ''],
    pending: ['type', 'title', 'location', 'modified_date', 'send_to', 'current_by', ''],
    approved: ['type', 'title', 'location', 'approved_date', 'approved_by', 'affective_date', ''],
  }

  // filter component
  keyword: string = '';
  type: string = 'ALL';
  selectedTr: { key: string, i: number } = { key: null, i: -1 };
  selectedItem: MyPageRowItem;

  constructor(
    private cdr: ChangeDetectorRef,
    private articleService: ArticleService,
    private confirm: ConfirmService,
    private router: Router,
    private modalService: NgbModal,
    private configModel: NgbModalConfig) {

    this.listStatus['approved'] = "PUBLISHED";
    this.listStatus['pending'] = "PENDING";
    this.listStatus['draft'] = "DRAFT";

    this.configModel.backdrop = 'static';
    this.configModel.keyboard = false;

  }

  ngOnInit(): void {
    this.onSearch(null);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  getIcon(type: string) {
    switch (type.toLowerCase()) {
      case 'article': return 'article.svg';
      case 'microinformation': return 'vp-1.svg';
      case 'atribut': return 'vp-2.svg';
      case 'formulir': return 'formulir.svg';
    }
    return "doc-empty.svg"
  }

  getEmptyMessage(key: string) {
    if (this.keyword) return 'Halaman yang Kamu cari tidak dapat ditemukan';
    let msg = '';
    switch (key) {
      case 'draft': msg = 'edit'; break;
      case 'pending': msg = 'review'; break;
      case 'approved': msg = 'publish'; break;
    }
    return `Kamu tidak memiliki halaman yang dalam proses <i>${msg}</i>.`;
  }

  setPage(key: string, item: TabDTO, page: number) {
    this.search(key, this.listStatus[key], this.type, page);
  }

  // search data
  onRefreshTable() {
    const activeId = this.activeId;
    const key = this.listHeader[activeId];
    const tabDto: TabDTO = this.dataForm[key];
    const paging = tabDto.pagination;
    this.search(key, this.listStatus[key], this.type, paging.page);
  }
  onSearch(e) {
    const status = this.listStatus;
    const type = this.type;
    this.listHeader.forEach((d, i) => {
      this.search(d, status[i], type);
    });
  }
  search(key: string, status: string, type: string, page: number = 1, limit: number = 10) {
    const tabDto: TabDTO = this.dataForm[key];
    const sort: SortEvent = tabDto.sort;
    this.subscriptions.push(
      this.articleService.searchMyPages(this.keyword, status, type, page, { column: sort.column, sort: sort.direction }, limit).subscribe(resp => {
        if (resp) {
          const { totalElements, totalPages, currentPage } = resp;
          const tabDto: TabDTO = this.dataForm[key];
          tabDto.dataList = resp.list;
          tabDto.pagination = new PaginationModel(currentPage, totalElements, PAGE_LIMIT);
          this.cdr.detectChanges();
        }
      })
    );
  }

  // table event
  onSort(sort: SortEvent) {
    const { column } = sort
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });
    const activeId = this.activeId;
    const key = this.listHeader[activeId];
    const tabDto: TabDTO = this.dataForm[key];
    const paging = tabDto.pagination;
    tabDto.sort = sort;
    this.search(key, this.listStatus[key], this.type, paging.page | 1);
  }

  // table ... event
  onOpenChangeDropdown(isOpen: boolean, key: string, i: number) {
    this.selectedTr = { key, i };
    const _tbody = document.getElementById(`table-${key}`).getElementsByTagName('tbody')[0];
    const _tr = _tbody.getElementsByTagName('tr')[i];
    if (isOpen === false) {
      _tr.classList.remove('is-click');
    } else {
      _tr.classList.add('is-click');
    }
  }
  onClickRevision(item: MyPageRowItem, index: number) {
    this.selectedItem = item;
    this.modalService.open(this.riwayatVersiModal, { size: 'xl' });
    return false;
  }
  onClickEdit(item: MyPageRowItem, index: number) {
    this.confirm.open({
      title: `Ubah Artikel`,
      message: `<p>Apakah Kamu yakin ingin mengubah artikel “<b>${item.title}</b>”?`,
      btnOkText: 'Ubah',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.router.navigate([`/article/form/${item.id}`, { isEdit: true }]);
      }
    });
    return false;
  }
  onClickCancel(item: MyPageRowItem, index: number) {
    this.confirm.open({
      title: `Hapus Artikel`,
      message: `<p>Apakah Kamu yakin ingin menghapus dan membatalkan penambahan artikel “<b>${item.title}</b>”?`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.subscriptions.push(
          this.articleService.cancelArticle(item.id).subscribe(resp => {
            if (resp) this.onRefreshTable();
          })
        );
      }
    });
    return false;
  }
  onClickCancelSend(item: MyPageRowItem, index: number) {
    this.confirm.open({
      title: `Batal Kirim`,
      message: `<p>Apakah kamu ingin membatalkan pengiriman artikel “<b>${item.title}</b>”?`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.subscriptions.push(
          this.articleService.cancelSend(item.id)
            .pipe(
              catchError((err) => {
                this.showErrorModal('Gagal Batal Kirim', 'Batal Kirim tidak dapat dilakukan karena halaman tersebut sedang dalam proses review.');
                return of(null);
              }),
              map(resp => resp)
            )
            .subscribe(resp => {
              if (resp) { this.onRefreshTable(); }
            })
        )
      }
    });
    return false;
  }

  showErrorModal(title: string, message: string) {
    this.confirm.open({
      title,
      message,
      btnOkText: 'OK',
      btnCancelText: ''
    }).then((confirmed) => {
      if (confirmed === true) {
        // do nothing
      }
    });
  }

}
