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
        bg_img_top: 'newsdefault_top.svg',
        component: [],
    },
    login: {
        image: 'default_login.svg'
    }
}