export class CommonHttpResponse {
    error?: string;
    msg?: string;
    status?: {
        error: string;
        msg: string;
    }
    data?: any;
    paging?: {
        totalData: number,
        page: number,
        maxPage?: number,
        rowPerPage?: number,
    }
}
