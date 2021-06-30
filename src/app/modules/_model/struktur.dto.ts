export declare interface StrukturDTO {
    id: number,
    name: string,
    title?: string,
    icon: string,
    image: string,
    desc: string,
    edit: boolean,
    uri: string,
    level: number,
    sort: number,
    parent?: number,
    menus?: StrukturDTO[],
    location?: string,
    location_text?: string,
    listParent?: any[]
}

export declare interface StrukturItemDTO {
    id: number,
    name: string,
    title?: string,
    icon: string,
    image: string,
    desc: string,
    edit: boolean,
    uri: string,
    level: number,
    sort: number,
    parent?: number,
    menus?: StrukturDTO[],
    location?: string,
    location_text?: string,
    listParent?: any[]
}