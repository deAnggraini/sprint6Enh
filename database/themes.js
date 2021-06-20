const DefaultThemeConfig = {
    header: {
        background: '#1995D1',
        //background: 'darkblue',
        color: 'white',
        hover: 'red',
    },
    homepage: {
        bg_img_top: 'default_top.svg',
        //bg_img_top: 'news.svg',
        component: [
            'search',
            'category',
            'recommendation',
            'news',
            'popular',
        ]
    }
}

module.exports = DefaultThemeConfig;