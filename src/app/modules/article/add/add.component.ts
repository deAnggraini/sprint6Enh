import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  formControl : FormGroup = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    status: new FormControl(0)
  });
  constructor() { }

  ngOnInit(): void {

  }

}
