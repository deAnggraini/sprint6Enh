import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef, forwardRef, HostBinding, OnChanges, SimpleChanges } from '@angular/core';
import { Option } from '../../_model/option';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, FormControl } from '@angular/forms';
// import { isFunction } from 'util';
import { NgbDropdown } from '@ng-bootstrap/ng-bootstrap';

const sampleOptions: Option[] = [
  {
    id: '1', value: '1', text: "Level 1 #1",
    children: [
      { id: '2', value: '1.1', text: "Level 2 #1-1" },
      { id: '3', value: '1.2', text: "Level 2 #1-2" },
      { id: '4', value: '1.3', text: "Level 2 #1-3" },
      { id: '5', value: '1.4', text: "Level 2 #1-4" },
      { id: '6', value: '1.5', text: "Level 2 #1-5" },
    ]
  },
  {
    id: '7', value: '2', text: "Level 1 #2",
    children: [
      { id: '8', value: '2.1', text: "Level 2 #2-1" },
      { id: '9', value: '2.2', text: "Level 2 #2-2" },
      { id: '10', value: '2.3', text: "Level 2 #2-3" },
      { id: '11', value: '2.4', text: "Level 2 #2-4" },
      { id: '12', value: '2.5', text: "Level 2 #2-5" },
    ]
  },
  {
    id: '13', value: '13', text: "Level 1 #3",
    children: [
      { id: '14', value: '3.1', text: "Level 2 #2-1" },
      { id: '15', value: '3.2', text: "Level 2 #2-2" },
      { id: '16', value: '3.3', text: "Level 2 #2-3" },
      { id: '17', value: '3.4', text: "Level 2 #2-4" },
      { id: '18', value: '3.5', text: "Level 2 #2-5" },
    ]
  }
]
const empty = { id: '0', value: '', text: '' };

@Component({
  selector: 'pakar-input-drop-down',
  host: { class: 'hq-test' },
  templateUrl: './input-drop-down.component.html',
  styleUrls: ['./input-drop-down.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputDropDownComponent),
      multi: true
    }
  ]
})
export class InputDropDownComponent implements OnInit, ControlValueAccessor {

  @HostBinding('class.ng-invalid') ngInvalid: any = '';

  @Input() options: Option[];
  @Input() placeholder: string;
  @Input() class: string;
  @Input() hasError: boolean;
  @Output() control: FormControl;
  @Output() onChange = new EventEmitter<any>();

  @ViewChild('comboBoxDrop') comboBoxDrop: NgbDropdown;
  @ViewChild('comboBox') comboBox: ElementRef<HTMLDivElement>;

  selected: Option = { id: '0', value: '', text: '' };
  disabled: boolean = false;

  constructor() { }


  get isValid() {
    if (this.control) {
      return this.control.valid;
    }
    return true;
  }

  openChange(open: boolean) {
    const div = this.comboBox.nativeElement;
    if (div) {
      if (open) {
        div.classList.add('focus');
      } else {
        div.classList.remove('focus');
      }
    }
  }

  onSelect(item: Option) {
    this.selected = item;
    this.onChange.emit(item);
    // if (typeof this.propogateChange === 'function') {
    this.propogateChange(item.id);
    // }
    this.comboBoxDrop.close();
  }

  ngOnInit(): void {
    if (!this.options) {
      this.options = JSON.parse(JSON.stringify(sampleOptions));
    }
    if (!this.placeholder) this.placeholder = 'Silahkan Pilih Salah Satu Data';
  }

  // ControlValueAccessor interface
  propogateChange: (_) => {}
  writeValue(value: any): void {
    if (value) { this.selected = value; }
  }
  registerOnChange(fn: any): void {
    this.propogateChange = fn;
  }
  registerOnTouched(fn: any): void { }
  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

}
