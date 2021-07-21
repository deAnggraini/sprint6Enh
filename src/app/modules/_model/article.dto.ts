import { Option } from 'src/app/utils/_model/option';

export declare interface SkReferenceDTO {
    id: number,
    title: string,
    no: string
}

export declare interface ArticleRelatedDTO {
    id: number,
    title: string
}

export declare interface ArticleParentDTO {
    id: number,
    title: string,
    no?: string,
}

export declare interface ArticleContentDTO {
    id: number,
    title: string,
    no?: string,
    level: number,
    sort: number,
    parent: number,
    children: ArticleContentDTO[],
    listParent?: ArticleParentDTO[],
}

export declare interface ArticleDTO {
    id: number,
    title: string,
    location: number,
    locationOption?: Option,
    desc: string,
    image: string,
    video: string,
    created_at?: Date,
    created_by?: string,
    contents: ArticleContentDTO[],
    references: SkReferenceDTO[],
    related: ArticleRelatedDTO[],
    suggestions: ArticleRelatedDTO[],
}