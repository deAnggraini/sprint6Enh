import { Component, OnInit, ViewChild } from '@angular/core';
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { NgbAccordionConfig, NgbPanelChangeEvent, NgbAccordion } from '@ng-bootstrap/ng-bootstrap';

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
      id: 0,
      title: 'Ketentuan Tahapan',
      desc: '',
      parent: 1,
      children: [
        {
          id: 0,
          title: 'Rekening Tahapan',
          desc: '',
          children: [],
        },
        {
          id: 0,
          title: 'Pembukaan Rekening Tahapan',
          desc: '',
          children: [],
        },
        {
          id: 0,
          title: 'Perubahan Data Rekening Tahapan',
          desc: '',
          children: [],
        },
        {
          id: 0,
          title: 'Penutupan Tahapan',
          desc: '',
          children: [],
        },
        {
          id: 0,
          title: 'Biaya Tahapan',
          desc: '',
          children: [],
        },
      ]
    },
    {
      id: 0,
      title: 'Prosedur Tahapan',
      desc: '',
      parent: 1,
      children: []
    },
    {
      id: 0,
      title: 'Formulir Tahapan',
      desc: '',
      parent: 1,
      children: []
    }
  ],
  references: [{id:1, title:'Perilhal Ketentuan Tahapan', no : '025/SKSE/TL/2020'}, {}, {}],
  related: [{}, {}, {}]
}

@Component({
  selector: 'app-form-article',
  templateUrl: './form-article.component.html',
  styleUrls: ['./form-article.component.scss']
})
export class FormArticleComponent implements OnInit {

  @ViewChild('accDesc') accordion: NgbAccordion;

  editor = ClassicEditor;
  config = {
    // toolbar: {
    //   items: ['bold', 'italic', '|', 'undo', 'redo', '-', 'numberedList', 'bulletedList'],
    //   viewportTopOffset: 30,
    //   shouldNotGroupWhenFull: true
    // },
    fontSize: [11, 13, 16, 18],
    placeholder: `Berisi penjelasan singkat tentang produk/aplikasi, dapat berupa definisi atas produk/aplikasi tersebut.\r\n
    \r\n
    Contoh : \r\n
    Time Loan - SME merupakan salah satu produk kredit produktif untuk modal kerja kepada debitur segmen Small dan Medium Enterprises (SME) dalam mata uang rupiah ataupun valas yang penarikannya menggunakan Surat Permohonan Penarikan Fasilitas Kredit/Perpanjangan Pembayaran untuk jangka waktu tertentu.`
  };

  formData = JSON.parse(JSON.stringify(sample));

  constructor(private accordionConfig: NgbAccordionConfig) {
    this.accordionConfig.closeOthers = true;
    // this.accordionConfig.type = 'info';
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
    console.log(this.formData);
  }

}
