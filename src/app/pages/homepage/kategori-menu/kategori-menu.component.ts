import { Component, OnInit } from '@angular/core';
import { DynamicAsideMenuService } from 'src/app/_metronic/core';

@Component({
  selector: 'app-kategori-menu',
  templateUrl: './kategori-menu.component.html',
  styleUrls: ['./kategori-menu.component.scss']
})
export class KategoriMenuComponent implements OnInit {

  listSvg: string[] = ['teamwork.svg', 'online-payment1.svg', 'atm.svg'];
  listData: any[] = [{}, {}];
  constructor(private categori: DynamicAsideMenuService) {
    this.categori.getCategory().subscribe(d => {
      this.listData = d;
    })
  }

  ngOnInit(): void {
  }

}
