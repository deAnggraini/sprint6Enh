import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddComponent } from './add/add.component';
import { ListComponent } from './list/list.component';
import { Routes, RouterModule } from '@angular/router';
import { CategoryArticleComponent } from './category-article.component';
import { GeneralModule } from 'src/app/_metronic/partials/content/general/general.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CoreModule } from 'src/app/_metronic/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InlineSVGModule } from 'ng-inline-svg';
import { UtilsModule } from 'src/app/utils/utils.module';
import { DetailComponent } from './add/detail/detail.component';

const routes: Routes = [
  {
    path: '',
    component: CategoryArticleComponent,
    children: [
      { path: 'list', component: ListComponent, },
      { path: 'list/:category', component: ListComponent, },
      { path: 'add', component: AddComponent, },
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: '**', redirectTo: 'list', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  declarations: [AddComponent, ListComponent, DetailComponent, CategoryArticleComponent],
  imports: [
    CommonModule,
    GeneralModule,
    NgbModule,
    CoreModule,
    ReactiveFormsModule,
    FormsModule,
    InlineSVGModule,
    UtilsModule,
    RouterModule.forChild(routes),
  ]
})
export class CategoryArticleModule { }
