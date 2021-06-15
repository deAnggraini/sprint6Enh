import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ArticleService } from 'src/app/modules/_services/article.service';

@Component({
  selector: 'app-popular',
  templateUrl: './popular.component.html',
  styleUrls: ['./popular.component.scss']
})
export class PopularComponent implements OnInit {

  dataList: any[] = [];
  constructor(private articleService: ArticleService, private changeDetectorRef: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.articleService.popular().subscribe(resp => {
      this.dataList = resp;
      setTimeout(() => this.changeDetectorRef.detectChanges(), 0);
    })
  }

}
