export interface PaginationSpring {
    totalElements: number;
    currentPage: number;
    totalPages: number;
    // list: T[],
    limit?: number
}

export interface PaginationSpringDTO<T> {
    totalElements: number;
    currentPage: number;
    totalPages: number;
    list: T[],
    limit?: number
}

export interface PaginationDTO {
    totalData: number;
    page: number;
    totalPage: number;
    limit: number
    rowPage: number;
}

export class PaginationModel implements PaginationDTO {
    totalData: number;
    page: number = 1;
    totalPage: number;
    limit: number
    rowPage: number;
    listPage: number[] = [];
    showMaxPage: boolean = true;

    constructor(page: number, totalData: number, limit = 10, rowPage = 3) {
        this.totalData = totalData;
        this.limit = limit;
        this.rowPage = rowPage;
        this.page = page;
        this.calculate();
    }

    public setShowMaxPage(showMaxPage: boolean): void {
        if (showMaxPage != this.showMaxPage) this.calculateListPage();
    }

    public static createEmpty(): PaginationModel {
        return new PaginationModel(1, 0);
    }

    public static create(resp: PaginationSpring): PaginationModel {
        return new PaginationModel(resp.currentPage, resp.totalElements);
    }

    private calculateListPage() {
        const listPage = [];
        const { page, rowPage, totalPage } = this;
        const mod = page % rowPage;
        const floor = Math.floor(page / rowPage);

        let showMax = true;
        let start = floor * rowPage;
        let end = start + rowPage;

        if (mod == 0) {
            start = (floor - 1) * rowPage;
            end = start + rowPage;
        }
        if (end >= totalPage) {
            end = totalPage;
            showMax = false;
        }

        for (let i = start; i < end; i++) {
            listPage.push(i + 1);
        }
        if (this.showMaxPage && showMax) {
            listPage.push(-1);
            listPage.push(this.totalPage);
        }
        this.listPage = listPage;
    }

    private calculate() {
        this.totalPage = Math.ceil(this.totalData / this.limit);
        if (this.page <= 0) this.page = 1;
        if (this.totalData && this.page > this.totalData) this.page = this.totalPage;
        this.calculateListPage();
    }

    public next(): number {
        // const page = this.page >= this.totalPage ? this.totalPage : this.page + 1;
        return this.setPage(this.page + 1);
    }

    public prev(): number {
        // const page = this.page <= 1 ? 1 : this.page - 1;
        return this.setPage(this.page - 1);
    }

    public first(): number {
        return this.setPage(1);
    }

    public last(): number {
        return this.setPage(this.totalPage);
    }

    public setPage(page): number {
        if (page < 1) page = 1;
        if (page > this.totalPage) page = this.totalPage;
        this.page = page;
        this.calculate();
        return page;
    }
}

export abstract class PaginationEvent {
    abstract setPage(page: number): void;
}