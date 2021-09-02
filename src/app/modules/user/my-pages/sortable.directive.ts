import { Directive, EventEmitter, Input, Output, OnInit } from '@angular/core';
// import { MyPageRowItem } from './my-pages.component';

// export type SortColumn = keyof MyPageRowItem | '';
export type SortDirection = 'asc' | 'desc' | '';
const rotate: { [key: string]: SortDirection } = { 'asc': 'desc', 'desc': '', '': 'asc' };

export interface SortEvent {
    column: string;
    direction: SortDirection;
}

@Directive({
    selector: 'th[sortable]',
    host: {
        '[class.asc]': 'direction === "asc"',
        '[class.desc]': 'direction === "desc"',
        '(click)': 'rotate()'
    }
})
export class NgbdSortableHeader implements OnInit {

    @Input() sortable: string = '';
    @Input('direction') direction: SortDirection = '';
    @Input() key: string = '';
    @Output() sort = new EventEmitter<SortEvent>();

    constructor() {
    }

    ngOnInit() {
    }

    rotate() {
        this.direction = rotate[this.direction];
        const result: SortEvent = { column: this.sortable, direction: this.direction };
        this.sort.emit(result);
    }
}