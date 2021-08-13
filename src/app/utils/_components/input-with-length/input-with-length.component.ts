import { Component, OnInit, Input, forwardRef, Output, EventEmitter, ChangeDetectorRef, ElementRef, ViewChild } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { fromEvent } from 'rxjs';
import { map, debounceTime, distinctUntilChanged } from 'rxjs/operators';

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
  @Input() disabled: boolean = false;

  @Output() onChange = new EventEmitter<any>();
  @ViewChild('theInput', { static: true }) theInput: ElementRef;

  _onTouched: (_) => {};
  _onChange: (_) => {};

  value: string;

  constructor(private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    fromEvent(this.theInput.nativeElement, 'keyup')
      .pipe(
        map((event: any) => {
          return event.target.value;
        }),
        debounceTime(300),
        distinctUntilChanged()
      )
      .subscribe((text: string) => {
        this.onChange.emit(text);
        this._onTouched(text);
      });
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
