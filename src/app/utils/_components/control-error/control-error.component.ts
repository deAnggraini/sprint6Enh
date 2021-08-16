import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormControl, ValidationErrors } from '@angular/forms';

export function alphaNumericValidator(control: FormControl): ValidationErrors | null {
  const ALPHA_NUMERIC_REGEX = /^(?:[a-zA-Z0-9\s\-\/]+)?$/;
  return ALPHA_NUMERIC_REGEX.test(control.value) ? null : { alphaNumericError: 'Hanya angka dan huruf yang diperbolehkan' };
}

@Component({
  selector: 'pakar-control-error',
  templateUrl: './control-error.component.html',
  styleUrls: ['./control-error.component.scss']
})
export class ControlErrorComponent implements OnInit, OnChanges {

  @Input() control: FormControl;
  @Input() alias: string;

  defaultError: any[] = [
    { key: 'required', msg: 'wajib diisi.', replace: false },
    { key: 'minlength', msg: 'panjang minimal {$}.', replace: 'control.errors.maxlength.requiredLength' },
    { key: 'maxlength', msg: 'panjang maksimal {$}.', replace: 'control.errors.maxlength.requiredLength' },
    { key: 'pattern', msg: 'pattern tidak diperbolehkan.', replace: false },
    { key: 'alphaNumericError', msg: 'Hanya angka dan huruf yang diperbolehkan.', replace: false },
  ];

  constructor() {
  }

  getMessage(): string {
    const keys = Object.keys(this.control.errors);
    const found = this.defaultError.find(d => d.key == keys[0]);
    if (found) {
      if (found.replace !== false) {
        return `${this.alias || ''} ${found.msg.replace('{$}', eval(found.replace))}`.trim();
      }
      return `${this.alias || ''} ${found.msg}`.trim();
    }
    return 'error ditemukan.';
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

}
