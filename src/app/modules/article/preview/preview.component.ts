import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService, UserModel } from '../../auth';
import { StrukturDTO } from '../../_model/struktur.dto';
import { StrukturService } from '../../_services/struktur.service';

@Component({
  selector: 'app-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.scss']
})
export class PreviewComponent implements OnInit {

  categoryId: number = 0;
  struktur$: BehaviorSubject<StrukturDTO> = new BehaviorSubject<StrukturDTO>(null);

  hideTable: boolean = false;

  //form
  dataForm: FormGroup;
  title: string = 'Tahapan';

  user$: Observable<UserModel>;
  aliasName: string = 'AA';
  fullName: string;

  changed_date: Date = new Date();

  //hardcode
  id = 100;


  constructor(
    private route: ActivatedRoute,
    private struktutService: StrukturService,
    private auth: AuthService
  ) {
    this.user$ = this.auth.currentUserSubject.asObservable();
  }

  ngOnInit(): void {
    this.loadData();

    //user
    this.user$.subscribe(u => {
      console.log("user$$$ >>", u );
      const aliasNameArr: string[] = [u.firstname, u.lastname];
      this.aliasName = aliasNameArr.map(d => d ? d[0] : '').join('');
      this.fullName = u.fullname;
    });
    
  }

  private loadData() {
    this.route.params.subscribe(params => {
      this.categoryId = params.category;
      this.loadData();
    });
    const node = this.struktutService.findNodeById(this.categoryId);
    this.struktur$.next(node);
  }
}
