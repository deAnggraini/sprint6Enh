export class CommonHttpResponse {
    error?: string;
    msg?: string;
    status?: {
        code: string;
        message: string;
    }
    data?: any;
    paging?: {
        totalData: number,
        page: number,
        maxPage?: number,
        rowPerPage?: number,
    }
}
