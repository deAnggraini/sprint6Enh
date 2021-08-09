import { Component, OnInit, OnDestroy, ChangeDetectorRef, ViewChildren, QueryList } from '@angular/core';
import { PaginationModel } from 'src/app/utils/_model/pagination';
import { ArticleService } from '../../_services/article.service';
import { Subscription } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from './sortable.directive';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';

export declare interface MyPageRowItem {
  type: string,
  id: number,
  title: string,
  location: string,
  modified_date: Date,
  modified_by: string,
  current_by: string,
  state: string,
  isNew: boolean

}

interface MyPageFiter {
  keyword: string,
  status: string
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
const PAGE_LIMIT = 5;
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

  subscriptions: Subscription[] = [];
  dataForm: FormBean = JSON.parse(JSON.stringify(EMPTY_FORM_BEAN));
  activeId: number = 2;
  listHeader: string[] = ['approved', 'pending', 'draft'];
  listStatus: string[] = ['PUBLISHED', 'PENDING', 'DRAFT'];

  // filter component
  keyword: string = '';
  type: string = 'ALL';
  selectedTr: { key: string, i: number } = { key: null, i: -1 };

  constructor(
    private cdr: ChangeDetectorRef,
    private articleService: ArticleService,
    private confirm: ConfirmService) {
    this.listStatus['approved'] = "PUBLISHED";
    this.listStatus['pending'] = "PENDING";
    this.listStatus['draft'] = "DRAFT";
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
        console.log({ resp });
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
    this.search(key, this.listStatus[key], this.type, paging.page);
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
    console.log('onClickRevision', { item, index });
    return false;
  }
  onClickEdit(item: MyPageRowItem, index: number) {
    console.log('onClickEdit', { item, index });
    this.confirm.open({
      title: `Ubah Artikel`,
      message: `<p>Apakah Kamu yakin ingin mengubah artikel “<b>${item.title}</b>”?`,
      btnOkText: 'Ubah',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
      }
    });
    return false;
  }
  onClickCancel(item: MyPageRowItem, index: number) {
    console.log('onClickCancel', { item, index });
    this.confirm.open({
      title: `Hapus Artikel`,
      message: `<p>Apakah kamu yakin ingin menghapus artike “<b>${item.title}</b>”?`,
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
