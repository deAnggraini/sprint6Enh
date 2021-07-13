import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaxLengthPipe } from './pipe/max-length.pipe';
import { ToastsContainer } from './_components/toast/toast-container.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PaginationComponent } from './_components/pagination/pagination.component';
import { ConfirmComponent } from './_components/confirm/confirm.component';
import { BreadcrumbsComponent } from './_components/breadcrumbs/breadcrumbs.component';
import { RouterModule } from '@angular/router';
import { PakarSearchComponent } from './_components/pakar-search/pakar-search.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { GeneralModule } from '../_metronic/partials/content/general/general.module';
import { InlineSVGModule } from 'ng-inline-svg';
import { SanitizeHtmlPipe } from './pipe/sanitize-html.pipe';
import { ComboBoxComponent } from './_components/combo-box/combo-box.component';
import { InputDropDownComponent } from './_components/input-drop-down/input-drop-down.component';
import { InputWithLengthComponent } from './_components/input-with-length/input-with-length.component';

@NgModule({
  declarations: [
    MaxLengthPipe,
    ToastsContainer,
    PaginationComponent,
    ConfirmComponent,
    BreadcrumbsComponent,
    PakarSearchComponent,
    SanitizeHtmlPipe,
    ComboBoxComponent,
    InputDropDownComponent,
    InputWithLengthComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    RouterModule,
    InlineSVGModule
  ],
  exports: [
    MaxLengthPipe,
    SanitizeHtmlPipe,
    ToastsContainer,
    PaginationComponent,
    ConfirmComponent,
    BreadcrumbsComponent,
    PakarSearchComponent,
    ComboBoxComponent,
    InputDropDownComponent,
    InputWithLengthComponent
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
