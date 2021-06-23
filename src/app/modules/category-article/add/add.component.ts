import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { DynamicAsideMenuService } from 'src/app/_metronic/core';
import { Subscription, BehaviorSubject } from 'rxjs';
import { NgbModal, ModalDismissReasons, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { StrukturService } from '../../_services/stuktur.service';

interface DTO {
  id: number,
  name: string,
  icon: string,
  image: string,
  desc: string
}

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit, OnDestroy {
  defaultValue: DTO = {
    id: 0,
    name: '',
    icon: '',
    image: '',
    desc: ''
  }
  menuConfig: any;
  subscriptions: Subscription[] = [];
  categories: any;
  selected$: BehaviorSubject<any> = new BehaviorSubject({});
  dataForm: FormGroup;
  hasError: boolean = false;

  imageFile: string;
  iconFile: string;

  constructor(
    private menu: DynamicAsideMenuService,
    private cdr: ChangeDetectorRef,
    private modalService: NgbModal,
    private config: NgbModalConfig,
    private fb: FormBuilder,
    private strukturService: StrukturService
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
    fd.append('name', this.dataForm.value.name);
    fd.append('desc', this.dataForm.value.desc);
    fd.append('image', this.dataForm.value.image);
    fd.append('icon', this.dataForm.value.icon);
    return fd;
  }

  save() {
    this.strukturService.add(this.convertToFormData()).subscribe(resp => {
      console.log({ resp });
      if (resp) {
        this.modalService.dismissAll();
      }
    })
  }

  open(content) {
    this.resetForm();
    this.modalService.open(content);
  }

  private initForm() {
    this.dataForm = this.fb.group({
      name: [this.defaultValue.name, Validators.compose([Validators.required, Validators.maxLength(50)])],
      icon: [this.defaultValue.icon, Validators.compose([Validators.required])],
      image: [this.defaultValue.image, Validators.compose([Validators.required])],
      desc: [this.defaultValue.desc, Validators.compose([Validators.required, Validators.maxLength(200)])],
    });
  }

  private resetForm() {
    this.dataForm.reset(this.defaultValue);
  }

  setJsTree(item: any) {
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
      this.menuConfig = res;
      this.cdr.detectChanges();
    });
    this.subscriptions.push(menuSubscr);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }
}
