import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ListComponent } from './list/list.component';
import { AddComponent } from './add/add.component';
import { GeneralModule } from 'src/app/_metronic/partials/content/general/general.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CoreModule } from 'src/app/_metronic/core';
import { ArticleComponent } from './article.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { SearchComponent } from './search/search.component';
import { InlineSVGModule } from 'ng-inline-svg';

const routes: Routes = [
  {
    path: '',
    component: ArticleComponent,
    children: [
      { path: 'list', component: ListComponent, },
      { path: 'list/:category', component: ListComponent, },
      { path: 'add', component: AddComponent, },
      { path: 'search', component: SearchComponent, },
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: '**', redirectTo: 'list', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  declarations: [
    ListComponent,
    AddComponent,
    ArticleComponent,
    SearchComponent
  ],
  imports: [
    CommonModule,
    GeneralModule,
    NgbModule,
    CoreModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    MatInputModule,
    FormsModule,
    InlineSVGModule
  ]
})
export class ArticleModule { }
