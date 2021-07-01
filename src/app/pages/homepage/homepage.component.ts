import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ThemeService } from 'src/app/modules/_services/theme.service';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  background_image: string;
  background_category_menu: string;
  sortingList$: Observable<string[]>;

  constructor(private theme: ThemeService, private changeDetectorRef: ChangeDetectorRef) {
    const img = this.theme.getConfig().homepage.bg_img_top;
    const img_url = `${environment.backend_img}${img}`;
    this.background_image = img_url;
    this.background_category_menu = 'eclipse.svg';
    this.sortingList$ = this.theme.homepageComponent$.asObservable();
  }

  ngOnInit(): void {
    this.sortingList$.subscribe(sorting => {
    })
  }

}
