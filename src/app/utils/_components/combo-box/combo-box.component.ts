import { Component, OnInit, Input, OnDestroy, Output, EventEmitter, forwardRef } from '@angular/core';
import { Option } from '../../_model/option';
import { BehaviorSubject, Subscription } from 'rxjs';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { isFunction } from 'util';

const sampleOptions: Option[] = [
  {
    value: '1', text: "Level 1 #1",
    children: [
      { value: '1.1', text: "Level 2 #1-1" },
      { value: '1.2', text: "Level 2 #1-2" },
      { value: '1.3', text: "Level 2 #1-3" },
      { value: '1.4', text: "Level 2 #1-4" },
      { value: '1.5', text: "Level 2 #1-5" },
    ]
  },
  {
    value: '2', text: "Level 1 #2",
    children: [
      { value: '2.1', text: "Level 2 #2-1" },
      { value: '2.2', text: "Level 2 #2-2" },
      { value: '2.3', text: "Level 2 #2-3" },
      { value: '2.4', text: "Level 2 #2-4" },
      { value: '2.5', text: "Level 2 #2-5" },
    ]
  },
  {
    value: '3', text: "Level 1 #3",
    children: [
      { value: '3.1', text: "Level 2 #2-1" },
      { value: '3.2', text: "Level 2 #2-2" },
      { value: '3.3', text: "Level 2 #2-3" },
      { value: '3.4', text: "Level 2 #2-4" },
      { value: '3.5', text: "Level 2 #2-5" },
    ]
  }
]

@Component({
  selector: 'pakar-combo-box-accordion',
  templateUrl: './combo-box.component.html',
  styleUrls: ['./combo-box.component.scss'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => ComboBoxComponent),
    multi: true
  }]
})
export class ComboBoxComponent implements OnInit, OnDestroy, ControlValueAccessor {

  @Input() options: BehaviorSubject<Option[]>;
  @Output() onChange = new EventEmitter<any>();

  selected: Option = { value: '', text: '' };
  hasError: boolean = false;
  subscriptions: Subscription[] = [];

  constructor() { }

  onHidden(target) {

  }

  onShow(target) {

  }

  onClick(item) {
    console.log('click', { item });
    // single click == select, double click == expand content
    this.onSelect(item);
  }

  onSelect(item: Option) {
    console.log('select', { item });
    this.selected = item;
    this.onChange.emit(item);
    if (isFunction(this.propogateChange)) {
      this.propogateChange(item);
    }
  }

  ngOnInit(): void {
    if (!this.options) {
      this.options = new BehaviorSubject(sampleOptions);
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }


  propogateChange: (_) => {}
  // ControlValueAccessor interface
  writeValue(value: any): void {
    console.log('writeValue', { value });
    if (value) this.selected = value;
  }
  registerOnChange(fn: any): void {
    this.propogateChange = fn;
  }
  registerOnTouched(fn: any): void { }
  setDisabledState?(isDisabled: boolean): void {
    throw new Error("Method not implemented.");
  }

}
