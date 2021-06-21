import { Component, OnInit } from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    $("#kt_tree_5").jstree({
      "core": {
        "themes": {
          "responsive": false
        },
        // so that create works
        "check_callback": true,
        "data": [
          {
            "text": "Produk Dana",
            "children": [
            ]
          },
          {
            text: "Produk Digital"
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
      "plugins": ["types"]
    });
  }
}
