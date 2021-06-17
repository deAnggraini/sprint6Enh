import { Component, OnInit } from '@angular/core';
import { LayoutService } from 'src/app/_metronic/core';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  today: Date = new Date();

  constructor(private layoutService: LayoutService) {
    this.layoutService.setConfig(null);
  }

  ngOnInit(): void {

  }

}
