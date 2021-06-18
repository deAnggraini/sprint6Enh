import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaxLengthPipe } from './pipe/max-length.pipe';

@NgModule({
  declarations: [
    MaxLengthPipe
  ],
  imports: [
    CommonModule
  ],
  exports: [
    MaxLengthPipe
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
