import { Injectable, TemplateRef } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  toasts: any[] = [];
  toasts$: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);

  constructor() { }

  show(textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ textOrTpl, ...options });
    this.toasts$.next(this.toasts);
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

  showWarning(msg: string) {
    this.show(msg, { classname: 'bg-warning text-light', delay: 3000 });
  }

  clear() {
    this.toasts = [];
    this.toasts$.next(this.toasts);
  }
}
