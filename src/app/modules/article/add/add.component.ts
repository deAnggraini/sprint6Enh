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

// function alphaNumericValidator(control: FormControl): ValidationErrors | null {
//   const ALPHA_NUMERIC_REGEX = /^(?:[a-zA-Z0-9\s\-\/]+)?$/;
//   return ALPHA_NUMERIC_REGEX.test(control.value) ? null : { alphaNumericError: 'Hanya angka dan huruf yang diperbolehkan' };
// }

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit, OnDestroy {
  defaultValue = {
    title: '',
    structureId: '',
    usedBy: '',
    templateId: '',
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
  paramKey: string = '';

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

  checkUniq(value: string) {
    this.hasError = false;
    if (!value) return;
    const checkUniqSubrcr = this.article.checkUniq(value.trim())
      .pipe(
        catchError((err) => {
          this.hasError = true;
          this.errorMsg = JSON.parse(err.message).message;
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
      const params = Object.assign({}, this.dataForm.value, { paramKey: this.paramKey, paramValue: this.dataForm.value.title });
      this.subscriptions.push(
        this.article.generate(params).subscribe((resp: ArticleDTO) => {
          this.article.formData = resp;
          this.router.navigate([`/article/form/${resp.id}`], { replaceUrl: true });
        })
      );
    } else {
      this.dataForm.markAllAsTouched();
    }
  }

  reset() {
    this.location.back();
    // this.router.navigate(['/homepage']);
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
      title: [this.defaultValue.title, Validators.compose([Validators.required, Validators.maxLength(50)])],
      structureId: [this.defaultValue.structureId, Validators.compose([Validators.required])],
      usedBy: [{ value: this.defaultValue.usedBy, disabled: true }, Validators.compose([Validators.required])],
      templateId: [this.defaultValue.templateId, Validators.compose([Validators.required])]
    });
    // , { updateOn: 'submit' }

    const locationSubrc = this.dataForm.get('structureId').valueChanges.subscribe(d => {
      if (d) {
        this.dataForm.get('usedBy').enable();
      }
    })
    this.subscriptions.push(locationSubrc);

    const templateSubrc = this.dataForm.get('templateId').valueChanges.subscribe(d => {
      if (d) {
        const found = this.listTemplate.find(r => r.id == parseInt(d));
        if (found) {
          this.paramKey = found.contents[0]?.params[0] || '';
          this.preview_img = `${this.backend_img}${found.image}`;
          this.cdr.detectChanges();
        }
      }
    })
    this.subscriptions.push(templateSubrc);
  }

}
