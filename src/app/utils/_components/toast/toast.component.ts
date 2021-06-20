import { Component, OnInit } from '@angular/core';
import { ToastService } from '../../_services/toast.service';
import { NgbToast } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.scss']
})
export class ToastComponent implements OnInit {

  constructor(private toastService: ToastService, private ngToast: NgbToast) { }

  ngOnInit(): void {
  }

  show(msg: string) {
    this.toastService.show(msg);
  }

  showSuccess(msg: string) {
    this.toastService.show(msg, { classname: 'bg-success text-light', delay: 5000 });
  }

  showDanger(msg: string) {
    this.toastService.show(msg, { classname: 'bg-danger text-light', delay: 5000 });
  }

}
