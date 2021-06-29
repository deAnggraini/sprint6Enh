import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';

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
  }

  // state
  dataForm: FormGroup;
  hasError: boolean = false;

  constructor(
    private fb: FormBuilder) { }

  get f() {
    return this.dataForm.controls;
  }

  save() {

  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.dataForm = this.fb.group({
      id: [0],
      title: [this.defaultValue.title, Validators.compose([Validators.required])],
      location: [this.defaultValue.location, Validators.compose([Validators.required])],
      usedBy: [this.defaultValue.usedBy, Validators.compose([Validators.required])]
    });
  }

}
