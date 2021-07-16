import { ChangeDetectorRef, Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { BehaviorSubject, Subscription } from 'rxjs';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { DynamicAsideMenuService } from 'src/app/_metronic/core';
import { StrukturDTO } from '../../_model/struktur.dto';
import { StrukturService } from '../../_services/struktur.service';
import { ToastService } from 'src/app/utils/_services/toast.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit, OnDestroy {
  @ViewChild('formAddSection') formAddSection: TemplateRef<any>;
  @ViewChild('formChangeParent') formChangeParent: TemplateRef<any>;
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
  menuConfig: any = { items: [] };
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

  listToChangeParent: any[] = [];
  listBrothers: any[] = [];
  selectedDelete: any = { id: 0, title: '', name: '' };

  constructor(
    private menu: DynamicAsideMenuService,
    private cdr: ChangeDetectorRef,
    private modalService: NgbModal,
    private config: NgbModalConfig,
    private fb: FormBuilder,
    private strukturService: StrukturService,
    private confirm: ConfirmService,
    private toast: ToastService
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
    const { id } = this.dataForm.value;

    // best practice
    fd.append('id', this.dataForm.value.id.toString());
    fd.append('name', this.dataForm.value.name);
    fd.append('desc', this.dataForm.value.desc);
    if (typeof (this.dataForm.value.image) == "string") {
      // fd.append('image', null);
    } else {
      fd.append('image', this.dataForm.value.image);
    }
    if (typeof (this.dataForm.value.icon) == "string") {
      // fd.append('icon', null);
    } else {
      fd.append('icon', this.dataForm.value.icon);
    }
    fd.append('edit', this.dataForm.value.edit ? "1" : "0");
    fd.append('uri', this.dataForm.value.uri);
    fd.append('level', this.dataForm.value.level.toString());

    // fd.append('sort', String(this.categories.length + 1));

    if (parseInt(id) > 0) {
      fd.append('sort', this.dataForm.value.sort.toString());
    } else {
      const listSort = this.categories.map(d => d.sort);
      const maxSort = Math.max(...listSort) | 0;
      fd.append('sort', (maxSort + 1).toString());
    }

    fd.append('parent', "0");
    fd.append('location', "");
    fd.append('location_text', "");
    return fd;
  }

  save() {
    if (this.dataForm.valid) {
      this.strukturService.save(this.convertToFormData()).subscribe(resp => {
        if (resp) {
          this.menu.refreshStruktur();
          this.modalService.dismissAll();
          this.toast.showSuccess('Simpan Data Berhasil');
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

  private validationChangeParent() {
    let result = false;
    this.listToChangeParent.forEach(d => {
      if (d.changeTo < 1) {
        d.error = true;
        result = true;
      } else d.error = false;
    })
    return result;
  }

  saveChangeParent() {
    if (!this.validationChangeParent()) {
      const { id } = this.selected$.value;
      this.strukturService.delete({ id, changeTo: this.listToChangeParent }).subscribe(resp => {
        if (resp) {
          this.menu.refreshStruktur();
          this.modalService.dismissAll();
          this.toast.showSuccess('Hapus Data Berhasil');
        }
      })
    }
  }

  deleteLevel1() {
    this.confirm.open({
      title: `Hapus Menu`,
      message: `<p>Apakah Kamu yakin ingin menghapus menu “<b>${this.selected$.value.title}</b>”?`,
      //</p><p>Seluruh kategori yang terdapat pada menu tersebut akan naik menjadi menu.</p>`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    })
      .then((confirmed) => {
        if (confirmed === true) {
          const { value } = this.selected$;
          if (value.menus && value.menus.length) {
            this.listToChangeParent = value.menus.map(d => {
              return { id: d.id, title: d.title, changeTo: 0, error: false };
            });
            this.listBrothers = this.categories.filter(d => d.id != value.id).map(d => { return { id: d.id, title: d.title } });
            this.selectedDelete = JSON.parse(JSON.stringify(value));
            this.modalService.open(this.formChangeParent, { size: 'lg' });
          } else {
            this.strukturService.delete({ id: this.selected$.value.id, changeTo: [] }).subscribe(resp => {
              if (resp) {
                this.menu.refreshStruktur();
                this.selected$.next({});
                this.toast.showSuccess('Hapus Data Berhasil');
              }
            })
          }
        }
      });
  }

  changeComboboxParent(value, id) {
    const found = this.listToChangeParent.find(d => d.id == id);
    if (found) {
      found.changeTo = parseInt(value);
    }
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
    } else {
      this.selected$.next({});
    }
  }

  ngOnInit(): void {
    this.initForm();
    const menuSubscr1 = this.strukturService.categories$.subscribe(res => {
      this.categories = res;
      this.categories$.next(this.categories);
      // ada perubahan data, dan sudah ada yang terselect, harus di update
      // if (this.selected$.value.id) {
      this.setJsTree({ id: this.selected$.value.id }, true);
      // }
      // this.cdr.detectChanges();
    });
    const menuSubscr2 = this.menu.menuConfig$.subscribe(res => {
      this.menuConfig = res;
      // this.cdr.detectChanges();
    });
    this.subscriptions.push(menuSubscr1);
    this.subscriptions.push(menuSubscr2);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }
}
