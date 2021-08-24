import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit,
} from '@angular/core';
import { LayoutService, LayoutInitService } from '../../_metronic/core';
import KTLayoutContent from '../../../assets/js/layout/base/content';
import { ThemeService } from 'src/app/modules/_services/theme.service';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';
import { PakarStateService, PakarRawState } from 'src/app/modules/_services/pakar-state.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent implements OnInit, AfterViewInit {
  // Public variables
  selfLayout = 'default';
  asideSelfDisplay: true;
  asideMenuStatic: true;
  contentClasses = '';
  contentContainerClasses = '';
  subheaderDisplay = true;
  contentExtended: false;
  asideCSSClasses: string;
  asideHTMLAttributes: any = {};
  headerMobileClasses = '';
  headerMobileAttributes = {};
  footerDisplay: boolean;
  footerCSSClasses: string;
  headerCSSClasses: string;
  headerHTMLAttributes: any = {};
  // offcanvases
  extrasSearchOffcanvasDisplay = false;
  extrasNotificationsOffcanvasDisplay = false;
  extrasQuickActionsOffcanvasDisplay = false;
  extrasCartOffcanvasDisplay = false;
  extrasUserOffcanvasDisplay = false;
  extrasQuickPanelDisplay = false;
  extrasScrollTopDisplay = false;
  @ViewChild('ktAside', { static: true }) ktAside: ElementRef;
  @ViewChild('ktHeaderMobile', { static: true }) ktHeaderMobile: ElementRef;
  @ViewChild('ktHeader', { static: true }) ktHeader: ElementRef;

  // custom header background
  background: string = ''
  popstate: boolean = false;
  // scrolls: any[] = [];

  constructor(
    private initService: LayoutInitService,
    private layout: LayoutService,
    private themes: ThemeService,
    private router: Router,
    private state: PakarStateService
  ) {
    this.initService.init();
  }

  ngOnInit(): void {
    this.background = this.themes.getConfig().header.background;
    // build view by layout config settings
    this.selfLayout = this.layout.getProp('self.layout');
    this.asideSelfDisplay = this.layout.getProp('aside.self.display');
    this.asideMenuStatic = this.layout.getProp('aside.menu.static');
    this.subheaderDisplay = this.layout.getProp('subheader.display');
    this.contentClasses = this.layout.getStringCSSClasses('content');
    this.contentContainerClasses = this.layout.getStringCSSClasses(
      'content_container'
    );
    this.contentExtended = this.layout.getProp('content.extended');
    this.asideHTMLAttributes = this.layout.getHTMLAttributes('aside');
    this.asideCSSClasses = this.layout.getStringCSSClasses('aside');
    this.headerMobileClasses = this.layout.getStringCSSClasses('header_mobile');
    this.headerMobileAttributes = this.layout.getHTMLAttributes(
      'header_mobile'
    );
    this.footerDisplay = this.layout.getProp('footer.display');
    this.footerCSSClasses = this.layout.getStringCSSClasses('footer');
    this.headerCSSClasses = this.layout.getStringCSSClasses('header');
    this.headerHTMLAttributes = this.layout.getHTMLAttributes('header');
    // offcanvases
    if (this.layout.getProp('extras.search.display')) {
      this.extrasSearchOffcanvasDisplay =
        this.layout.getProp('extras.search.layout') === 'offcanvas';
    }

    if (this.layout.getProp('extras.notifications.display')) {
      this.extrasNotificationsOffcanvasDisplay =
        this.layout.getProp('extras.notifications.layout') === 'offcanvas';
    }

    if (this.layout.getProp('extras.quickActions.display')) {
      this.extrasQuickActionsOffcanvasDisplay =
        this.layout.getProp('extras.quickActions.layout') === 'offcanvas';
    }

    if (this.layout.getProp('extras.cart.display')) {
      this.extrasCartOffcanvasDisplay =
        this.layout.getProp('extras.cart.layout') === 'offcanvas';
    }

    if (this.layout.getProp('extras.user.display')) {
      this.extrasUserOffcanvasDisplay =
        this.layout.getProp('extras.user.layout') === 'offcanvas';
    }

    this.extrasQuickPanelDisplay = this.layout.getProp(
      'extras.quickPanel.display'
    );

    this.extrasScrollTopDisplay = this.layout.getProp(
      'extras.scrolltop.display'
    );
  }

  ngAfterViewInit(): void {
    if (this.ktAside) {
      for (const key in this.asideHTMLAttributes) {
        if (this.asideHTMLAttributes.hasOwnProperty(key)) {
          this.ktAside.nativeElement.attributes[key] = this.asideHTMLAttributes[
            key
          ];
        }
      }
    }

    if (this.ktHeaderMobile) {
      for (const key in this.headerMobileAttributes) {
        if (this.headerMobileAttributes.hasOwnProperty(key)) {
          this.ktHeaderMobile.nativeElement.attributes[
            key
          ] = this.headerMobileAttributes[key];
        }
      }
    }

    if (this.ktHeader) {
      for (const key in this.headerHTMLAttributes) {
        if (this.headerHTMLAttributes.hasOwnProperty(key)) {
          this.ktHeader.nativeElement.attributes[
            key
          ] = this.headerHTMLAttributes[key];
        }
      }
    }
    // Init Content
    KTLayoutContent.init('kt_content');

    this.router.events.subscribe(resp => {
      const y = window.scrollY;
      if (resp instanceof NavigationStart) {
        const { id, restoredState, url } = resp;
        // console.log('NavigationStart', JSON.parse(JSON.stringify(resp)));
        this.popstate = resp.navigationTrigger == "popstate";
        if (this.popstate) {
          let oldData: PakarRawState;
          if (restoredState) {
            const { navigationId } = restoredState;
            oldData = this.state.state[navigationId];
          } else if (url) {
            const datas = Object.values(this.state.state);
            oldData = datas.find(d => d.url == url);
            this.state.state[id] = oldData;
          }
          if (!oldData) {
            const keys = Object.keys(this.state.state);
            oldData = this.state.state[keys[0]];
            this.state.state[id] = oldData;
          }
          if (oldData) {
            oldData.url = url
          }
        } else {
          const index = id < 0 ? 0 : id - 1;

          if (!this.state.state[index]) this.state.state[index] = { url: 'kosong', scroll: 0, data: {} };
          this.state.state[index].scroll = y;
          this.state.state[id] = { url, scroll: 0, data: {} };
        }
      } else if (resp instanceof NavigationEnd) {
        const { id, url } = resp;
        const _data = this.state.state[id];
        // console.log('NavigationEnd scolls', this.state);
        // console.log({ id, _data });
        if (_data && _data.url == url) {
          const { scroll } = _data;
          // console.log('go to ', scroll);
          window.scrollTo(0, scroll);
        } else {
          const datas = Object.values(this.state.state);
          const filter = datas.filter(d => d.url == url);
          // console.log({ filter });
          const _data = filter[filter.length - 1];
          const { scroll } = _data;
          // console.log('kosong go to ', scroll);
          window.scrollTo(0, scroll);
        }
      } else {

      }
    });

    this.state.state[1] = { url: this.router.url, scroll: 0, data: {} };
  }
}
