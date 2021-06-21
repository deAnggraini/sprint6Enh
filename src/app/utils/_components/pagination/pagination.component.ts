import { Component, OnInit } from '@angular/core';
import { PaginationModel } from '../../_model/pagination';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

  paging: PaginationModel;

  constructor() { }

  ngOnInit(): void {
    this.paging = new PaginationModel(1, 100);
  }

  setPage(page) {
    this.paging.setPage(page);
  }

}
