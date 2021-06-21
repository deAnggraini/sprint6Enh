import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaxLengthPipe } from './pipe/max-length.pipe';
import { ToastsContainer } from './_components/toast/toast-container.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PaginationComponent } from './_components/pagination/pagination.component';

@NgModule({
  declarations: [
    MaxLengthPipe,
    ToastsContainer,
    PaginationComponent,
  ],
  imports: [
    CommonModule,
    NgbModule,
  ],
  exports: [
    MaxLengthPipe,
    ToastsContainer,
    PaginationComponent
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
