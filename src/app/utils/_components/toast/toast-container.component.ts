import { Component, TemplateRef, OnInit, ChangeDetectorRef } from '@angular/core';
import { ToastService } from '../../_services/toast.service';

@Component({
  selector: 'app-toasts',
  template: `
    <ngb-toast
      *ngFor="let toast of toastService.toasts"
      class="p-5"
      [class]="toast.classname"
      [autohide]="true"
      [delay]="toast.delay || 3000"
      (hidden)="toastService.remove(toast)"
    >
      <ng-template [ngIf]="isTemplate(toast)" [ngIfElse]="text">
        <ng-template [ngTemplateOutlet]="toast.textOrTpl"></ng-template>
      </ng-template>

      <ng-template #text><b>{{ toast.textOrTpl }}</b></ng-template>
    </ngb-toast>
  `,
  host: { '[class.ngb-toasts]': 'true' }
})
export class ToastsContainer implements OnInit {

  constructor(public toastService: ToastService,
    private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.toastService.toasts$.subscribe(() => {
      this.cdr.detectChanges();
    });
  }

  isTemplate(toast) {
    return toast.textOrTpl instanceof TemplateRef;
  }
}
