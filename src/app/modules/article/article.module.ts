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
import { RecommendationComponent } from './recommendation/recommendation.component';
import { TerbaruComponent } from './terbaru/terbaru.component';
import { UtilsModule } from 'src/app/utils/utils.module';
import { FormArticleComponent } from './form-article/form-article.component';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { DragDropModule } from '@angular/cdk/drag-drop';
// import { CdkTreeModule } from '@angular/cdk/tree';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatExpansionModule } from "@angular/material/expansion";
import { PreviewComponent } from './preview/preview.component';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { ScrollTopDownComponent } from './scroll-top-down/scroll-top-down.component';

const routes: Routes = [
  {
    path: '',
    component: ArticleComponent,
    children: [
      { path: 'list/:id', component: ListComponent },
      {
        path: 'add', component: AddComponent,
        data: {
          allowedRoles: ['SUPERADMIN', 'ADMIN', 'EDITOR']
        }
      },
      {
        path: 'form', component: FormArticleComponent,
        data: {
          allowedRoles: ['SUPERADMIN', 'ADMIN', , 'EDITOR']
        }
      },
      {
        path: 'form/:id', component: FormArticleComponent,
        data: {
          allowedRoles: ['SUPERADMIN', 'ADMIN', , 'EDITOR']
        }
      },
      {
        path: 'preview/:id', component: PreviewComponent,
        data: {
          allowedRoles: ['SUPERADMIN', 'ADMIN', 'PUBLISHER', 'EDITOR']
        }
      },
      { path: 'search', component: SearchComponent, },
      { path: 'search/:page', component: SearchComponent, },
      { path: 'recommendation', component: RecommendationComponent, },
      { path: 'recommendation/:page', component: RecommendationComponent, },
      { path: 'terbaru', component: TerbaruComponent, },
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
    SearchComponent,
    RecommendationComponent,
    TerbaruComponent,
    FormArticleComponent,
    PreviewComponent,
    ScrollTopDownComponent,
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
    InlineSVGModule,
    UtilsModule,
    CKEditorModule,
    MatExpansionModule,
    DragDropModule,
    // CdkTreeModule,
    MatIconModule,
    MatButtonModule,
    SlickCarouselModule
  ]
})
export class ArticleModule { }
