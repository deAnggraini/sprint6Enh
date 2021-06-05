let articles = [
  {
    id: 1,
    title: 'Aplikasi Mesin',
    level: 0,
    menus: [
      {
        id: 2,
        title: 'Pendukung Transaksi Umum',
        level: 1,
        bullet: 'dot',
        icon: 'flaticon2-list-2',
        svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
        page: '/article/list/2',
        menus: [
          {
            id: 121,
            title: 'ABACAS',
            level: 1,
            bullet: 'dot',
            icon: 'flaticon2-list-2',
            svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
            page: '/article/list/121',
          },
          {
            id: 122,
            title: 'Aplikasi customized report',
            level: 1,
            bullet: 'dot',
            icon: 'flaticon2-list-2',
            svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
            page: '/article/list/122',
          }
        ]
      },
      {
        id: 3,
        title: 'Pendukung Transaksi Internasional',
        level: 1,
        bullet: 'dot',
        icon: 'flaticon2-list-2',
        svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
        page: '/article/list/3',
        menus: [
          {
            id: 131,
            title: 'Andy',
            level: 1,
            bullet: 'dot',
            icon: 'flaticon2-list-2',
            svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
            page: '/article/list/131',
          },
          {
            id: 132,
            title: 'BDS-OR',
            level: 1,
            bullet: 'dot',
            icon: 'flaticon2-list-2',
            svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
            page: '/article/list/132',
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
        bullet: 'dot',
        icon: 'flaticon2-list-2',
        svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
        page: '/article/list/11',
      },
      {
        id: 13,
        title: 'Contact',
        level: 1,
        bullet: 'dot',
        icon: 'flaticon2-list-2',
        svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
        page: '/article/list/13',
      }
    ]
  },
  {
    id: 21,
    title: 'Test Footer',
    level: 0,
  }
];
const temp = {
  items: [
    {
      title: 'Homepage',
      root: true,
      icon: 'flaticon2-architecture-and-city',
      svg: './assets/media/svg/icons/Home/Home.svg',
      page: '/homepage',
      bullet: 'dot',
    },
    {
      title: 'Daskboard',
      root: true,
      icon: 'flaticon2-architecture-and-city',
      svg: './assets/media/svg/icons/Shopping/Chart-line1.svg',
      page: '/dashboard',
      bullet: 'dot',
    },

    { section: 'Produk Untuk Nasabah' },
    {
      title: 'Produk Dana',
      root: true,
      bullet: 'dot',
      icon: 'flaticon2-list-2',
      svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
      page: '/article',
      submenu: [
        {
          title: 'Deposito',
          bullet: 'dot',
          page: '/article/list/deposito',
        },
        {
          title: 'Giro',
          bullet: 'dot',
          page: '/article/list/giro',
        },
        {
          title: 'LAKU',
          bullet: 'dot',
          page: '/article/list/laku',
        },
      ]
    },
    {
      title: 'Produk Investasi & Asuransi',
      bullet: 'dot',
      icon: 'flaticon2-list-2',
      svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
      page: '/article',
      submenu: [
        {
          title: 'Bancassurance',
          bullet: 'dot',
          page: '/article/list/Bancassurance',
        },
        {
          title: 'DBMM',
          bullet: 'dot',
          page: '/article/list/DBMM',
        },
        {
          title: 'Reksadana',
          bullet: 'dot',
          page: '/article/list/Reksadana',
        },
        {
          title: 'Obligasi Korporasi',
          bullet: 'dot',
          page: '/article/list/Obligasi Korporasi',
        },
        {
          title: 'Obligasi Negara',
          bullet: 'dot',
          page: '/article/list/Obligasi Negara',
          submenu: [
            {
              title: 'Obligasi Negara Ritel (ORI)',
              bullet: 'dot',
              page: '/article/list/Obligasi Negara Ritel (ORI)',
            },
            {
              title: 'Obligasi Negara Valas (INDON)',
              bullet: 'dot',
              page: '/article/list/Obligasi Negara Valas (INDON)',
            }
          ]
        },
        {
          title: 'Sertifikat Berharga BI',
          bullet: 'dot',
          page: '/article/list/Sertifikat Berharga BI',
          submenu: [
            {
              title: 'Surat Berharga BI dalam Valas (SBBI Valas)',
              bullet: 'dot',
              page: '/article/list/Surat Berharga BI dalam Valas (SBBI Valas)',
            },
            {
              title: 'Sertifikat Bank Indonesia (SBI)',
              bullet: 'dot',
              page: '/article/list/Sertifikat Bank Indonesia (SBI)',
            }
          ]
        },
      ]
    },

    { section: 'Aktivitas Cabang' },
    {
      title: 'Pengetahuan Perbankan',
      root: true,
      bullet: 'dot',
      icon: 'flaticon2-list-2',
      svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
      page: '/article',
      submenu: [
        {
          title: 'Penjaminan Simpanan',
          bullet: 'dot',
          page: '/article/list/Penjaminan Simpanan',
        },
        {
          title: 'Transparansi Informasi',
          bullet: 'dot',
          page: '/article/list/Transparansi Informasi',
        },
        {
          title: 'Pembukaan Rahasia Bank',
          bullet: 'dot',
          page: '/article/list/Pembukaan Rahasia Bank',
        },
      ]
    },
    {
      title: 'Layanan Khusus di Cabang',
      bullet: 'dot',
      icon: 'flaticon2-list-2',
      svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
      page: '/article',
      submenu: [
        {
          title: 'BCA Bizz',
          bullet: 'dot',
          page: '/article/list/BCA Bizz',
        },
        {
          title: 'Kantor Kas',
          bullet: 'dot',
          page: '/article/list/Kantor Kas',
        },
        {
          title: 'BCA Ekspress',
          bullet: 'dot',
          page: '/article/list/BCA Ekspress',
        },
      ]
    },
    {
      title: 'Pelaku cabang',
      bullet: 'dot',
      icon: 'flaticon2-list-2',
      svg: './assets/media/svg/icons/Layout/Layout-right-panel-2.svg',
      page: '/article',
      submenu: [
        {
          title: 'CS',
          bullet: 'dot',
          page: '/article/list/CS',
          submenu: [
            {
              title: 'Pembukaan Tahapan',
              bullet: 'dot',
              page: '/article/list/Pembukaan Tahapan',
            },
            {
              title: 'Pembukaan Tahapan Expresi',
              bullet: 'dot',
              page: '/article/list/Pembukaan Tahapan Expresi',
            }
          ]
        },
        {
          title: 'Teller',
          bullet: 'dot',
          page: '/article/list/Teller',
        },
        {
          title: 'AO SME',
          bullet: 'dot',
          page: '/article/list/AO SME',
        },
        {
          title: 'AO Komersial',
          bullet: 'dot',
          page: '/article/list/AO Komersial',
        },
        {
          title: 'RO',
          bullet: 'dot',
          page: '/article/list/RO',
        }
      ]
    },
  ]
};

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

(() => {
  const dumm_template: Menu = {
    // title: '',
    // root: false,
    // submenu: [],
  }
  const parseItem = (item: any): Menu => {
    const { id, title, icon, svg, page } = item;
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
  articles.map(item => {
    temp.items.push({ section: item.title });
    if (item.menus && item.menus.length) item.menus.forEach(d => { temp.items.push(readChild(d) as any) });
  })
})();

export const DynamicAsideMenuConfig = temp;
