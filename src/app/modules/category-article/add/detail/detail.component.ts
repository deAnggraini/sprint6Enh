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
    level: 2,
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

  editSection() { this.editLevel1.emit(this.section); }
  deleteSection() { this.deleteLevel1.emit(); }

  @ViewChild('formAddChild') formModal: TemplateRef<any>;
  @ViewChild('formChangeParent') formChangeParentModal: TemplateRef<any>;

  locations: any[] = [];
  dataForm: FormGroup;
  section: StrukturDTO;
  tree_id = "#struktur-tree";
  treeItem$: BehaviorSubject<StrukturDTO[]> = new BehaviorSubject([]);
  datasource: StrukturDTO[];
  isEdit: boolean = false;
  hasError: boolean = false;
  imageFile: string;
  moved: boolean = false;
  logs: any[] = [];
  txtLevelName: string = 'Kategori';
  txtLevelChildName: string = 'Sub-Kategori';
  selectedDelete: any = { id: 0, title: '', name: '' };
  listToChangeParent: any[] = [];
  listBrothers: any[] = [];

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

  showIconName(type: string = "image") {
    if (type == "image") {
      if (typeof (this.dataForm.value.image) === "string") {
        return this.dataForm.value.image;
      } else {
        return this.dataForm.value.image?.name;
      }
    }
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

  private setTxtLevelName(level: number = 2) {
    if (level == 2) {
      this.txtLevelName = 'Kategori';
      this.txtLevelChildName = 'Sub-Kategori';
    } else if (level == 3) {
      this.txtLevelName = 'Sub-Kategori';
      this.txtLevelChildName = 'Sub-Sub-Kategori';
    } else if (level == 4) {
      this.txtLevelName = 'Sub-Sub-Kategori';
      this.txtLevelChildName = 'None';
    }
  }

  save() {
    if (this.dataForm.valid) {
      this.strukturService.save(this.convertToFormData()).subscribe(resp => {
        if (resp) {
          this.menu.refreshStruktur();
          this.modalService.dismissAll();
        }
      });
    }
  }

  private convertTreeToArray(dataList: any[]) {
    let result = [];
    if (dataList && dataList.length) {
      dataList.forEach(d => {
        const _clone = JSON.parse(JSON.stringify(d));
        delete _clone.menus;
        result.push(_clone);
        result = result.concat(this.convertTreeToArray(d.menus));
      })
    }
    return result;
  }

  updateSection() {
    const params = {
      id: this.section.id,
      children: this.convertTreeToArray(this.logs.slice(-1)[0]),
    }
    this.strukturService.updateSection(params).subscribe(resp => {
      if (resp) {
        this.menu.refreshStruktur();
      }
    });
  }

  cancel() {
    this.moved = false;
    this.logs = [];
    this.menu.refreshStruktur();
  }

  findBrother(node) {
    let { parent } = node;
    parent = parseInt(parent);
    if (parent) {
      const parent_node = this.findNode(parent);
      const { menus } = parent_node;
      const brothers = menus.filter(d => d.id != node.id);
      return brothers.map(d => { return { id: d.id, title: d.title } });
    }
    return [];
  }

  delete(node) {
    const categoryText = node.level == 2 ? 'kategori' : node.level == 3 ? 'sub-kategori' : 'sub-sub-kategori';
    // const categoryChildText = node.level == 2 ? 'sub-kategori' : node.level == 3 ? 'sub-sub-kategori' : '';
    this.confirm.open({
      title: `Hapus ${categoryText}`,
      message: `<p>Apakah Kamu yakin ingin menghapus ${categoryText} “<b>${node.title}</b>”?`,
      // ${node.level != 4 ? `</p><p>Seluruh ${categoryChildText} yang terdapat pada menu tersebut akan naik menjadi ${categoryText}.</p>` : ''}`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    })
      .then((confirmed) => {
        if (confirmed === true) {
          if (node.menus && node.menus.length) {
            this.listToChangeParent = node.menus.map(d => {
              return { id: d.id, title: d.title, changeTo: 0, error: false };
            });
            this.listBrothers = this.findBrother(node);
            this.selectedDelete = JSON.parse(JSON.stringify(node));
            this.changeNodeParent();
          } else {
            this.strukturService.delete({ id: node.id, changeTo: [] }).subscribe(resp => {
              if (resp) {
                this.menu.refreshStruktur();
              }
            })
          }
        }
      });
  }

  saveChangeParent() {
    if (!this.validationChangeParent()) {
      const { id } = this.selectedDelete;
      this.strukturService.delete({ id, changeTo: this.listToChangeParent }).subscribe(resp => {
        if (resp) {
          this.menu.refreshStruktur();
          this.modalService.dismissAll();
        }
      })
    }
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

  changeComboboxParent(value, id) {
    const found = this.listToChangeParent.find(d => d.id == id);
    if (found) {
      found.changeTo = parseInt(value);
    }
  }

  changeNodeParent() {
    this.modalService.open(this.formChangeParentModal, { size: 'lg' });
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.logs = [];
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

  private findLocation(data) {
    return this.locations.find(d => d.id == data.id);
  }

  private createCategorySingleDimention(dataList: any[], parent: any[]) {
    if (dataList && dataList.length) {
      dataList.forEach(d => {
        const _clone = JSON.parse(JSON.stringify(d));
        // delete _clone.menus;
        _clone._value = parent.concat(_clone).map(d => d.id).join(',');
        _clone._text = parent.concat(_clone).map(d => d.title).join(' > ');
        this.locations.push(_clone);
        this.createCategorySingleDimention(d.menus, JSON.parse(JSON.stringify(parent.concat(_clone))));
      })
    }
  }

  private resetForm() {
    this.setTxtLevelName();
    this.defaultValue.location = this.section.id.toString();
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

  private parseChildren(menus: any[]) {
    const result = [];
    if (menus && menus.length) {
      menus.forEach(d => {
        const { id, title, menus, level } = d;
        result.push(
          {
            id,
            type: `level-${level}`,
            _text: title,
            "text": `<div class="d-flex tree-item flex-row flex-grow justify-content-between">
                  <div class="node-text">${title}</div>
                  ${this.createButtonIcon({ id, level })}
                </div>`,
            "children": this.parseChildren(menus),
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
        const { id, title, menus, level } = d;
        ds.push(
          {
            id,
            type: `level-${level}`,
            _text: title,
            "text": `<div class="d-flex tree-item flex-row flex-grow justify-content-between">
                  <div class="node-text">${title}</div>
                  ${this.createButtonIcon(1)}
                </div>`,
            "children": this.parseChildren(menus)
          }
        );
      });
    }
    this.datasource = ds;
    return ds;
  }

  private createButtonIcon(data: any) {
    return `<div class="node-buttons mr-1">
      ${data.level != 4 ? '<div class="btn btn-sm btn-clean btn-blue btn-my add-child" data-id="${data.id}"><i class="add-child fas fa-plus-circle"></i></div>' : ''}
      <div class="btn btn-sm btn-clean btn-blue btn-my edit-child" data-id="${data.id}"><i class="edit-child fas fa-pen"></i></div>
      <div class="btn btn-sm btn-clean btn-blue btn-my delete-child" data-id="${data.id}"><i class="delete-child far fa-trash-alt"></i></div>
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

  private moving(data) {
    this.moved = true;
    const new_children = this.createJsonData(data.new_instance.get_json(), this.section.id);
    this.logs.push(new_children);
    this.cdr.detectChanges();
  }

  private createJsonData(jstree_json: any[], parent: number = 0, level: number = 2) {
    const datasource = [];
    if (jstree_json && jstree_json.length) {
      jstree_json.forEach((d, i) => {
        if (d.id) {
          const node = this.findNode(d.id);
          if (node) {
            // const _node = JSON.parse(JSON.stringify(node));
            node.level = level;
            node.parent = parent;
            node.sort = i + 1;
            node.menus = [];
            node.menus = this.createJsonData(d.children, d.id, level + 1);
            datasource.push(node);
          }
        }
      });
    }
    return datasource;
  }

  private initJsTree() {
    const $that = this;
    $(this.tree_id).jstree({
      "core": {
        "multiple": false,
        "check_callback": true,
        "themes": {
          "responsive": false,
          "variant": "large"
        },
        "data": this.datasource
      },
      "types": {
        "default": {
          "icon": "ki ki-arrow-next icon-sm pakar-color-dark"
        },
      },
      "state": {
        "key": "demo1"
      },
      "plugins": ['dnd', 'state', 'themes', "types"]
    }).on('move_node.jstree', function (e, data) {
      $that.moving(data);
    });
    $(this.tree_id).on("create_node.jstree", function (e, data) {
      console.log('create_node');
      $("li#" + data.node.id).find("a").append('test');
    });
    $(this.tree_id).on("changed.jstree", function (e, data) {
      const { selected, action, node, event } = data;
      if (action == "select_node" && event) {
        const { target } = event;

        const addChild = $(target).hasClass('add-child');
        if (addChild) {
          const node = $that.findNode(parseInt(selected[0]));
          const myLocation = $that.findLocation(node);
          const defaultValue = Object.assign({}, $that.defaultValue, { level: node.level + 1, parent: node.id, location: myLocation._value });
          $that.setTxtLevelName(node.level + 1);
          $that.dataForm.reset(defaultValue);
          $that.isEdit = false;
          $that.imageFile = null;
          $that.open(false);
          e.stopPropagation();
        }

        const editChild = $(target).hasClass('edit-child');
        if (editChild) {
          const node = $that.findNode(parseInt(selected[0]));
          if (node) {
            $that.setTxtLevelName(node.level);
            $that.edit(node);
          } else {
            console.error('node not found');
          }
          e.stopPropagation();
        }

        const deleteChild = $(target).hasClass('delete-child');
        if (deleteChild) {
          const node = $that.findNode(parseInt(selected[0]));
          if (node) {
            $that.setTxtLevelName(node.level);
            $that.delete(node);
          } else {
            console.error('node not found');
          }
          e.stopPropagation();
        }
      }
    });
  }

}
