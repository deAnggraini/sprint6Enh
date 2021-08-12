import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { PaginationModel, PaginationEvent } from '../../_model/pagination';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit, OnChanges, PaginationEvent {

  // parameters
  @Input() page: number; // tidak perlu, sudah terhandle di object PaginationModel
  @Input() paging: PaginationModel;
  @Input() showFirst: boolean = false;
  @Input() showPrev: boolean = true;
  @Input() showNext: boolean = true;
  @Input() showLast: boolean = false;

  // event
  @Output() onChange = new EventEmitter<any>();

  constructor() {
    if (!this.paging) this.paging = new PaginationModel(1, 100);
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  ngOnInit(): void {
    // console.log({ paging : this.paging });
  }

  setPage(page: number): void {
    this.paging.setPage(page);
    this.onChange.emit(page);
  }

}
