import { Component, OnInit, forwardRef, Input, ChangeDetectorRef, EventEmitter, Output, ViewChild, ElementRef } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { Option } from '../../_model/option';
import { NgbDropdown } from '@ng-bootstrap/ng-bootstrap';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
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
  @Input() options: Option[];
  @Output() onSearch = new EventEmitter<any>();
  @Output() onChange = new EventEmitter<any>();

  @ViewChild('autocompleteDrop') comboBoxDrop: NgbDropdown;
  @ViewChild('autocomplete') comboBox: ElementRef<HTMLDivElement>;
  @ViewChild('inputText', { static: true }) inputText: ElementRef;

  _onTouched: (_) => {};
  _onChange: (_) => {};

  value: string;
  selected: Option = { id: '', text: '', value: '' };
  disabled: boolean = false;
  hasError: boolean = false;

  constructor(private cdr: ChangeDetectorRef) { }

  openChange(open: boolean) {
    const div = this.comboBox.nativeElement;
    if (div) {
      if (open) {
        div.classList.add('focus');
      } else {
        div.classList.remove('focus');
      }
      // this._onTouched(this.value);
    }
  }
  onSelect(item: Option) {
    this.selected = item;
    this.onChange.emit(item);
    this._onChange(item.id);
    this.comboBoxDrop.close();
  }

  ngOnInit(): void {
    fromEvent(this.inputText.nativeElement, 'keyup')
      .pipe(
        map((event: any) => {
          return event.target.value;
        }),
        debounceTime(500),
        distinctUntilChanged()
      )
      .subscribe((text: string) => {
        this.onSearch.emit(text);
        this.comboBoxDrop.open();
      });
  }

  writeValue(obj: any): void {
    console.log('writeValue', { obj });
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
