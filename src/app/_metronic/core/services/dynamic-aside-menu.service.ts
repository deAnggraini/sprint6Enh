import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { DynamicAsideMenuConfig } from '../../configs/dynamic-aside-menu.config';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { environment } from 'src/environments/environment';
import { map, first } from 'rxjs/operators';

interface Menu {
  id?: number,
  root?: boolean,
  bullet?: string,
  title?: string,
  icon?: string,
  svg?: string,
  page?: string,
  submenu?: Menu[],
  section?: string
}

const dummy_category = [
  {
    id: 1,
    title: 'Aplikasi Mesin',
    level: 0,
    desc: '',
    menus: [
      {
        id: 2,
        title: 'Pendukung Transaksi Umum',
        level: 1,
        desc: '',
        menus: [
          {
            id: 121,
            title: 'ABACAS',
            level: 1,
            desc: '',
          },
          {
            id: 122,
            title: 'Aplikasi customized report',
            level: 1,
            desc: '',
          }
        ]
      },
      {
        id: 3,
        title: 'Pendukung Transaksi Internasional',
        level: 1,
        desc: '',
        menus: [
          {
            id: 131,
            title: 'Andy',
            level: 1,
            desc: '',
          },
          {
            id: 132,
            title: 'BDS-OR',
            level: 1,
            desc: '',
          }
        ]
      }
    ]
  },
  {
    id: 11,
    title: 'Others',
    level: 0,
    menus: [
      {
        id: 12,
        title: 'About',
        level: 1,
        desc: '',
      },
      {
        id: 13,
        title: 'Contact',
        level: 1,
        desc: '',
      }
    ]
  },
  {
    id: 21,
    title: 'Test Footer',
    level: 0,
    desc: '',
  }
];

const emptyMenuConfig = {
  items: []
};

@Injectable({
  providedIn: 'root'
})
export class DynamicAsideMenuService {

  private _base_url = `${environment.apiUrl}/master`;
  private menuConfigSubject = new BehaviorSubject<any>(emptyMenuConfig);
  private unsubscribe: Subscription[] = [];
  menuConfig$: Observable<any>;

  constructor(private apiService: ApiService) {
    this.menuConfig$ = this.menuConfigSubject.asObservable();
    // this.loadMenu();
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
      if (item.menus && item.menus.length)
        item.menus.forEach((d: any) => { items.push(readChild(d) as any) });
    })
    return items;
  }

  private populateCategoryArticle() {
    const categories = this.apiService.get(`${this._base_url}/category-article`)
      .subscribe(
        (_articles: any[]) => {
          this.loadMenu(this.parseToMenu(_articles));
          return _articles;
        }
      );
  }

  // Here you able to load your menu from server/data-base/localStorage
  // Default => from DynamicAsideMenuConfig
  private loadMenu(_server) {
    const config = DynamicAsideMenuConfig;
    const items = [].concat(config.items).concat(_server);
    this.setMenu({ items });
  }

  private setMenu(menuConfig) {
    this.menuConfigSubject.next(menuConfig);
  }

  private getMenu(): any {
    return this.menuConfigSubject.value;
  }
}
