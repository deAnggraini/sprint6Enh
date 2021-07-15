import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import * as CustomEditor from './../../../ckeditor/build/ckeditor';
import { NgbAccordionConfig, NgbPanelChangeEvent, NgbAccordion } from '@ng-bootstrap/ng-bootstrap';
import { ArticleService } from '../../_services/article.service';
import { Router } from '@angular/router';
import { ChangeEvent, CKEditorComponent } from '@ckeditor/ckeditor5-angular';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';

// import { ArrayDataSource } from '@angular/cdk/collections';
// import { NestedTreeControl } from '@angular/cdk/tree';

const sample = {
  id: 0,
  title: '',
  desc: '',
  image: '',
  video: '',
  created_at: new Date(),
  created_by: '',
  contents: [
    {
      id: 1,
      title: 'Ketentuan Tahapan',
      desc: '',
      parent: 0,
      level: 1,
      sort: 1,
      children: [
        {
          id: 3,
          title: 'Rekening Tahapan',
          desc: '',
          parent: 1,
          level: 2,
          sort: 1,
          children: [
            {
              id: 18,
              title: 'Satu',
              desc: '',
              parent: 3,
              level: 3,
              sort: 1,
              children: [],
            },
            {
              id: 19,
              title: 'Dua',
              desc: '',
              parent: 3,
              level: 3,
              sort: 2,
              children: [],
            },
            {
              id: 20,
              title: 'Tiga',
              desc: '',
              parent: 3,
              level: 3,
              sort: 3,
              children: [],
            },
          ],
        },
        {
          id: 4,
          title: 'Pembukaan Rekening Tahapan',
          desc: '',
          parent: 1,
          level: 2,
          sort: 2,
          children: [],
        },
        {
          id: 5,
          title: 'Perubahan Data Rekening Tahapan',
          desc: '',
          parent: 1,
          level: 2,
          sort: 3,
          children: [],
        },
        {
          id: 6,
          title: 'Penutupan Tahapan',
          desc: '',
          parent: 1,
          level: 2,
          sort: 4,
          children: [],
        },
        {
          id: 7,
          title: 'Biaya Tahapan',
          desc: '',
          parent: 1,
          level: 2,
          sort: 5,
          children: [],
        },
      ],
    },
    {
      id: 2,
      title: 'Prosedur Tahapan',
      desc: '',
      parent: 0,
      level: 1,
      sort: 2,
      children: [
        {
          id: 8,
          title: 'Rekening Tahapan',
          desc: '',
          parent: 2,
          level: 2,
          sort: 1,
          children: [],
        },
        {
          id: 9,
          title: 'Pembukaan Rekening Tahapan',
          desc: '',
          parent: 2,
          level: 2,
          sort: 2,
          children: [],
        },
        {
          id: 10,
          title: 'Perubahan Data Rekening Tahapan',
          desc: '',
          parent: 2,
          level: 2,
          sort: 3,
          children: [],
        },
        {
          id: 11,
          title: 'Penutupan Tahapan',
          desc: '',
          parent: 2,
          level: 2,
          sort: 4,
          children: [],
        },
        {
          id: 12,
          title: 'Biaya Tahapan',
          desc: '',
          parent: 2,
          level: 2,
          sort: 5,
          children: [],
        },
      ],
    },
    {
      id: 3,
      title: 'Formulir Tahapan',
      desc: '',
      parent: 0,
      level: 1,
      sort: 2,
      children: [
        {
          id: 13,
          title: 'Rekening Tahapan',
          desc: '',
          parent: 3,
          level: 2,
          sort: 1,
          children: [],
        },
        {
          id: 14,
          title: 'Pembukaan Rekening Tahapan',
          desc: '',
          parent: 3,
          level: 2,
          sort: 2,
          children: [],
        },
        {
          id: 15,
          title: 'Perubahan Data Rekening Tahapan',
          desc: '',
          parent: 3,
          level: 2,
          sort: 3,
          children: [],
        },
        {
          id: 16,
          title: 'Penutupan Tahapan',
          desc: '',
          parent: 3,
          level: 2,
          sort: 4,
          children: [],
        },
        {
          id: 17,
          title: 'Biaya Tahapan',
          desc: '',
          parent: 3,
          level: 2,
          sort: 5,
          children: [],
        },
      ],
    }
  ],
  references: [{ id: 1, title: 'Perilhal Ketentuan Tahapan', no: '025/SKSE/TL/2020' }, {}, {}],
  related: [{}, {}, {}]
}

const TREE_DATA: FoodNode[] = [
  {
    name: 'Fruit',
    children: [
      { name: 'Apple' },
      { name: 'Banana' },
      { name: 'Fruit loops' },
    ]
  }, {
    name: 'Vegetables',
    children: [
      {
        name: 'Green',
        children: [
          { name: 'Broccoli' },
          { name: 'Brussel sprouts' },
        ]
      }, {
        name: 'Orange',
        children: [
          { name: 'Pumpkins' },
          { name: 'Carrots' },
        ]
      },
    ]
  },
];

interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

interface FoodNode {
  name: string;
  children?: FoodNode[];
}

@Component({
  selector: 'app-form-article',
  templateUrl: './form-article.component.html',
  styleUrls: ['./form-article.component.scss']
})
/*
 * TODO : convert template ke article == backend
 * UI : kirim form 4 field saja
 */
export class FormArticleComponent implements OnInit, AfterViewInit {

  @ViewChild('accDesc') accordion: NgbAccordion;
  @ViewChild('editorDesc') editorComponent: CKEditorComponent;

  editor = CustomEditor;
  config = {
    toolbar: {
      items: [
        'fontFamily',
        'fontSize',
        'fontColor',
        'fontBackgroundColor',
        'heading',
        '|',
        'undo',
        'redo',
        '|',
        'bold',
        'italic',
        'underline',
        'strikethrough',
        'alignment',
        'subscript',
        'superscript',
        '|',
        'bulletedList',
        'numberedList',
        'todoList',
        '|',
        'outdent',
        'indent',
        'findAndReplace',
        '|',
        'link',
        'imageUpload',
        'blockQuote',
        'insertTable',
        'mediaEmbed',
        '|',
        'code',
        'codeBlock',
        'htmlEmbed',
        'specialCharacters',
      ],
      shouldNotGroupWhenFull: true
    },
    language: 'en',
    image: {
      toolbar: [
        'imageTextAlternative',
        'imageStyle:full',
        'imageStyle:side',
        'linkImage'
      ]
    },
    table: {
      contentToolbar: [
        'tableColumn',
        'tableRow',
        'mergeTableCells',
        'tableCellProperties',
        'tableProperties'
      ]
    },
    fontFamily: {
      options: ['Calibri, sans-serif', 'Arial, Helvetica, sans-serif', 'Segoe UI, Open Sans'],
      supportAllValues: false
    },
    fontSize: {
      options: [11, 13, 16, 18],
      supportAllValues: false
    },
    placeholder: `Berisi penjelasan singkat tentang produk/aplikasi, <br>dapat berupa definisi atas produk/aplikasi tersebut.\r\n
    \r\n
    Contoh : \r\n
    Time Loan - SME merupakan salah satu produk kredit produktif untuk modal kerja kepada debitur segmen Small dan Medium Enterprises (SME) dalam mata uang rupiah ataupun valas yang penarikannya menggunakan Surat Permohonan Penarikan Fasilitas Kredit/Perpanjangan Pembayaran untuk jangka waktu tertentu.`
  };

  formData = JSON.parse(JSON.stringify(sample));

  // tree vew
  // private _transformer = (node: FoodNode, level: number) => {
  //   return {
  //     expandable: !!node.children && node.children.length > 0,
  //     name: node.name,
  //     level: level,
  //   };
  // }
  // treeControl = new NestedTreeControl<FoodNode>(node => node.children);
  // dataSource = new ArrayDataSource(TREE_DATA);
  // hasChild = (_: number, node: FoodNode) => !!node.children && node.children.length > 0;

  constructor(private accordionConfig: NgbAccordionConfig,
    private article: ArticleService,
    private router: Router) {
    this.accordionConfig.closeOthers = false;
    // this.accordionConfig.type = 'info';
  }

  drop(event: CdkDragDrop<any[]>) {
    console.log({ event });
    if (event.previousContainer === event.container) {
      console.log('move dalam satu list');
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      console.log('pindah list');
      console.log('dari', event.previousContainer.data);
      console.log('ke', event.container.data);
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }

  onHidden(panelId) {
    const header = document.getElementById(panelId + "-header");
    if (header) {
      header.classList.remove('panel-open');
    }
  }

  onShow(panelId) {
    const header = document.getElementById(panelId + "-header");
    if (header) {
      header.classList.add('panel-open');
    }
  }

  beforeChange($event: NgbPanelChangeEvent, accordion: NgbAccordion) {
    // console.log({ accordion });
    // const { panelId, nextState } = $event;
    // const header = document.getElementById(panelId + "-header");
    // if (header) {
    //   if (nextState === true) {
    //     header.classList.add('panel-open');
    //   } else {
    //     header.classList.remove('panel-open');
    //   }
    // }
  };

  ngOnInit(): void {
    if (this.article.formParam == null) {
      // this.router.navigate(['article/add']);
    }
    console.log(this.formData);
  }

  ngAfterViewInit(): void {
    console.log(this.editor);
    console.log(this.editor.editor);
  }

  // CKEDITOR5 function
  public onReady(editor) {
    // console.log(this.editor.builtinPlugins.map(plugin => plugin.pluginName));
    // console.log({ editor });
    // console.log(Array.from(editor.ui.componentFactory.names()));
    // editor.ui.getEditableElement().parentElement.insertBefore(
    //     editor.ui.view.toolbar.element,
    //     editor.ui.getEditableElement()
    // );
  }

  public onChange({ editor }: ChangeEvent) {
    console.log({ editor });
    const data = editor.getData();
    console.log(data);
  }

}
