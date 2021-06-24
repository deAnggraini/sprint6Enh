import { Component, OnInit } from '@angular/core';
import { LayoutService } from 'src/app/_metronic/core';
import { environment } from 'src/environments/environment';
import { ThemeService } from '../_services/theme.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  today: Date = new Date();
  login_image: string;

  constructor(
    private theme: ThemeService,
    private layoutService: LayoutService
  ) {
    this.layoutService.setConfig(null);
    const img = this.theme.getConfig().login.image;
    const img_url = `${environment.backend_img}/themes/homepage/${img}`;
    this.login_image = img_url;
  }

  ngOnInit(): void {

  }

}
