import { Component, OnInit, Input } from '@angular/core';
import { StrukturDTO } from 'src/app/modules/_model/struktur.dto';
import { BehaviorSubject } from 'rxjs';

export declare interface ArticleBreadDTO {
  id: number,
  title: string
}

@Component({
  selector: 'pakar-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.scss']
})
export class BreadcrumbsComponent implements OnInit {

  @Input() node: BehaviorSubject<StrukturDTO>;
  @Input() article: ArticleBreadDTO;
  dataList: any[];

  constructor() { }

  ngOnInit(): void {
    if (this.node) {
      this.node.subscribe(node => {
        if (!node) return;
        if (!node.listParent) node.listParent = [];
        this.dataList = [{ uri: '/homepage', title: 'PAKAR' }];
        node.listParent.forEach(d => {
          this.dataList.push({ uri: `/struktur/list/${d.id}`, title: d.title });
        });
        this.dataList.push({ uri: `/struktur/list/${node.id}`, title: node.title });

        if (this.article) {
          this.dataList.push({ uri: `/article/list/${this.article.id}`, title: this.article.title });
        }
      });
    }
  }

}
