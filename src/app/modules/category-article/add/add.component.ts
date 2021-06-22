import { Component, OnInit } from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  constructor() { }


  // consume by jstree
  customMenu(node) {
    console.log(node);
    var items = {
      renameItem: { // The "rename" menu item
        label: "Rename",
        action: function () { }
      },
      deleteItem: { // The "delete" menu item
        label: "Delete",
        action: function () { }
      }
    }
    if ($(node).hasClass("folder")) {
      // Delete the "delete" menu item
      delete items.deleteItem;
    }
    return items;
  }

  ngOnInit(): void {
    console.log('ngOnInit called');

    const $that = this;
    function customMenu(node) {
      $that.customMenu(node);
    }
    function createButtonIcon(id) {
      return `
      <div class="node-buttons">
        <div class="btn btn-sm btn-clean btn-my"><i class="fas fa-plus-circle"></i></div>
        <div class="btn btn-sm btn-clean btn-my"><i class="fas fa-pen"></i></div>
        <div class="btn btn-sm btn-clean btn-my"><i class="far fa-trash-alt"></i></div>
      </div>`;
    }

    const tree_id = "#struktur-tree";
    const $tree = $(tree_id).jstree({
      "core": {
        "multiple": false,
        "check_callback": true,
        "themes": {
          "responsive": false
        },
        "data": [
          {
            id: 1,
            type: 'level-1',
            "text": `
              <div class="d-flex tree-item flex-row flex-grow justify-content-between">
                <div class="node-text">Produk Dana</div>
                ${createButtonIcon(1)}
              </div>
              `,
            "children": []
          },
          {
            text: `
            <div class="d-flex tree-item flex-row flex-grow justify-content-between">
              <div class="node-text">Produk Digital</div>
              ${createButtonIcon(1)}
            </div>
            `
          },
          {
            text: "Produk Investasi"
          },
          {
            text: "Produk Kerjasama"
          },
          {
            text: "Produk Kartu Kredit"
          },
          {
            text: "Produk Kredit Konsumtif"
          },
          {
            text: "Produk Kredit Produktif",
            children: [
              {
                "text": "SME",
                state: { "opened": true },
                children: [
                  {
                    id: 11,
                    "text": "Kredit Lokal",
                    "icon": "fa fa-file",
                    state: { "selected": true },
                  },
                  {
                    id: 10,
                    "text": "Time Loan",
                    "icon": "fa fa-file"
                  }
                ]
              }
            ]
          }
        ]
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

    $tree.on("create_node.jstree", function (e, data) {
      console.log('create_node');
      $("li#" + data.node.id).find("a").append('test');
    });
    $tree.on("changed.jstree", function (e, data) {
      console.log("The selected nodes are:");
      console.log(data.selected);
    });

    $('#struktur-tree-2').jstree({
      "core": {
        "multiple": false,
        "check_callback": true,
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
      "plugins": ['dnd', 'contextmenu', "types"],
      "contextmenu": { items: customMenu }
    });
  }
}
