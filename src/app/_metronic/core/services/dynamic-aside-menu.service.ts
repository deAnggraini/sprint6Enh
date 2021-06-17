import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { DynamicAsideMenuConfig } from '../../configs/dynamic-aside-menu.config';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { environment } from 'src/environments/environment';
import { AuthService, UserModel } from 'src/app/modules/auth';

interface Menu {
  id?: number,
  root?: boolean,
  bullet?: string,
  title?: string,
  icon?: string,
  svg?: string,
  page?: string,
  submenu?: Menu[],
  section?: string,
  isFunction?: boolean,
  parent?: Menu,
  showMore?: boolean
}

const emptyMenuConfig = {
  items: []
};

@Injectable({
  providedIn: 'root'
})
export class DynamicAsideMenuService {

  private _base_url = `${environment.apiUrl}/doc`;
  private menuConfigSubject = new BehaviorSubject<any>(emptyMenuConfig);
  private categoriesObject = new BehaviorSubject<any[]>([]);
  private categories$: Observable<any[]>;
  private categories: any[] = [];
  // private unsubscribe: Subscription[] = [];
  menuConfig$: Observable<any>;
  user$: Observable<UserModel>;
  login: UserModel;

  constructor(
    private apiService: ApiService,
    private auth: AuthService
  ) {
    this.auth.currentUserSubject.asObservable().subscribe((user) => {
      this.login = user;
    });
    this.menuConfig$ = this.menuConfigSubject.asObservable();
    this.categories$ = this.categoriesObject.asObservable();
    this.populateCategoryArticle();
  }

  private parseToMenu(articles) {
    const dumm_template: Menu = {
      // title: '',
      // root: false,
      // submenu: [],
    }
    const parseItem = (item: any): Menu => {
      const { id, title, desc } = item;
      const icon = 'flaticon2-list-2';
      const svg = './assets/media/svg/icons/Layout/Layout-right-panel-2.svg';
      const page = `/article/list/${id}`;
      let res: Menu = Object.assign({}, dumm_template, { id, title, icon, svg, page, submenu: [] });
      return res;
    }
    const readChild = (item: any): Menu => {
      if (item.id) {
        const menu = parseItem(item);
        if (item.menus && item.menus.length) {
          item.menus.map((d: any) => {
            const menuItem = readChild(d);
            menu.submenu = menu.submenu.concat(menuItem);
          });
        }
        if (menu.submenu && menu.submenu.length == 0) delete menu.submenu;
        return menu;
      }
      return {};
    }

    // loop top level
    const items = [];
    articles.map(item => {
      items.push({ section: item.title });
      if (item.menus && item.menus.length) {
        const maxLoop = item.showLess ? 2 : item.menus.length;
        for (let i = 0; i < maxLoop; i++) {
          const menu = item.menus[i];
          if (menu) {
            items.push(readChild(menu) as any);
          }
        }
        if (item.menus.length > 2) {
          let title = 'Lihat Lebih Sedikit'
          if (item.showLess) {
            title = 'Lihat Semua';
          }
          items.push({
            isFunction: true,
            title,
            page: '/lihatsemua/title',
            data: item,
          });
        }
      }
    })
    return items;
  }

  private populateCategoryArticle() {
    this.apiService.get(`${this._base_url}/category-article`)
      .subscribe(
        (_articles: any[]) => {
          _articles.map(d => d.showLess = true);
          this.categories = JSON.parse(JSON.stringify(_articles));
          this.categoriesObject.next(this.categories);
          this.loadMenu(this.parseToMenu(_articles));
        }
      );
  }

  private menuByRoles() {
    const { roles } = this.login;
    const config = DynamicAsideMenuConfig;
    if (roles.includes("SUPERADMIN")) {
      return config.super_admin;
    } else if (roles.includes('ADMIN')) {
      return config.super_admin;
    } else if (roles.includes('EDITOR')) {
      return config.editor;
    } else if (roles.includes('PUBLISHER')) {
      return config.publisher;
    }
    return [];
  }

  // Here you able to load your menu from server/data-base/localStorage
  // Default => from DynamicAsideMenuConfig
  private loadMenu(_server) {
    const config = DynamicAsideMenuConfig;
    const items = [].concat(config.items)
      .concat(this.menuByRoles())
      .concat(_server)
      .concat(config.footer)
      .concat({ section: ' ' }) // agar menu tidak terlalu mepet kebawah
      ;
    this.setMenu({ items });
  }

  private setMenu(menuConfig) {
    this.menuConfigSubject.next(menuConfig);
  }

  private getMenu(): any {
    return this.menuConfigSubject.value;
  }

  updateMenu(item: any) {
    const found = this.categories.find(d => d.id == item.data.id);
    if (found) {
      found.showLess = !found.showLess;
    }
    this.loadMenu(this.parseToMenu(this.categories));
  }

  getCategory(): Observable<any> {
    return this.categories$;
  }
}
