import { Component, OnInit, Input, forwardRef } from '@angular/core';
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

  _onTouched: (_) => {};
  _onChange: (_) => {};

  value: string;
  disabled: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  // ControlValueAccessor interface
  writeValue(obj: any): void {
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
