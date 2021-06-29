import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaxLengthPipe } from './pipe/max-length.pipe';
import { ToastsContainer } from './_components/toast/toast-container.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PaginationComponent } from './_components/pagination/pagination.component';
import { ConfirmComponent } from './_components/confirm/confirm.component';
import { BreadcrumbsComponent } from './_components/breadcrumbs/breadcrumbs.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    MaxLengthPipe,
    ToastsContainer,
    PaginationComponent,
    ConfirmComponent,
    BreadcrumbsComponent,
  ],
  imports: [
    CommonModule,
    NgbModule,
    RouterModule
  ],
  exports: [
    MaxLengthPipe,
    ToastsContainer,
    PaginationComponent,
    ConfirmComponent,
    BreadcrumbsComponent
  ]
})
export class UtilsModule {
  static forRoot(): ModuleWithProviders<UtilsModule> {
    return {
      ngModule: UtilsModule,
      providers: []
    };
  }
}
