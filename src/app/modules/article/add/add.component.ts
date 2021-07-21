import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ValidationErrors, FormControl } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { Location } from '@angular/common';
import { Subscription, BehaviorSubject, of } from 'rxjs';
import { TemplateArticleService } from '../../_services/template-article.service';
import { StrukturService } from '../../_services/struktur.service';
import { Option } from 'src/app/utils/_model/option';
import { Router } from '@angular/router';
import { ArticleService } from '../../_services/article.service';
import { catchError, map } from 'rxjs/operators';
import { ArticleDTO } from './../../_model/article.dto';

function alphaNumericValidator(control: FormControl): ValidationErrors | null {
  const ALPHA_NUMERIC_REGEX = /^(?:[a-zA-Z0-9\s\-\/]+)?$/;
  return ALPHA_NUMERIC_REGEX.test(control.value) ? null : { alphaNumericError: 'Hanya angka dan huruf yang diperbolehkan' };
}

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit, OnDestroy {
  defaultValue = {
    title: '',
    location: '',
    usedBy: '',
    template: '',
  }
  backend_img = environment.backend_img;
  preview_img: string;
  subscriptions: Subscription[] = [];

  // state
  dataForm: FormGroup;
  hasError: boolean = false;
  errorMsg: string = '';
  disabledUsedBy: boolean = true;
  listTemplate: any[] = [];
  locationOptions: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  usedByOptions: Option[] = [
    { id: "1", value: "Nasabah", text: 'Nasabah' },
    { id: "2", value: "Internal", text: 'Internal' },
    { id: "3", value: "Nasabah & Internal", text: 'Nasabah & Internal' },
  ];

  constructor(
    private cdr: ChangeDetectorRef,
    private location: Location,
    private fb: FormBuilder,
    private templateService: TemplateArticleService,
    private struktur: StrukturService,
    private article: ArticleService,
    private router: Router) { }

  get f() {
    return this.dataForm.controls;
  }

  checkUniq(value) {
    const checkUniqSubrcr = this.article.checkUniq(value.trim())
      .pipe(
        catchError((err) => {
          // this.dataForm.controls['title'].setAsyncValidators() //.updateValueAndValidity();
          this.hasError = true;
          this.errorMsg = err;
          return of(null);
        }),
        map(resp => resp),
      )
      .subscribe(
        resp => {
          if (resp === false) {
            this.hasError = false;
            this.errorMsg = '';
          }
          this.cdr.detectChanges();
        }
      );
    this.subscriptions.push(checkUniqSubrcr);
  }

  save() {
    if (this.dataForm.valid && !this.hasError) {
      this.article.formParam = this.dataForm.value;
      const params = Object.assign({}, this.dataForm.value, { paramKey: '[Nama Produk]', paramValue: this.dataForm.value.title });
      this.subscriptions.push(
        this.article.generate(params).subscribe((resp: ArticleDTO) => {
          this.article.formData = resp;
          this.router.navigate(['/article/form']);
        })
      );
    } else {
      this.dataForm.markAllAsTouched();
    }
  }

  reset() {
    this.location.back();
  }

  changeLocation(item) {
    this.disabledUsedBy = false;

    const templateSubrc = this.templateService.findByCategories(item.id).subscribe(resp => {
      this.listTemplate = resp;
      this.cdr.detectChanges();
    });
    this.subscriptions.push(templateSubrc);
  }

  getPreviewImage(url): string {
    return `${this.backend_img}${url}`;
  }

  ngOnInit(): void {
    this.initForm();
    const categoriesSribe = this.struktur.categories$.subscribe(resp => {
      if (resp) {
        this.locationOptions.next(this.struktur.parseToOptions(resp));
        this.cdr.detectChanges();
      }
    });
    this.subscriptions.push(categoriesSribe);
    this.struktur.list();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }

  private initForm() {
    this.dataForm = this.fb.group({
      id: [0],
      title: [this.defaultValue.title, Validators.compose([Validators.required, Validators.maxLength(50), alphaNumericValidator])],
      location: [this.defaultValue.location, Validators.compose([Validators.required])],
      usedBy: [{ value: this.defaultValue.usedBy, disabled: true }, Validators.compose([Validators.required])],
      template: [this.defaultValue.template, Validators.compose([Validators.required])]
    });
    const locationSubrc = this.dataForm.get('location').valueChanges.subscribe(d => {
      if (d) {
        this.dataForm.get('usedBy').enable();
      }
    })
    this.subscriptions.push(locationSubrc);
    const templateSubrc = this.dataForm.get('template').valueChanges.subscribe(d => {
      if (d) {
        const found = this.listTemplate.find(r => r.id == parseInt(d));
        if (found) {
          this.preview_img = `${this.backend_img}${found.image}`;
          this.cdr.detectChanges();
        }
      }
    })
    this.subscriptions.push(templateSubrc);
  }

}
