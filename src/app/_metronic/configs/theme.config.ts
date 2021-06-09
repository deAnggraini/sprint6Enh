export interface ThemeConfig {
    themeId: string
    header: {
        background: string
    },
    homepage: {
        bg_img_top: string
    }
}

export const DefaultThemeConfig = {
    header: {
        background: '#1995D1',
    },
    homepage: {
        bg_img_top: 'default_top.svg',
    }
}