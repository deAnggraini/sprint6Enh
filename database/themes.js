const DefaultThemeConfig = {
    header: {
        background: '#1995D1',
        //background: 'darkblue',
        color: 'white',
        hover: 'red',
    },
    homepage: {
        bg_img_top: '/themes/default_top.svg',
        //bg_img_top: 'news.svg',
        component: [
            'search',
            'category',
            'recommendation',
            'news',
            'popular',
        ]
    },
    login: {
        image: '/themes/default_login.svg'
    }
}

module.exports = DefaultThemeConfig;