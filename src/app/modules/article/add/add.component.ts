import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Option } from 'src/app/utils/_model/option';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {
  defaultValue = {
    title: '',
    location: '',
    usedBy: '',
    template: '',
  }
  backend_img = environment.backend_img;

  // state
  dataForm: FormGroup;
  hasError: boolean = false;
  location1: Option;

  constructor(
    private fb: FormBuilder) { }

  get f() {
    return this.dataForm.controls;
  }

  save() {

  }

  check() {
    this.dataForm.markAllAsTouched();
    console.log({ location1: this.location1, errors: this.dataForm.errors, isValid: this.dataForm.valid, data: this.dataForm.value });
  }

  changeLocation1(e) {
    console.log({ e });
    this.dataForm.setValue(Object.assign({}, this.dataForm.value, { location: e.text }));
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.dataForm = this.fb.group({
      id: [0],
      title: [this.defaultValue.title, Validators.compose([Validators.required])],
      location: [this.defaultValue.location, Validators.compose([Validators.required])],
      usedBy: [this.defaultValue.usedBy, Validators.compose([Validators.required])],
      template: [this.defaultValue.template, Validators.compose([Validators.required])],
    });
  }

}
