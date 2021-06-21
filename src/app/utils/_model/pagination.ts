
export class PaginationModel {
    totalData: number;
    page: number;
    totalPage: number;
    rowPerPage: number
    rowPage: number;

    constructor(page, totalData, rowPerPage = 10, rowPage = 10) {
        this.totalData = totalData;
        this.page = page;
        this.rowPerPage = rowPerPage;
        this.rowPage = rowPage;

        this.calculate();
    }

    private calculate() {
        this.totalPage = Math.ceil(this.totalData / this.rowPerPage);
        if (this.page < 0) this.page = 1;
        if (this.page > this.totalData) this.page = this.totalPage;

        // calculate row page
        if (this.rowPerPage > this.totalPage) this.rowPage = this.totalPage;
    }

    public next(): number {
        if (this.page == this.totalPage) return this.totalPage;
        this.page += 1;
        return this.page;
    }

    public prev(): number {
        if (this.page == 1) return this.page;
        this.page -= 1;
        return this.page;
    }

    public first(): number {
        this.page = 1;
        return this.page;
    }

    public last(): number {
        this.page = this.totalPage;
        return this.page;
    }

    public setPage(page): void {
        if(page < 1) page = 1;
        if(page > this.totalPage) page = this.totalPage;
        this.page = page;
    }
}