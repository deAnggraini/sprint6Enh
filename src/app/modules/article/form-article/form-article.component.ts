import { Component, OnInit } from '@angular/core';
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';

@Component({
  selector: 'app-form-article',
  templateUrl: './form-article.component.html',
  styleUrls: ['./form-article.component.scss']
})
export class FormArticleComponent implements OnInit {

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

  constructor() {
  }

  ngOnInit(): void {
    console.log(this.editor);
  }

}
