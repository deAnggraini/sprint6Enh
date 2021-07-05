export interface ThemeConfig {
    themeId: string
    header: {
        background: string
    },
    homepage: {
        bg_img_top: string
        component: string[]
    },
    login: {
        image: string
    }
}

export const DefaultThemeConfig : ThemeConfig = {
    themeId: 'default',
    header: {
        background: 'red',
    },
    homepage: {
        bg_img_top: '/themes/default_top.svg',
        component: [],
    },
    login: {
        image: '/themes/default_login.svg'
    }
}