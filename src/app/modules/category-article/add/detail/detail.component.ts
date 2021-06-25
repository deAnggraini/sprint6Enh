import { Component, OnInit, Input, OnDestroy, ChangeDetectorRef, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';

declare var $: any;

@Component({
  selector: 'app-struktur-lvl2-tree',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit, OnDestroy {

  @Output() editLevel1 = new EventEmitter<any>();
  @Output() deleteLevel1 = new EventEmitter<any>();
  @Input() selected: Observable<any>;
  section: any;
  tree_id = "#struktur-tree";
  datasource: any[];

  constructor(private cdr: ChangeDetectorRef) { }

  editSection() {
    this.editLevel1.emit(this.section);
  }

  deleteSection() {
    this.deleteLevel1.emit();
  }

  ngOnDestroy(): void {
    console.log('destroy called');
  }

  ngOnInit(): void {
    this.datasource = [];
    this.initJsTree();
    this.selected.subscribe(res => {
      this.section = res;
      this.refreshJsTree();
      // this.cdr.detectChanges();
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

  private createTreeItem(item) {

  }

  private createButtonIcon(id) {
    return `<div class="node-buttons">
      <div class="btn btn-sm btn-clean btn-my"><i class="fas fa-plus-circle"></i></div>
      <div class="btn btn-sm btn-clean btn-my"><i class="fas fa-pen"></i></div>
      <div class="btn btn-sm btn-clean btn-my"><i class="far fa-trash-alt"></i></div>
    </div>`;
  }

  private refreshJsTree() {
    this.parse();
    const $tree = $(this.tree_id).jstree(true);
    $tree.settings.core.data = this.datasource;
    $tree.refresh(true);
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
        // [
        //   {
        //     id: 1,
        //     type: 'level-1',
        //     "text": `
        //       <div class="d-flex tree-item flex-row flex-grow justify-content-between">
        //         <div class="node-text">Produk Dana</div>
        //         ${this.createButtonIcon(1)}
        //       </div>
        //       `,
        //     "children": []
        //   },
        //   {
        //     text: `
        //     <div class="d-flex tree-item flex-row flex-grow justify-content-between">
        //       <div class="node-text">Produk Digital</div>
        //       ${this.createButtonIcon(1)}
        //     </div>
        //     `
        //   },
        //   {
        //     text: "Produk Investasi"
        //   },
        //   {
        //     text: "Produk Kerjasama"
        //   },
        //   {
        //     text: "Produk Kartu Kredit"
        //   },
        //   {
        //     text: "Produk Kredit Konsumtif"
        //   },
        //   {
        //     text: "Produk Kredit Produktif",
        //     children: [
        //       {
        //         "text": "SME",
        //         state: { "opened": true },
        //         children: [
        //           {
        //             id: 11,
        //             "text": "Kredit Lokal",
        //             "icon": "fa fa-file",
        //             state: { "selected": true },
        //           },
        //           {
        //             id: 10,
        //             "text": "Time Loan",
        //             "icon": "fa fa-file"
        //           }
        //         ]
        //       }
        //     ]
        //   }
        // ]
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
      "plugins": ['dnd', 'themes', "types"]
    });
    $(this.tree_id).on("create_node.jstree", function (e, data) {
      $("li#" + data.node.id).find("a").append('test');
    });
    $(this.tree_id).on("changed.jstree", function (e, data) {
      console.log("The selected nodes are:");
      console.log({ data });
      console.log(data.selected);
    });
  }

}
