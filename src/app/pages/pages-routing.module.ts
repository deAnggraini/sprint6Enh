import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './_layout/layout.component';
import { HomepageComponent } from './homepage/homepage.component';
import { AuthGuard } from '../modules/auth/_services/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    canActivateChild: [AuthGuard],
    children: [
      {
        path: 'homepage',
        component: HomepageComponent
      },
      {
        path: 'article',
        loadChildren: () =>
          import('../modules/article/article.module').then((m) => m.ArticleModule),
      },
      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then((m) => m.DashboardModule),
      },
      {
        path: 'builder',
        loadChildren: () => import('./builder/builder.module').then((m) => m.BuilderModule),
        data: {
          allowedRoles: ['SUPERADMIN', 'ADMIN', 'PUBLISHER']
        }
      },
      {
        path: '',
        redirectTo: '/homepage',
        pathMatch: 'full',
      },
      {
        path: '**',
        redirectTo: 'error/404',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule { }
