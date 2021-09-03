import { Component, OnInit, Input, Output, EventEmitter, forwardRef, ViewChild, ElementRef, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Option } from '../../_model/option';
import { NgbDropdown } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subject, of, BehaviorSubject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap, switchMap } from 'rxjs/operators';

const sample = [{
  id: '1', value: 'text', text: '025/SKSE/TL/2020 - Perilhal Ketentuan Tahapan'
}];

@Component({
  selector: 'pakar-multi-select',
  templateUrl: './multi-select.component.html',
  styleUrls: ['./multi-select.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => MultiSelectComponent),
      multi: true
    }
  ]
})
export class MultiSelectComponent implements OnInit, ControlValueAccessor, OnDestroy {

  @Input() class: string;
  @Input() placeholder: string;
  @Input() hasError: boolean;
  @Input() maxSelected: number;
  @Input() dataList: BehaviorSubject<Option[]>;
  @Output() onChange = new EventEmitter<any>();
  @Output() getData = new EventEmitter<any>();
  @Output() onSelect = new EventEmitter<any>();

  // ControlValueAccessor propogate
  _onTouched: (_) => {};
  _onChange: (_) => {};

  @ViewChild('multiSelectDropDown') dropDown: NgbDropdown;
  @ViewChild('multiSelect') comboBox: ElementRef<HTMLDivElement>;

  subscriptions: Subscription[] = [];
  value: Option[];
  disabled: boolean = false;
  searching: boolean = false;

  search: string;
  onChangeKeyword = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap((keyword) => {
        if (keyword) {
          this.searching = true;
          this.search = keyword;
          this.dropDown.open();
        } else {
          this.dropDown.close();
        }
      }),
      switchMap(term => {
        if (term) {
          this.getData.emit(term);
        } else {
          this.dataList.next([]);
        }
        return of([]);
      }
      ),
    )

  constructor(private cdr: ChangeDetectorRef) { }

  cancel(item) {
    this.value = this.value.filter(d => d.id != item.id);
    this._onChange(this.value);
    return false;
  }

  private isMaxSelected(): boolean {
    const result = this.value.length >= this.maxSelected;
    if (result) {
      alert(`Maksimal data selected adalah ${this.maxSelected}`);
    }
    return result;
  }
  private add(item) {
    this.value.push(item);
    this._onChange(this.value);
    this.onSelect.emit({ item, keyword: this.search });
    return false;
  }

  innetHtml(text) {
    const keyword = this.search;
    const regEx = new RegExp(this.search, "ig");
    const replace = `<span class="fount-text">${keyword}</span>`;
    return text.replace(regEx, replace);
  }

  // checkbox event
  setChecked(item: Option): boolean {
    return this.value.find(d => d.id == item.id) ? true : false;
  }
  onClickCheck(e, item) {
    const isChecked: boolean = e.target.checked;
    if (isChecked && this.isMaxSelected()) {
      e.preventDefault();
      return false;
    }
    return true;
  }
  onChangeCheck(e, item) {
    const isChecked: boolean = e.target.checked;
    if (isChecked) {
      this.add(item);
    } else {
      this.cancel(item);
    }
    this.search = '';
    return true;
  }

  onFocusSearch(value) {
    if (this.search) this.dropDown.open();
  }

  ngbDropDownOpenChange(open: boolean) {
    const div = this.comboBox.nativeElement;
    if (div) {
      if (open) {
        div.classList.add('focus');
      } else {
        div.classList.remove('focus');
        this.search = '';
      }
    }
  }

  keyEnter(_) {
    const _dataList = this.dataList.value;
    if (_dataList && _dataList.length) {
      const first = _dataList[0];
      const found = this.value.find(d => d.id == first.id);
      if (found) return; // sudah terselect abaikan
      if (first && this.isMaxSelected()) {
        // sudah max selected abaikan
      } else {
        this.add(first);
      }
    }
  }

  ngOnInit(): void {
    if (!this.placeholder) this.placeholder = 'search type here';
    if (!this.dataList) this.dataList = new BehaviorSubject([]);
    if (!this.maxSelected) this.maxSelected = 100;
    this.subscriptions.push(
      this.dataList.subscribe(resp => {
        this.searching = false;
        this.cdr.detectChanges();
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  // ControlValueAccessor interface
  writeValue(obj: any[]): void {
    this.value = obj;
  }
  registerOnChange(fn: any): void {
    this._onChange = fn;
  }
  registerOnTouched(fn: any): void {
    this._onTouched = fn;
  }
  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

}
