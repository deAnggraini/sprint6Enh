import { Component, OnInit } from '@angular/core';
import { ThemeService } from 'src/app/modules/_services/theme.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  background_image: string;
  background_category_menu: string;

  constructor(private theme: ThemeService) {
    const img = this.theme.getConfig().homepage.bg_img_top;
    const img_url = `${environment.apiUrl}/themes/homepage/${img}`;
    this.background_image = img_url;
    this.background_category_menu = 'eclipse.svg';
  }

  ngOnInit(): void {
  }

}
