import { Component, OnInit, OnDestroy, ViewChildren, QueryList, ChangeDetectorRef, TemplateRef, ViewChild } from '@angular/core';
import { NgbdSortableHeader, SortEvent } from '../my-pages/sortable.directive';
import { Subscription } from 'rxjs';
import { ArticleService } from '../../_services/article.service';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { Router } from '@angular/router';
import { PaginationModel } from 'src/app/utils/_model/pagination';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';

export declare interface ContentRowItem {
  type: string,
  id: number,
  title: string,
  affective_date?: Date,
  modified_by: string,
  modified_date: Date,
  approved_date?: Date,
  approved_by?: string,
  send_to?: string,
  current_by: string,
  state: string,
  isNew: boolean
}

interface TabDTO {
  dataList: ContentRowItem[],
  pagination: PaginationModel,
  sort?: SortEvent
}
interface FormBean {
  all: TabDTO,
  article: TabDTO,
  virtualpage: TabDTO,
  formulir: TabDTO,
}
const PAGE_LIMIT = 10;
const EMPTY_FORM_BEAN: FormBean = {
  all: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'desc' }
  },
  article: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'desc' }
  },
  virtualpage: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'desc' }
  },
  formulir: {
    dataList: [],
    pagination: new PaginationModel(1, 0),
    sort: { column: 'modified_date', direction: 'desc' }
  },
}

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit, OnDestroy {

  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  @ViewChild('riwayatVersiModal') riwayatVersiModal: TemplateRef<any>;

  subscriptions: Subscription[] = [];
  dataForm: FormBean = JSON.parse(JSON.stringify(EMPTY_FORM_BEAN));
  activeId: number = 0;
  listHeader: string[] = ['all', 'article', 'virtualpage', 'formulir'];
  listStatus: string[] = ['ALL', 'article', 'virtualpage', 'formulir'];

  tabHeader = {
    all: 'Semua',
    article: 'Article',
    virtualpage: 'Virtual Page',
    formulir: 'Formulir',
  }

  // table
  tableHeader = {
    all: ['', 'Judul', 'Lokasi', 'Tanggal Berlaku', 'Dimodifikasi Oleh', 'Sedang di Edit Oleh', ''],
    article: ['', 'Judul', 'Lokasi', 'Tanggal Berlaku', 'Dimodifikasi Oleh', 'Sedang di Edit Oleh', ''],
    virtualpage: ['', 'Judul', 'Lokasi', 'Tanggal Berlaku', 'Dimodifikasi Oleh', 'Sedang di Edit Oleh', ''],
    formulir: ['', 'Judul', 'Lokasi', 'Tanggal Berlaku', 'Dimodifikasi Oleh', 'Sedang di Edit Oleh', ''],
  }
  tableColumn = {
    draft: ['type', 'title', 'location', 'modified_date', 'modified_by', 'current_by', ''],
    pending: ['type', 'title', 'location', 'modified_date', 'send_to', 'current_by', ''],
    approved: ['type', 'title', 'location', 'approved_date', 'approved_by', 'affective_date', ''],
  }

  // filter component
  keyword: string = '';
  selectedTr: { key: string, i: number } = { key: null, i: -1 };
  selectedItem: ContentRowItem;

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

  getLabel(type: string) {
    switch (type.toLowerCase()) {
      case 'all': return 'Halaman';
      case 'article': return 'Artikel';
      case 'virtualpage': return 'Virtual Page';
      case 'formulir': return 'Formulir';
    }
    return "doc-empty.svg"
  }

  setPage(key: string, item: TabDTO, page: number) {
    this.search(key, this.listStatus[key], page);
  }

  // search data
  onRefreshTable() {
    const activeId = this.activeId;
    const key = this.listHeader[activeId];
    const tabDto: TabDTO = this.dataForm[key];
    const paging = tabDto.pagination;
    this.search(key, this.listStatus[key], paging.page);
  }
  onSearch(e) {
    const status = this.listStatus;
    this.listHeader.forEach((d, i) => {
      this.search(d, status[i]);
    });
  }
  search(key: string, status: string, page: number = 1, limit: number = 10) {
    const tabDto: TabDTO = this.dataForm[key];
    const sort: SortEvent = tabDto.sort;
    this.subscriptions.push(
      this.articleService.searchContents(this.keyword, status, page, { column: sort.column, sort: sort.direction }, limit).subscribe(resp => {
        if (resp) {
          const { totalElements, currentPage } = resp;
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
    this.search(key, this.listStatus[key], paging.page | 1);
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
  onClickRevision(item: any, index: number) {
    this.selectedItem = item;
    this.modalService.open(this.riwayatVersiModal, { size: 'xl' });
    return false;
  }
  onClickEdit(item: ContentRowItem, index: number) {
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
  onClickCancel(item: ContentRowItem, index: number) {
    this.confirm.open({
      title: `Hapus Artikel`,
      message: `<p>Apakah kamu yakin ingin menghapus artike “<b>${item.title}</b>”?`,
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
  onClickCancelSend(item: ContentRowItem, index: number) {
    this.confirm.open({
      title: `Batal Kirim`,
      message: `<p>Apakah kamu ingin membatalkan pengiriman artikel “<b>${item.title}</b>”?`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.onRefreshTable();
      }
    });
    return false;
  }
}