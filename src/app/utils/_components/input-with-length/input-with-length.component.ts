import { Component, OnInit, Input, forwardRef, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'pakar-input-length',
  templateUrl: './input-with-length.component.html',
  styleUrls: ['./input-with-length.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputWithLengthComponent),
      multi: true
    }
  ]
})
export class InputWithLengthComponent implements OnInit, ControlValueAccessor {

  @Input() placeholder: string;
  @Input() maxlength: number;
  @Input() hasError: boolean;

  @Output() onChange = new EventEmitter<any>();

  _onTouched: (_) => {};
  _onChange: (_) => {};

  value: string;
  disabled: boolean = false;

  constructor(private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
  }

  // ControlValueAccessor interface
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
