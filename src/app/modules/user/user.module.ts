import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from './user.component';
import { Routes, RouterModule } from '@angular/router';
import { MyPagesComponent } from './my-pages/my-pages.component';
import { GeneralModule } from 'src/app/_metronic/partials/content/general/general.module';
import { CoreModule } from 'src/app/_metronic/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { UtilsModule } from 'src/app/utils/utils.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InlineSVGModule } from 'ng-inline-svg';
import { NgbdSortableHeader } from './my-pages/sortable.directive';
import { NotificationComponent } from './notification/notification.component';
import { ContentComponent } from './content/content.component';

const routes: Routes = [
  {
    path: '',
    component: UserComponent,
    children: [
      { path: 'notification', component: NotificationComponent, },
      { path: 'mypages', component: MyPagesComponent, },
      { path: 'contents', component: ContentComponent, },
      { path: '', redirectTo: 'mypages', pathMatch: 'full' },
      { path: '**', redirectTo: 'mypages', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  declarations: [
    UserComponent,
    MyPagesComponent,
    NgbdSortableHeader,
    NotificationComponent,
    ContentComponent,
  ],
  imports: [
    CommonModule,
    GeneralModule,
    NgbModule,
    CoreModule,
    RouterModule.forChild(routes),
    UtilsModule,
    ReactiveFormsModule,
    FormsModule,
    InlineSVGModule,
  ]
})
export class UserModule { }
