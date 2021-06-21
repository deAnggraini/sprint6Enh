import { Injectable, TemplateRef } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  toasts: any[] = [];

  constructor() { }

  show(textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ textOrTpl, ...options });
  }

  remove(toast) {
    this.toasts = this.toasts.filter(t => t !== toast);
  }

  showSuccess(msg: string) {
    this.show(msg, { classname: 'bg-success text-light', delay: 3000 });
  }

  showDanger(msg: string) {
    this.show(msg, { classname: 'bg-danger text-light', delay: 3000 });
  }
}
