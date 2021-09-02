import { Component, OnInit, forwardRef, Input, ChangeDetectorRef, EventEmitter, Output, ViewChild, ElementRef } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { Option } from '../../_model/option';
import { NgbDropdown } from '@ng-bootstrap/ng-bootstrap';
import { fromEvent } from 'rxjs';
import { map, debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'pakar-auto-complete',
  templateUrl: './auto-complete.component.html',
  styleUrls: ['./auto-complete.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AutoCompleteComponent),
      multi: true
    }
  ]
})
export class AutoCompleteComponent implements OnInit, ControlValueAccessor {

  @Input() placeholder: string;
  @Input() options: Option[] = [];
  @Input() selected: Option = { id: '', text: '', value: '' };
  @Output() onSearch = new EventEmitter<any>();
  @Output() onChange = new EventEmitter<any>();

  @ViewChild('autocompleteDrop') comboBoxDrop: NgbDropdown;
  @ViewChild('autocomplete') comboBox: ElementRef<HTMLDivElement>;
  @ViewChild('inputText', { static: true }) inputText: ElementRef;

  _onTouched: (_) => {};
  _onChange: (_) => {};

  keyword: string = '';
  value: string;
  disabled: boolean = false;
  hasError: boolean = false;
  isEnter: boolean = false;

  constructor(private cdr: ChangeDetectorRef) { }

  openChange(open: boolean) {
    const div = this.comboBox.nativeElement;
    if (div) {
      if (open) {
        div.classList.add('focus');
      } else {
        div.classList.remove('focus');
        this.isEnter = false;
      }
      // this._onTouched(this.value);
    }
  }
  onBlur(text) {
    this.inputText.nativeElement.value = this.selected.text;
  }
  onReset() {
    this.onSelect({ id: '', text: '', value: '' });
  }
  onSelect(item: Option) {
    this.selected = item;
    this.onChange.emit(item);
    this._onChange(item.id);
    this.comboBoxDrop.close();
    this.cdr.detectChanges();
  }
  keyEnter(e) {
    const _options = this.options;
    if (_options && _options.length) {
      this.selected = { id: '', text: '', value: '' };
      this.onSelect(_options[0]);
    }
    e.stopPropagation();
    e.preventDefault();
    return false;
  }

  ngOnInit(): void {
    fromEvent(this.inputText.nativeElement, 'keyup')
      .pipe(
        map((event: any) => {
          if (event.keyCode == 13) {
            this.isEnter = true;
            return '';
          }
          this.keyword = event.target.value;
          return event.target.value;
        }),
        debounceTime(500),
        distinctUntilChanged()
      )
      .subscribe((text: string) => {
        if (this.isEnter) {
          this.isEnter = false;
          if (this.options.length)
            this.comboBoxDrop.close();
        } else {
          this.onSearch.emit(text);
          this.comboBoxDrop.open();
        }
      });

  }

  writeValue(obj: any): void {
    this.value = obj;
    if (obj) { this.cdr.detectChanges(); }
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
