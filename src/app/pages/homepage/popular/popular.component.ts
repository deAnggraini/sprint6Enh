import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-popular',
  templateUrl: './popular.component.html',
  styleUrls: ['./popular.component.scss']
})
export class PopularComponent implements OnInit {
  lblKetentuanPopular = String;

  constructor() { }

  ngOnInit(): void {
  }

  buttonPopular(event : any) {
    console.log("event = " , event);
    
  }
}
