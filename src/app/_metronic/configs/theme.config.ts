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
        background: 'red',
    },
    homepage: {
        bg_img_top: 'newsdefault_top.svg',
    }
}