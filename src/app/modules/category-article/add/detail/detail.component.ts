import { Component, OnInit, Input, OnDestroy, ChangeDetectorRef, Output, EventEmitter, ViewChild, TemplateRef } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { StrukturDTO } from 'src/app/modules/_model/struktur.dto';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { StrukturService } from 'src/app/modules/_services/stuktur.service';
import { DynamicAsideMenuService } from 'src/app/_metronic/core';

declare var $: any;

@Component({
  selector: 'app-struktur-lvl2-tree',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit, OnDestroy {

  defaultValue: StrukturDTO = {
    id: 0,
    name: '',
    icon: '',
    image: '',
    desc: '',
    edit: true,
    uri: '',
    level: 1,
    sort: 0,
    parent: 0,
    menus: [],
    location: '',
    location_text: ''
  }

  @Output() editLevel1 = new EventEmitter<any>();
  @Output() deleteLevel1 = new EventEmitter<any>();
  @Input() selected: Observable<any>;
  @Input() locationStruktur: Observable<any[]>;

  @ViewChild('formAddChild') formModal: TemplateRef<any>;

  locations: any[] = [];
  dataForm: FormGroup;
  section: StrukturDTO;
  tree_id = "#struktur-tree";
  treeItem$: BehaviorSubject<StrukturDTO[]> = new BehaviorSubject([]);
  datasource: StrukturDTO[];
  isEdit: boolean = false;
  hasError: boolean = false;
  imageFile: string;

  constructor(
    private menu: DynamicAsideMenuService,
    private strukturService: StrukturService,
    private confirm: ConfirmService,
    private cdr: ChangeDetectorRef,
    private modalService: NgbModal,
    private config: NgbModalConfig,
    private fb: FormBuilder) {
    this.config.backdrop = 'static';
    this.config.keyboard = false;
  }

  get f() {
    return this.dataForm.controls;
  }

  onImageChange(e, field: string = 'image') {
    if (e.target.files && e.target.files.length) {
      const reader = new FileReader();
      const [file] = e.target.files;
      reader.readAsDataURL(file);

      reader.onload = () => {
        if (field == "image") {
          this.imageFile = reader.result as string;
          this.dataForm.patchValue({
            image: file
          });
        }
      };
    }
  }

  editSection() {
    this.editLevel1.emit(this.section);
  }

  deleteSection() {
    this.deleteLevel1.emit();
  }

  open(reset: boolean = true) {
    if (reset) {
      this.resetForm();
      this.isEdit = false;
    }
    this.modalService.open(this.formModal);
  }

  edit(data: any) {
    this.imageFile = null;
    this.isEdit = true;
    this.dataForm.reset(Object.assign({}, data, { name: data.title }));
    this.open(false);
  }

  private convertToFormData(): FormData {
    const fd: FormData = new FormData();
    fd.append('id', this.dataForm.value.id.toString());
    fd.append('name', this.dataForm.value.name);
    fd.append('desc', this.dataForm.value.desc);
    fd.append('image', this.dataForm.value.image);
    fd.append('icon', this.dataForm.value.icon);
    fd.append('edit', this.dataForm.value.edit ? "1" : "0");
    fd.append('uri', this.dataForm.value.uri);
    fd.append('level', this.dataForm.value.level.toString());
    fd.append('sort', String(this.section.menus.length + 1));
    fd.append('parent', this.dataForm.value.parent);
    fd.append('location', this.dataForm.value.location);
    fd.append('location_text', this.locations.find(d => d._value == this.dataForm.value.location)._text);
    return fd;
  }

  save() {
    if (this.dataForm.valid) {
      this.strukturService.save(this.convertToFormData()).subscribe(resp => {
        if (resp) {
          this.menu.refreshStruktur();
          this.modalService.dismissAll();
        }
      })
    }
  }


  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.initForm();
    this.datasource = [];
    this.initJsTree();
    this.selected.subscribe(res => {
      this.section = res;
      this.defaultValue.parent = res.id;
      this.defaultValue.level = res.level + 1;

      this.refreshJsTree();
      // this.cdr.detectChanges();
    });
    this.treeItem$.subscribe(res => {
      this.datasource = [];
    });
    this.locationStruktur.subscribe(resp => {
      this.locations = [];
      this.createCategorySingleDimention(resp, []);
    })
  }

  private createCategorySingleDimention(dataList: any[], parent: any[]) {
    if (dataList && dataList.length) {
      dataList.forEach(d => {
        const _clone = JSON.parse(JSON.stringify(d));
        delete _clone.menus;
        _clone._value = parent.concat(_clone).map(d => d.id).join(',');
        _clone._text = parent.concat(_clone).map(d => d.title).join(' > ');
        this.locations.push(_clone);
        this.createCategorySingleDimention(d.menus, JSON.parse(JSON.stringify(parent.concat(_clone))));
      })
    }
  }

  private resetForm() {
    this.dataForm.reset(this.defaultValue);
    this.imageFile = null;
  }

  private initForm() {
    this.dataForm = this.fb.group({
      id: [this.defaultValue.id],
      name: [this.defaultValue.name, Validators.compose([Validators.required, Validators.maxLength(50)])],
      icon: [this.defaultValue.icon],
      image: [this.defaultValue.image, Validators.compose([Validators.required])],
      desc: [this.defaultValue.desc, Validators.compose([Validators.required, Validators.maxLength(200)])],
      edit: [this.defaultValue.edit],
      uri: [this.defaultValue.uri],
      level: [this.defaultValue.level],
      sort: [this.defaultValue.sort],
      parent: [this.defaultValue.parent],
      menus: [this.defaultValue.menus],
      location: [this.defaultValue.location, Validators.compose([Validators.required])]
    });
  }

  private parseChildren(menus: any[], level: number) {
    const result = [];
    if (menus && menus.length) {
      menus.forEach(d => {
        const { id, title, menus } = d;
        result.push(
          {
            id,
            type: `level-${level}`,
            "text": `<div class="d-flex tree-item flex-row flex-grow justify-content-between">
                  <div class="node-text">${title}</div>
                  ${this.createButtonIcon(1)}
                </div>`,
            "children": this.parseChildren(menus, level + 1)
          }
        );
      });
    }
    return result;
  }

  private parse() {
    const ds: any[] = [];
    if (this.section.menus) {
      this.section.menus.forEach(d => {
        const { id, title, menus } = d;
        ds.push(
          {
            id,
            type: 'level-2',
            "text": `<div class="d-flex tree-item flex-row flex-grow justify-content-between">
                  <div class="node-text">${title}</div>
                  ${this.createButtonIcon(1)}
                </div>`,
            "children": this.parseChildren(menus, 2)
          }
        );
      });
    }
    this.datasource = ds;
    return ds;
  }

  private createButtonIcon(data: any) {
    return `<div class="node-buttons">
      <div class="btn btn-sm btn-blue btn-my add-child" data-id="${data.id}"><i class="add-child fas fa-plus-circle"></i></div>
      <div class="btn btn-sm btn-blue btn-my edit-child" data-id="${data.id}"><i class="edit-child fas fa-pen"></i></div>
      <div class="btn btn-sm btn-blue btn-my delete-child" data-id="${data.id}"><i class="delete-child far fa-trash-alt"></i></div>
    </div>`;
  }

  private refreshJsTree() {
    this.parse();
    const $tree = $(this.tree_id).jstree(true);
    $tree.settings.core.data = this.datasource;
    $tree.refresh(true);
  }

  private findNode(id: number) {
    return this.locations.find(d => d.id == id)
  }

  private initJsTree() {
    const $that = this;
    $(this.tree_id).jstree({
      "core": {
        "multiple": false,
        "check_callback": true,
        "themes": {
          "responsive": false
        },
        "data": this.datasource
      },
      "types": {
        "default": {
          "icon": "fas fa-chevron-right"
        },
        "file": {
          "icon": "fa fa-file"
        }
      },
      "state": {
        "key": "demo1"
      },
      "plugins": ['dnd', 'state', 'themes', "types"]
    });
    $(this.tree_id).on("create_node.jstree", function (e, data) {
      $("li#" + data.node.id).find("a").append('test');
    });
    $(this.tree_id).on("changed.jstree", function (e, data) {
      const { selected, action, node, event } = data;
      if (action == "select_node" && event) {
        const { target } = event;

        const addChild = $(target).hasClass('add-child');
        if (addChild) {
          const { original } = node;
        }

        const editChild = $(target).hasClass('edit-child');
        if (editChild) {
          const node = $that.findNode(parseInt(selected[0]));
          if (node) {
            $that.edit(node);
          } else {
            console.error('node not found');
          }
        }
      }
    });
  }

}
