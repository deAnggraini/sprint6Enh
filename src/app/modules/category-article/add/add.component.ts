import { ChangeDetectorRef, Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { BehaviorSubject, Subscription } from 'rxjs';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { DynamicAsideMenuService } from 'src/app/_metronic/core';
import { StrukturDTO } from '../../_model/struktur.dto';
import { StrukturService } from '../../_services/stuktur.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit, OnDestroy {
  @ViewChild('formAddSection') formAddSection: TemplateRef<any>;
  defaultValue: StrukturDTO = {
    id: 0,
    name: '',
    icon: '',
    image: '',
    desc: '',
    edit: true,
    uri: '',
    level: 1,
    sort: 0
  }
  menuConfig: any;
  subscriptions: Subscription[] = [];
  categories: any[];
  selected$: BehaviorSubject<any> = new BehaviorSubject({});
  categories$: BehaviorSubject<any> = new BehaviorSubject({});
  dataForm: FormGroup;
  hasError: boolean = false;

  imageFile: string;
  iconFile: string;
  iconText: string;
  isEdit: boolean = false;

  constructor(
    private menu: DynamicAsideMenuService,
    private cdr: ChangeDetectorRef,
    private modalService: NgbModal,
    private config: NgbModalConfig,
    private fb: FormBuilder,
    private strukturService: StrukturService,
    private confirm: ConfirmService
  ) {
    this.config.backdrop = 'static';
    this.config.keyboard = false;
  }

  get f() {
    return this.dataForm.controls;
  }

  onImageChange(e, field: string) {
    if (e.target.files && e.target.files.length) {
      const reader = new FileReader();
      const [file] = e.target.files;
      reader.readAsDataURL(file);

      reader.onload = () => {
        if (field == "icon") {
          this.iconFile = reader.result as string;
          this.dataForm.patchValue({
            icon: file
          });
        } else if (field == "image") {
          this.imageFile = reader.result as string;
          this.dataForm.patchValue({
            image: file
          });
        }
      };
    }
  }

  private convertToFormData(): FormData {
    const fd: FormData = new FormData();

    // best practice
    fd.append('id', this.dataForm.value.id.toString());
    fd.append('name', this.dataForm.value.name);
    fd.append('desc', this.dataForm.value.desc);
    fd.append('image', this.dataForm.value.image);
    fd.append('icon', this.dataForm.value.icon);
    fd.append('edit', this.dataForm.value.edit ? "1" : "0");
    fd.append('uri', this.dataForm.value.uri);
    fd.append('level', this.dataForm.value.level.toString());
    fd.append('sort', String(this.categories.length + 1));
    fd.append('parent', "0");

    fd.append('data', JSON.parse(this.dataForm.value)); // <--- yang ini kang jum
    
    return fd;
  }

  save() {
    if (this.dataForm.valid) {
      this.strukturService.save(this.convertToFormData()).subscribe(resp => {
        if (resp) {
          this.menu.addStruktur(resp);
          this.modalService.dismissAll();
        }
      })
    } else {
      this.dataForm.markAllAsTouched();
    }
  }

  showIconName(type: string = "image") {
    if (type == "image") {
      if (typeof (this.dataForm.value.image) === "string") {
        return this.dataForm.value.image;
      } else {
        return this.dataForm.value.image?.name;
      }
    } else {
      if (typeof (this.dataForm.value.icon) === "string") {
        return this.dataForm.value.icon;
      } else {
        return this.dataForm.value.icon?.name;
      }
    }
  }

  open(reset: boolean = true) {
    if (reset) {
      this.resetForm();
      this.isEdit = false;
    }
    this.modalService.open(this.formAddSection);
  }

  editLevel1(data: any) {
    this.iconFile = null;
    this.imageFile = null;
    this.isEdit = true;
    this.dataForm.reset(Object.assign({}, data, { name: data.title }));
    this.open(false);
  }

  deleteLevel1() {
    this.confirm.open({
      title: `Hapus Menu`,
      message: `<p>Apakah Kamu yakin ingin menghapus menu “<b>${this.selected$.value.title}</b>”?</p><p>Seluruh kategori yang terdapat pada menu tersebut akan naik menjadi menu.</p>`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    })
      .then((confirmed) => {
        if (confirmed === true) {
          this.strukturService.delete({ id: this.selected$.value.id }).subscribe(resp => {
            if (resp) {
              this.menu.refreshStruktur(resp);
              this.selected$.next({});
            }
          })
        }
      });
  }

  private initForm() {
    this.dataForm = this.fb.group({
      id: [0],
      name: [this.defaultValue.name, Validators.compose([Validators.required, Validators.maxLength(50)])],
      icon: [this.defaultValue.icon, Validators.compose([Validators.required])],
      image: [this.defaultValue.image, Validators.compose([Validators.required])],
      desc: [this.defaultValue.desc, Validators.compose([Validators.required, Validators.maxLength(200)])],
      edit: [true],
      uri: [''],
      level: [1],
      sort: [0]
    });
  }

  private resetForm() {
    this.dataForm.reset(this.defaultValue);
    this.imageFile = null;
    this.iconFile = null;
  }

  setJsTree(item: any, forceUpdate: boolean = false) {
    if (forceUpdate == false && item.id == this.selected$.value.id) return;
    const found = this.categories.find(d => d.id == item.id);
    if (found) {
      const _found = JSON.parse(JSON.stringify(found)); // agar master tidak ikut berubah
      this.selected$.next(_found);
    }
  }

  ngOnInit(): void {
    this.initForm();
    const menuSubscr = this.menu.menuConfig$.subscribe(res => {
      this.categories = this.menu.categories;
      this.categories$.next(this.categories);
      this.menuConfig = res;

      // ada perubahan data, dan sudah ada yang terselect, harus di update
      if (this.selected$.value.id) {
        this.setJsTree({ id: this.selected$.value.id }, true);
      }
      this.cdr.detectChanges();
    });
    this.subscriptions.push(menuSubscr);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }
}
