import { Injectable } from '@angular/core';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmComponent } from '../_components/confirm/confirm.component';

@Injectable({
  providedIn: 'root'
})
export class ConfirmService {

  constructor(
    private modalService: NgbModal,
    private configModel: NgbModalConfig) {
    this.configModel.backdrop = 'static';
    this.configModel.keyboard = false;
  }

  public open(options: any = {
    title: 'title',
    message: 'message',
    btnOkText: 'OK',
    btnCancelText: 'Cancel'
  }): Promise<boolean> {
    const modal = this.modalService.open(ConfirmComponent, { size: 'md' });
    modal.componentInstance.title = options.title;
    modal.componentInstance.message = options.message;
    modal.componentInstance.btnOkText = options.btnOkText;
    modal.componentInstance.btnCancelText = options.btnCancelText;
    return modal.result;
  }
}
