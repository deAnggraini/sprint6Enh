import { Component, OnInit } from '@angular/core';
import { DynamicAsideMenuService } from 'src/app/_metronic/core';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-kategori-menu',
  templateUrl: './kategori-menu.component.html',
  styleUrls: ['./kategori-menu.component.scss']
})
export class KategoriMenuComponent implements OnInit {

  backend_img: string = environment.backend_img;
  listData: any[] = [];
  constructor(private categori: DynamicAsideMenuService) {
    this.categori.getCategory().subscribe(d => {
      this.listData = d;
    })
  }

  ngOnInit(): void {
  }

}
