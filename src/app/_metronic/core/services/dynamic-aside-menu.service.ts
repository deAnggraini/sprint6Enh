import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { DynamicAsideMenuConfig } from '../../configs/dynamic-aside-menu.config';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { environment } from 'src/environments/environment';
import { AuthService, UserModel } from 'src/app/modules/auth';
import { StrukturService } from 'src/app/modules/_services/struktur.service';

// interface Menu {    //old
//   id?: number,
//   root?: boolean,
//   bullet?: string,
//   title?: string,
//   icon?: string,
//   svg?: string,
//   page?: string,
//   submenu?: Menu[],
//   section?: string,
//   isFunction?: boolean,
//   parent?: Menu,
//   showMore?: boolean,
//   level?: number,
// }

interface Menu {
  id?: number,
  title?: string,
  level?: number,
  desc?: string;
  sort?: number;
  parent?: Menu,
  menus?: Menu[],
  icon?: string,
  image?: string;
  uri?: string;
  edit?: boolean;

  submenu?: Menu[],
}

const emptyMenuConfig = {
  items: []
};

@Injectable({
  providedIn: 'root'
})
export class DynamicAsideMenuService {

  private _base_url = `${environment.apiUrl}/doc`;
  private _auth_url = `${environment.apiUrl}/auth`;
  private menuConfigSubject = new BehaviorSubject<any>(emptyMenuConfig);
  private categories$ = new BehaviorSubject<any[]>([]);
  private menus$ = new BehaviorSubject<any[]>([]);
  // private categories$: Observable<any[]>;
  categories: any[] = [];
  menus: any[] = [];
  // private unsubscribe: Subscription[] = [];
  menuConfig$: Observable<any>;
  user$: Observable<UserModel>;
  login: UserModel;

  constructor(
    private apiService: ApiService,
    private auth: AuthService,
    private strukturService: StrukturService,
  ) {
    this.auth.currentUserSubject.asObservable().subscribe((user) => {
      this.login = user;
    });
    this.menuConfig$ = this.menuConfigSubject.asObservable();
    // this.categories$ = this.categoriesObject.asObservable();

    this.strukturService.categories$.subscribe(
      (_articles: any[]) => {
        if (_articles) {
          _articles.map(d => d.showLess = true);
          this.categories = JSON.parse(JSON.stringify(_articles));
          this.categories$.next(this.categories);
          // this.loadMenu(this.parseToMenu(_articles));
        }
      });

    this.populateCategoryArticle();
    this.populateMenus();
  }

  private parseToMenu(articles) {
    const dumm_template: Menu = {
      // title: '',
      // root: false,
      // submenu: [],
    }
    const parseItem = (item: any): Menu => {
      const { id, title, desc, icon, uri, level } = item;
      const svg = './assets/media/svg/icons/Navigation/Angle-right.svg';
      let res: Menu = Object.assign({}, dumm_template, { id, title, icon, svg, uri, submenu: [], level });
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
      // items.push({ title: item.title, section: item.title, id: item.id, level: 0, root: true, edit: item.edit });
      items.push(item);
      // Add item.id condition exclude menu top and bottom
      if (item.menus && item.menus.length && item.edit) {
        const maxLoop = item.showLess ? 2 : item.menus.length;
        for (let i = 0; i < maxLoop; i++) {
          const menu = item.menus[i];
          if (menu) {
            items.push(readChild(menu) as any);
          }
        }
        if (item.menus.length > 2) {
          let title = 'Tutup ' + item.title;
          if (item.showLess) {
            title = 'Lihat Semua ' + item.title;
          }
          items.push({
            isFunction: true,
            title,
            page: '/lihatsemua/title',
            data: item,
            level: -1
          });
        }
      }
    })
    return items;
  }

  private populateMenus() {
    // codeing disini
    this.apiService.get(`${this._auth_url}/menu`).subscribe(
      (_menus: any[]) => {
        if (_menus) {
          _menus.map(d => d.showLess = true);
          this.menus = JSON.parse(JSON.stringify(_menus));
          const parse = this.parseToMenu(_menus);
          this.loadMenu(parse);
        }
      }
    );
  }

  private populateCategoryArticle() {
    this.strukturService.list();
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

  private loadMenu(_server) {

    const items = [].concat(_server);
    this.setMenu({ items });
  }

  public setMenu(menuConfig) {
    this.menuConfigSubject.next(menuConfig);
  }

  private getMenu(): any {
    return this.menuConfigSubject.value;
  }

  updateMenu(item: any) {
    const found = this.menus.find(d => d.id == item.data.id);
    if (found) {
      found.showLess = !found.showLess;
    }
    // this.loadMenu(this.parseToMenu(this.categories));
    this.loadMenu(this.parseToMenu(this.menus));
  }

  getCategory(): BehaviorSubject<any> {
    return this.categories$;
  }

  // addStruktur(newData: any, level: number = 1, parent: any = null) {
  //   newData.showLess = true;
  //   let found = this.categories.find(d => d.id == newData.id);
  //   if (found) {
  //     found.title = newData.title;
  //     found.desc = newData.desc;
  //     found.icon = newData.icon;
  //     found.image = newData.image;
  //   } else {
  //     this.categories.push(newData);
  //   }
  //   this.categories$.next(this.categories);
  //   this.loadMenu(this.parseToMenu(this.categories));
  // }

  private removeStruktur(data: any) {
    this.categories = this.categories.filter(d => d.id != data.id);
    this.categories$.next(this.categories);
    this.loadMenu(this.parseToMenu(this.categories));
  }

  refreshStruktur(dataList: any[] = []) {
    // if (dataList.length) {
    //   dataList.map(d => d.showLess = true);
    //   this.categories = dataList;
    //   this.categories$.next(dataList);
    //   this.loadMenu(this.parseToMenu(dataList));
    // } else {
    this.populateCategoryArticle();
    this.populateMenus();
    // }
  }

}
