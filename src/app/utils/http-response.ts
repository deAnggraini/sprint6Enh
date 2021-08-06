export class CommonHttpResponse {
    error?: string;
    msg?: string;
    status?: {
        code: string;
        message: string;
        alert?: string;
        failCount?: number;
    }
    data?: any;
    paging?: {
        totalData: number,
        page: number,
        maxPage?: number,
        rowPerPage?: number,

        /* Pagination DTO */
        // totalData: number;
        // page: number;
        totalPage: number;
        limit: number
        rowPage: number;
    }
}
