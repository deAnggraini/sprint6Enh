import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StrukturDTO } from '../../_model/struktur.dto';
import { BehaviorSubject } from 'rxjs';
import { StrukturService } from '../../_services/struktur.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  categoryId: number = 0;
  struktur$: BehaviorSubject<StrukturDTO> = new BehaviorSubject<StrukturDTO>(null);
  image_path: string = environment.backend_img;

  constructor(private route: ActivatedRoute, private struktutService: StrukturService) { }

  search() {

  }

  image_uri(image: string) {
    return `url(${this.image_path}/${image})`;
  }

  private loadData() {
    const node = this.struktutService.findNodeById(this.categoryId);
    this.struktur$.next(node);
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.categoryId = params.category;
      this.loadData();
    });
    this.struktutService.categories$.subscribe(() => {
      this.loadData();
    })
  }

}
