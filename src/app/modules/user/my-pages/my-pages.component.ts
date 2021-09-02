import { Component, OnInit, OnDestroy, ChangeDetectorRef, ViewChildren, QueryList, TemplateRef, ViewChild, HostListener } from '@angular/core';
import { PaginationModel } from 'src/app/utils/_model/pagination';
import { ArticleService } from '../../_services/article.service';
import { Subscription, of } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from './sortable.directive';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { Router } from '@angular/router';
import { catchError, map, mergeMapTo } from 'rxjs/operators';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { UserModel } from '../../auth/_models/user.model';
import { environment } from 'src/environments/environment';
import { UserService } from '../../_services/user.service';
import { Option } from 'src/app/utils/_model/option';

export declare interface MyPageRowItem {
  type: string,
  id: number,
  title: string,
  location: string,
  modified_date: Date,
  modified_by: string,
  approved_date?: Date,
  approved_by?: string,
  effective_date?: Date,
  send_to?: string,
  current_by: string,
  state: string,
  isNew: boolean,
  isPublished: boolean
  isClone: boolean
  receiver?: string
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
    sort: { column: 'modified_date', direction: 'asc' }
  },
  pending: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'asc' }
  },
  draft: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'asc' }
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
  @ViewChild('formConfirmDelete') formConfirmDelete: TemplateRef<any>;

  subscriptions: Subscription[] = [];
  dataForm: FormBean = JSON.parse(JSON.stringify(EMPTY_FORM_BEAN));
  activeId: number = 0;
  listHeader: string[] = ['draft', 'pending', 'approved'];
  listStatus: string[] = ['DRAFT', 'PENDING', 'PUBLISHED'];

  // table
  tableHeader = {
    draft: ['', 'Judul', 'Lokasi', 'Modifikasi Terakhir', 'Modifikasi Oleh', 'Sedang di Edit Oleh', ''],
    pending: ['', 'Judul', 'Lokasi', 'Modifikasi Terakhir', 'Reviewer/Publisher', 'Sedang di Edit Oleh', ''],
    approved: ['', 'Judul', 'Lokasi', 'Tanggal Approve', 'Approver', 'Tanggal Berlaku', ''],
  }
  tableColumn = {
    draft: ['type', 'title', 'location', 'modified_date', 'modified_by', 'current_by', ''],
    pending: ['type', 'title', 'location', 'modified_date', 'send_to', 'current_by', ''],
    approved: ['type', 'title', 'location', 'approved_date', 'approved_by', 'effective_date', ''],
  }

  // filter component
  keyword: string = '';
  type: string = 'ALL';
  selectedTr: { key: string, i: number } = { key: null, i: -1 };
  selectedItem: MyPageRowItem;

  //approver delete
  userOptions: Option[] = [];
  delete = {
    sendTo: {
      username: '',
      email: '',
    },
    sendNote: ''
  }
  idSelected: number

  constructor(
    private cdr: ChangeDetectorRef,
    private articleService: ArticleService,
    private confirm: ConfirmService,
    private router: Router,
    private modalService: NgbModal,
    private configModel: NgbModalConfig,
    private userService: UserService) {

    this.listStatus['approved'] = "PUBLISHED";
    this.listStatus['pending'] = "PENDING";
    this.listStatus['draft'] = "DRAFT";

    this.configModel.backdrop = 'static';
    this.configModel.keyboard = false;

  }

  ngOnInit(): void {
    this.onSearch(null);
    this.setScaleValue(window)
  }

  ngOnDestroy(): void {
    this.modalService.dismissAll();
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  setScaleValue(window) {
    let scale = 1
    let scalefont = 1
    if (window.innerWidth < 1340) {
      scalefont = window.innerWidth / 1458
      scale = window.innerWidth / 1458
      document.documentElement.style.setProperty('--scale', `${scale}`);
      document.documentElement.style.setProperty('--scalefont', `${scalefont}`);
      return
    }
    if (window.innerWidth < 1458) {
      scale = window.innerWidth / 1458
    }
    document.documentElement.style.setProperty('--scale', `${scale}`);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.setScaleValue(event.target)
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
  showArticle(id: number) {
    if (id) this.router.navigate([`/article/list/${id}`]);
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
    if (!item.isNew) {
      this.router.navigate([`/article/form/${item.id}`, { isEdit: true }]);
      return
    }
    this.articleService.checkArticleEditing(item.id).subscribe((resp: UserModel[]) => {
      let editingMsg: string = '';
      if (resp && resp.length) {
        editingMsg = '<br><br>';
        editingMsg += `Saat ini artikel sedang di edit juga oleh :
            <ul>`;
        resp.forEach(d => {
          editingMsg += `<li>${d.fullname}</li>`;
        });
        editingMsg += '</ul>'
      }

      this.confirm.open({
        title: `Ubah Artikel`,
        message: `<p>Apakah Kamu yakin ingin mengubah artikel “<b>${item.title}</b>”?${editingMsg}`,
        btnOkText: 'Ubah',
        btnCancelText: 'Batal'
      }).then((confirmed) => {
        if (confirmed === true) {
          const needClone: boolean = item.isPublished === true && item.isClone === false;
          this.router.navigate([`/article/form/${item.id}`, { isEdit: true, needClone }]);
        }
      });
    });
    return false;
  }
  onClickCancel(item: MyPageRowItem, index: number) {
    this.confirm.open({
      title: `Hapus Artikel`,
      message: `<p>Apakah Kamu yakin ingin menghapus artikel “<b>${item.title}</b>”?`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        if (item.isPublished) {
          this.idSelected = item.id
          this.modalService.open(this.formConfirmDelete);
          return
        }
        this.subscriptions.push(
          this.articleService.cancelArticle(item.id).subscribe(resp => {
            if (resp) this.onRefreshTable();
          })
        );
      }
    });
    return false;
  }
  onClickCancelChange(item: MyPageRowItem, index: number) {
    this.confirm.open({
      title: `Batal Ubah Artikel`,
      message: `<p>Apakah Kamu yakin ingin membatalkan perubahan artikel “<b>${item.title}</b>”?`,
      btnOkText: 'Ya, Batal Ubah',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.subscriptions.push(
          this.articleService.cancelEditArticle({ id: item.id, username: this.getUsername() }).subscribe(resp => {
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
      message: `<p>Apakah kamu ingin membatalkan pengiriman artikel “<b>${item.title}</b>” ke “<b>${item.send_to}</b>”?`,
      btnOkText: 'Ya, Batal Kirim',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.subscriptions.push(
          this.articleService.cancelSend(item.id, item.receiver)
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
  // User saerch
  searchUser(keyword) {
    if (keyword) {
      let body = { keyword: keyword, role: 'PUBLISHER', username: this.getUsername() }
      this.subscriptions.push(
        this.userService.searchUserApprover(body).subscribe(resp => {
          if (resp) {
            this.userOptions = this.userService.usersToOptions(resp);
          }
        })
      );
    } else {
      this.userOptions = [];
    }
  }
  userChange(item: Option) {
    this.delete.sendTo.email = item.value;
  }

  onCancelApprover(e) {
    let body = { id: this.idSelected, isHasSend: true, sendTo: this.delete.sendTo, sendNote: this.delete.sendNote }
    this.subscriptions.push(
      this.articleService.deleteArticle(body).subscribe(resp => {
        if (resp) {
          this.modalService.dismissAll();
          this.onRefreshTable();
        }
      })
    )
    return false;
  }

  showErrorModal(title: string, message: string) {
    this.confirm.open({
      title: title,
      message: `<p>${message}`,
      btnOkText: 'OK',
      btnCancelText: ''
    }).then((confirmed) => {
      if (confirmed === true) {
        this.modalService.dismissAll();
      }
    });
  }

  getUsername(): string {
    let username = JSON.parse(localStorage.getItem(`${environment.appVersion}-${environment.USERDATA_KEY}`)).username
    return username
  }


}
