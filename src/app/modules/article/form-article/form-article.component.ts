import { Component, OnInit, ViewChild, AfterViewInit, OnDestroy, ChangeDetectorRef, TemplateRef, Inject } from '@angular/core';
import * as CustomEditor from './../../../ckeditor/build/ckeditor';
import { ArticleService } from '../../_services/article.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ChangeEvent, CKEditorComponent } from '@ckeditor/ckeditor5-angular';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { BehaviorSubject, Subscription, of, Subject } from 'rxjs';
import { Option, craeteEmptyOption } from 'src/app/utils/_model/option';
import { SkReferenceService } from '../../_services/sk-reference.service';
import { AuthService, UserModel } from '../../auth';
import { ArticleDTO, ArticleContentDTO, ArticleParentDTO, ArticleSenderDTO } from '../../_model/article.dto';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { StrukturService } from '../../_services/struktur.service';
import { catchError, debounceTime, distinctUntilChanged, map, first } from 'rxjs/operators';
import { ConfirmService } from 'src/app/utils/_services/confirm.service';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../_services/user.service';
import { DOCUMENT } from '@angular/common';
import { ViewportScroller } from '@angular/common';

const TOOL_TIPS = [
  'Berisi aturan/kaidah/ketetapan/syarat/kriteria atas produk/aplikasi yang harus dipahami pembaca sebelum melakukan prosedur atas produk/aplikasi tersebut; dapat dituangkan dalam bentuk kalimat ataupun tabel.',
  'Berisi proses/alur kerja/tahapan/cara kerja yang terkait atas suatu produk/aplikasi, biasanya dijelaskan dalam bentuk diagram alur.',
  'Berisi list-list formulir apa saja yang digunakan atas suatu produk/aplikasi.',
  'Berisi aturan/kaidah/ketetapan/syarat/kriteria atas produk/aplikasi yang harus dipahami pembaca sebelum melakukan prosedur atas produk/aplikasi tersebut; dapat dituangkan dalam bentuk kalimat ataupun tabel.',
  'Berisi proses/alur kerja/tahapan/cara kerja yang terkait atas suatu produk/aplikasi, biasanya dijelaskan dalam bentuk diagram alur.',
  'Berisi list-list formulir apa saja yang digunakan atas suatu produk/aplikasi.'
];

const defaultValue: ArticleDTO = {
  id: 0,
  isEmptyTemplate: false,
  title: '',
  desc: '',
  structureId: 0,
  structureOption: { id: '', value: '', text: '' },
  image: '',
  video: '',
  contents: [],
  references: [],
  related: [],
  suggestions: [],
  isHasSend: false,
  sender: null
}

@Component({
  selector: 'app-form-article',
  templateUrl: './form-article.component.html',
  styleUrls: ['./form-article.component.scss']
})
export class FormArticleComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('editorDesc') editorComponent: CKEditorComponent;
  @ViewChild('formSaveAndSend') formSaveAndSend: TemplateRef<any>;

  subscriptions: Subscription[] = [];

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
        // '|',
        // 'Mathtype',
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
    wordCount: { maxLimit: 1000 },
    fontColor: {
      colors: [
        {
          color: 'hsl(0, 0%, 100%)',
          label: 'Putih',
          hasBorder: true
        },
        {
          color: 'hsl(0, 0%, 0%)',
          label: 'Hitam'
        },
        {
          color: 'hsl(0, 0%, 90%)',
          label: 'Abu - Abu Muda'
        },
        {
          color: 'hsl(215, 22%, 34%)',
          label: 'Biru Abu - Abu'
        },
        {
          color: 'hsl(218, 52%, 52%)',
          label: 'Biru, Aksen 1'
        },
        {
          color: 'hsl(24, 84%, 56%)',
          label: 'Oranye, Aksen 2'
        },
        {
          color: 'hsl(0, 0%, 65%)',
          label: 'Abu - Abu, Aksen 3'
        },
        {
          color: 'hsl(45, 100%, 50%)',
          label: 'Emas, Aksen 4'
        },
        {
          color: 'hsl(209, 59%, 60%)',
          label: 'Biru, Aksen 5'
        },
        {
          color: 'hsl(96, 42%, 48%)',
          label: 'Hijau, Aksen 6'
        },
        {
          color: 'hsl(0, 0%, 93%)',
          label: 'Putih, Lebih Gelap 5%'
        },
        {
          color: 'hsl(0, 0%, 50%)',
          label: 'Hitam, Lebih Terang 50%'
        },
        {
          color: 'hsl(0, 2%, 81%)',
          label: 'Abu - Abu Muda, Lebih Gelap 10%'
        },
        {
          color: 'hsl(214, 21%, 87%)',
          label: 'Biru Abu - Abu, Lebih Terang 80%'
        },
        {
          color: 'hsl(219, 52%, 90%)',
          label: 'Biru, Aksen 1, Lebh Terang 80%'
        },
        {
          color: 'hsl(25, 83%, 91%)',
          label: 'Oranye, Aksen 2, Lebih Terang 80%'
        },
        {
          color: 'hsl(0, 0%, 92%)',
          label: 'Abu - Abu, Aksen 3, Lebih Terang 80%'
        },
        {
          color: 'hsl(45, 100%, 90%)',
          label: 'Emas, Aksen 4, Lebih Terang 80%'
        },
        {
          color: 'hsl(208, 57%, 92%)',
          label: 'Biru, Aksen 5, Lebih Terang 80%'
        },
        {
          color: 'hsl(95, 41%, 89%)',
          label: 'Hijau, Aksen 6, Lebih Terang 80%'
        },
        {
          color: 'hsl(0, 0%, 85%)',
          label: 'Putih, Lebih Gelap 15%'
        },
        {
          color: 'hsl(0, 0%, 35%)',
          label: 'Hitam, Lebih Terang 35%'
        },
        {
          color: 'hsl(0, 2%, 68%)',
          label: 'Abu - Abu Muda, Lebih Gelap 25%'
        },
        {
          color: 'hsl(215, 21%, 74%)',
          label: 'Biru Abu - Abu, Lebih Terang 60%'
        },
        {
          color: 'hsl(219, 52%, 81%)',
          label: 'Biru, Aksen 1, Lebh Terang 60%'
        },
        {
          color: 'hsl(25, 82%, 82%)',
          label: 'Oranye, Aksen 2, Lebih Terang 60%'
        },
        {
          color: 'hsl(0, 0%, 86%)',
          label: 'Abu - Abu, Aksen 3, Lebih Terang 60%'
        },
        {
          color: 'hsl(45, 98%, 80%)',
          label: 'Emas, Aksen 4, Lebih Terang 60%'
        },
        {
          color: 'hsl(208, 59%, 84%)',
          label: 'Biru, Aksen 5, Lebih Terang 60%'
        },
        {
          color: 'hsl(96, 42%, 79%)',
          label: 'Hijau, Aksen 6, Lebih Terang 60%'
        },
        {
          color: 'hsl(0, 0%, 75%)',
          label: 'Putih, Lebih Gelap 25%'
        },
        {
          color: 'hsl(0, 0%, 25%)',
          label: 'Hitam, Lebih Terang 25%'
        },
        {
          color: 'hsl(0, 2%, 45%)',
          label: 'Abu - Abu Muda, Lebih Gelap 50%'
        },
        {
          color: 'hsl(215, 22%, 60%)',
          label: 'Biru Abu - Abu, Lebih Terang 40%'
        },
        {
          color: 'hsl(218, 52%, 71%)',
          label: 'Biru, Aksen 1, Lebh Terang 40%'
        },
        {
          color: 'hsl(24, 84%, 74%)',
          label: 'Oranye, Aksen 2, Lebih Terang 40%'
        },
        {
          color: 'hsl(0, 0%, 79%)',
          label: 'Abu - Abu, Aksen 3, Lebih Terang 40%'
        },
        {
          color: 'hsl(45, 100%, 70%)',
          label: 'Emas, Aksen 4, Lebih Terang 40%'
        },
        {
          color: 'hsl(208, 58%, 75%)',
          label: 'Biru, Aksen 5, Lebih Terang 40%'
        },
        {
          color: 'hsl(96, 42%, 68%)',
          label: 'Hijau, Aksen 6, Lebih Terang 40%'
        },
        {
          color: 'hsl(0, 0%, 65%)',
          label: 'Putih, Lebih Gelap 35%'
        },
        {
          color: 'hsl(0, 0%, 15%)',
          label: 'Hitam, Lebih Terang 15%'
        },
        {
          color: 'hsl(0, 2%, 22%)',
          label: 'Abu - Abu Muda, Lebih Gelap 75%'
        },
        {
          color: 'hsl(213, 22%, 25%)',
          label: 'Biru Abu - Abu, Lebih Gelap 25%'
        },
        {
          color: 'hsl(218, 52%, 39%)',
          label: 'Biru, Aksen 1, Lebh Gelap 25%'
        },
        {
          color: 'hsl(24, 84%, 42%)',
          label: 'Oranye, Aksen 2, Lebih Gelap 25%'
        },
        {
          color: 'hsl(0, 0%, 48%)',
          label: 'Abu - Abu, Aksen 3, Lebih Gelap 25%'
        },
        {
          color: 'hsl(45, 100%, 37%)',
          label: 'Emas, Aksen 4, Lebih Gelap 25%'
        },
        {
          color: 'hsl(208, 59%, 45%)',
          label: 'Biru, Aksen 5, Lebih Gelap 25%'
        },
        {
          color: 'hsl(96, 42%, 36%)',
          label: 'Hijau, Aksen 6, Lebih Gelap 25%'
        },
        {
          color: 'hsl(0, 0%, 50%)',
          label: 'Putih, Lebih Gelap 50%'
        },
        {
          color: 'hsl(0, 0%, 5%)',
          label: 'Hitam, Lebih Terang 5%'
        },
        {
          color: 'hsl(0, 2%, 9%)',
          label: 'Abu - Abu Muda, Lebih Gelap 90%'
        },
        {
          color: 'hsl(215, 22%, 17%)',
          label: 'Biru Abu - Abu, Lebih Gelap 50%'
        },
        {
          color: 'hsl(218, 53%, 26%)',
          label: 'Biru, Aksen 1, Lebh Gelap 50%'
        },
        {
          color: 'hsl(25, 85%, 28%)',
          label: 'Oranye, Aksen 2, Lebih Gelap 50%'
        },
        {
          color: 'hsl(0, 0%, 32%)',
          label: 'Abu - Abu, Aksen 3, Lebih Gelap 50%'
        },
        {
          color: 'hsl(45, 100%, 25%)',
          label: 'Emas, Aksen 4, Lebih Gelap 50%'
        },
        {
          color: 'hsl(208, 60%, 30%)',
          label: 'Biru, Aksen 5, Lebih Gelap 50%'
        },
        {
          color: 'hsl(96, 42%, 24%)',
          label: 'Hijau, Aksen 6, Lebih Gelap 50%'
        },
        {
          color: 'hsl(0, 100%, 38%)',
          label: 'Merah Tua'
        },
        {
          color: 'hsl(0, 100%, 50%)',
          label: 'Merah'
        },
        {
          color: 'hsl(45, 100%, 50%)',
          label: 'Oranye'
        },
        {
          color: 'hsl(60, 100%, 50%)',
          label: 'Kuning'
        },
        {
          color: 'hsl(89, 58%, 56%)',
          label: 'Hijau Muda'
        },
        {
          color: 'hsl(147, 100%, 35%)',
          label: 'Hijau'
        },
        {
          color: 'hsl(196, 100%, 47%)',
          label: 'Biru Muda'
        },
        {
          color: 'hsl(205, 100%, 38%)',
          label: 'Biru'
        },
        {
          color: 'hsl(220, 100%, 19%)',
          label: 'Biru Tua'
        },
        {
          color: 'hsl(274, 54%, 41%)',
          label: 'Ungu'
        }
      ],
      columns: 10
    },
    fontBackgroundColor: {
      colors: [
        {
          color: 'hsl(60, 100%, 50%)',
          label: 'Kuning'
        },
        {
          color: 'hsl(120, 100%, 50%)',
          label: 'Hijau Cerah'
        },
        {
          color: 'hsl(180, 100%, 50%)',
          label: 'Biru Kehijauan'
        },
        {
          color: 'hsl(300, 100%, 50%)',
          label: 'Merah Muda'
        },
        {
          color: 'hsl(240, 100%, 50%)',
          label: 'Biru'
        },
        {
          color: 'hsl(0, 100%, 50%)',
          label: 'Merah'
        },
        {
          color: 'hsl(240, 100%, 25%)',
          label: 'Biru Tua'
        },
        {
          color: 'hsl(180, 100%, 25%)',
          label: 'Hijau Kebiruan'
        },
        {
          color: 'hsl(120, 100%, 25%)',
          label: 'Hijau'
        },
        {
          color: 'hsl(300, 100%, 25%)',
          label: 'Lembayung'
        },
        {
          color: 'hsl(0, 100%, 25%)',
          label: 'Merah Tua'
        },
        {
          color: 'hsl(60, 100%, 25%)',
          label: 'Kuning Tua'
        },
        {
          color: 'hsl(0, 0%, 50%)',
          label: 'Abu - abu 50%'
        },
        {
          color: 'hsl(0, 0%, 75%)',
          label: 'Abu - abu 255'
        },
        {
          color: 'hsl(0, 0%, 0%)',
          label: 'Hitam'
        },
      ],
      columns: 5
    }
  };

  configTopic = {
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
    placeholder: 'Masukkan kalimat pengantar terkait Ketentuan disini.',
    fontColor: {
      colors: [
        {
          color: 'hsl(0, 0%, 100%)',
          label: 'Putih',
          hasBorder: true
        },
        {
          color: 'hsl(0, 0%, 0%)',
          label: 'Hitam'
        },
        {
          color: 'hsl(0, 0%, 90%)',
          label: 'Abu - Abu Muda'
        },
        {
          color: 'hsl(215, 22%, 34%)',
          label: 'Biru Abu - Abu'
        },
        {
          color: 'hsl(218, 52%, 52%)',
          label: 'Biru, Aksen 1'
        },
        {
          color: 'hsl(24, 84%, 56%)',
          label: 'Oranye, Aksen 2'
        },
        {
          color: 'hsl(0, 0%, 65%)',
          label: 'Abu - Abu, Aksen 3'
        },
        {
          color: 'hsl(45, 100%, 50%)',
          label: 'Emas, Aksen 4'
        },
        {
          color: 'hsl(209, 59%, 60%)',
          label: 'Biru, Aksen 5'
        },
        {
          color: 'hsl(96, 42%, 48%)',
          label: 'Hijau, Aksen 6'
        },
        {
          color: 'hsl(0, 0%, 93%)',
          label: 'Putih, Lebih Gelap 5%'
        },
        {
          color: 'hsl(0, 0%, 50%)',
          label: 'Hitam, Lebih Terang 50%'
        },
        {
          color: 'hsl(0, 2%, 81%)',
          label: 'Abu - Abu Muda, Lebih Gelap 10%'
        },
        {
          color: 'hsl(214, 21%, 87%)',
          label: 'Biru Abu - Abu, Lebih Terang 80%'
        },
        {
          color: 'hsl(219, 52%, 90%)',
          label: 'Biru, Aksen 1, Lebh Terang 80%'
        },
        {
          color: 'hsl(25, 83%, 91%)',
          label: 'Oranye, Aksen 2, Lebih Terang 80%'
        },
        {
          color: 'hsl(0, 0%, 92%)',
          label: 'Abu - Abu, Aksen 3, Lebih Terang 80%'
        },
        {
          color: 'hsl(45, 100%, 90%)',
          label: 'Emas, Aksen 4, Lebih Terang 80%'
        },
        {
          color: 'hsl(208, 57%, 92%)',
          label: 'Biru, Aksen 5, Lebih Terang 80%'
        },
        {
          color: 'hsl(95, 41%, 89%)',
          label: 'Hijau, Aksen 6, Lebih Terang 80%'
        },
        {
          color: 'hsl(0, 0%, 85%)',
          label: 'Putih, Lebih Gelap 15%'
        },
        {
          color: 'hsl(0, 0%, 35%)',
          label: 'Hitam, Lebih Terang 35%'
        },
        {
          color: 'hsl(0, 2%, 68%)',
          label: 'Abu - Abu Muda, Lebih Gelap 25%'
        },
        {
          color: 'hsl(215, 21%, 74%)',
          label: 'Biru Abu - Abu, Lebih Terang 60%'
        },
        {
          color: 'hsl(219, 52%, 81%)',
          label: 'Biru, Aksen 1, Lebh Terang 60%'
        },
        {
          color: 'hsl(25, 82%, 82%)',
          label: 'Oranye, Aksen 2, Lebih Terang 60%'
        },
        {
          color: 'hsl(0, 0%, 86%)',
          label: 'Abu - Abu, Aksen 3, Lebih Terang 60%'
        },
        {
          color: 'hsl(45, 98%, 80%)',
          label: 'Emas, Aksen 4, Lebih Terang 60%'
        },
        {
          color: 'hsl(208, 59%, 84%)',
          label: 'Biru, Aksen 5, Lebih Terang 60%'
        },
        {
          color: 'hsl(96, 42%, 79%)',
          label: 'Hijau, Aksen 6, Lebih Terang 60%'
        },
        {
          color: 'hsl(0, 0%, 75%)',
          label: 'Putih, Lebih Gelap 25%'
        },
        {
          color: 'hsl(0, 0%, 25%)',
          label: 'Hitam, Lebih Terang 25%'
        },
        {
          color: 'hsl(0, 2%, 45%)',
          label: 'Abu - Abu Muda, Lebih Gelap 50%'
        },
        {
          color: 'hsl(215, 22%, 60%)',
          label: 'Biru Abu - Abu, Lebih Terang 40%'
        },
        {
          color: 'hsl(218, 52%, 71%)',
          label: 'Biru, Aksen 1, Lebh Terang 40%'
        },
        {
          color: 'hsl(24, 84%, 74%)',
          label: 'Oranye, Aksen 2, Lebih Terang 40%'
        },
        {
          color: 'hsl(0, 0%, 79%)',
          label: 'Abu - Abu, Aksen 3, Lebih Terang 40%'
        },
        {
          color: 'hsl(45, 100%, 70%)',
          label: 'Emas, Aksen 4, Lebih Terang 40%'
        },
        {
          color: 'hsl(208, 58%, 75%)',
          label: 'Biru, Aksen 5, Lebih Terang 40%'
        },
        {
          color: 'hsl(96, 42%, 68%)',
          label: 'Hijau, Aksen 6, Lebih Terang 40%'
        },
        {
          color: 'hsl(0, 0%, 65%)',
          label: 'Putih, Lebih Gelap 35%'
        },
        {
          color: 'hsl(0, 0%, 15%)',
          label: 'Hitam, Lebih Terang 15%'
        },
        {
          color: 'hsl(0, 2%, 22%)',
          label: 'Abu - Abu Muda, Lebih Gelap 75%'
        },
        {
          color: 'hsl(213, 22%, 25%)',
          label: 'Biru Abu - Abu, Lebih Gelap 25%'
        },
        {
          color: 'hsl(218, 52%, 39%)',
          label: 'Biru, Aksen 1, Lebh Gelap 25%'
        },
        {
          color: 'hsl(24, 84%, 42%)',
          label: 'Oranye, Aksen 2, Lebih Gelap 25%'
        },
        {
          color: 'hsl(0, 0%, 48%)',
          label: 'Abu - Abu, Aksen 3, Lebih Gelap 25%'
        },
        {
          color: 'hsl(45, 100%, 37%)',
          label: 'Emas, Aksen 4, Lebih Gelap 25%'
        },
        {
          color: 'hsl(208, 59%, 45%)',
          label: 'Biru, Aksen 5, Lebih Gelap 25%'
        },
        {
          color: 'hsl(96, 42%, 36%)',
          label: 'Hijau, Aksen 6, Lebih Gelap 25%'
        },
        {
          color: 'hsl(0, 0%, 50%)',
          label: 'Putih, Lebih Gelap 50%'
        },
        {
          color: 'hsl(0, 0%, 5%)',
          label: 'Hitam, Lebih Terang 5%'
        },
        {
          color: 'hsl(0, 2%, 9%)',
          label: 'Abu - Abu Muda, Lebih Gelap 90%'
        },
        {
          color: 'hsl(215, 22%, 17%)',
          label: 'Biru Abu - Abu, Lebih Gelap 50%'
        },
        {
          color: 'hsl(218, 53%, 26%)',
          label: 'Biru, Aksen 1, Lebh Gelap 50%'
        },
        {
          color: 'hsl(25, 85%, 28%)',
          label: 'Oranye, Aksen 2, Lebih Gelap 50%'
        },
        {
          color: 'hsl(0, 0%, 32%)',
          label: 'Abu - Abu, Aksen 3, Lebih Gelap 50%'
        },
        {
          color: 'hsl(45, 100%, 25%)',
          label: 'Emas, Aksen 4, Lebih Gelap 50%'
        },
        {
          color: 'hsl(208, 60%, 30%)',
          label: 'Biru, Aksen 5, Lebih Gelap 50%'
        },
        {
          color: 'hsl(96, 42%, 24%)',
          label: 'Hijau, Aksen 6, Lebih Gelap 50%'
        },
        {
          color: 'hsl(0, 100%, 38%)',
          label: 'Merah Tua'
        },
        {
          color: 'hsl(0, 100%, 50%)',
          label: 'Merah'
        },
        {
          color: 'hsl(45, 100%, 50%)',
          label: 'Oranye'
        },
        {
          color: 'hsl(60, 100%, 50%)',
          label: 'Kuning'
        },
        {
          color: 'hsl(89, 58%, 56%)',
          label: 'Hijau Muda'
        },
        {
          color: 'hsl(147, 100%, 35%)',
          label: 'Hijau'
        },
        {
          color: 'hsl(196, 100%, 47%)',
          label: 'Biru Muda'
        },
        {
          color: 'hsl(205, 100%, 38%)',
          label: 'Biru'
        },
        {
          color: 'hsl(220, 100%, 19%)',
          label: 'Biru Tua'
        },
        {
          color: 'hsl(274, 54%, 41%)',
          label: 'Ungu'
        }
      ],
      columns: 10
    },
    fontBackgroundColor: {
      colors: [
        {
          color: 'hsl(60, 100%, 50%)',
          label: 'Kuning'
        },
        {
          color: 'hsl(120, 100%, 50%)',
          label: 'Hijau Cerah'
        },
        {
          color: 'hsl(180, 100%, 50%)',
          label: 'Biru Kehijauan'
        },
        {
          color: 'hsl(300, 100%, 50%)',
          label: 'Merah Muda'
        },
        {
          color: 'hsl(240, 100%, 50%)',
          label: 'Biru'
        },
        {
          color: 'hsl(0, 100%, 50%)',
          label: 'Merah'
        },
        {
          color: 'hsl(240, 100%, 25%)',
          label: 'Biru Tua'
        },
        {
          color: 'hsl(180, 100%, 25%)',
          label: 'Hijau Kebiruan'
        },
        {
          color: 'hsl(120, 100%, 25%)',
          label: 'Hijau'
        },
        {
          color: 'hsl(300, 100%, 25%)',
          label: 'Lembayung'
        },
        {
          color: 'hsl(0, 100%, 25%)',
          label: 'Merah Tua'
        },
        {
          color: 'hsl(60, 100%, 25%)',
          label: 'Kuning Tua'
        },
        {
          color: 'hsl(0, 0%, 50%)',
          label: 'Abu - abu 50%'
        },
        {
          color: 'hsl(0, 0%, 75%)',
          label: 'Abu - abu 255'
        },
        {
          color: 'hsl(0, 0%, 0%)',
          label: 'Hitam'
        },
      ],
      columns: 5
    }
  };

  // error manual
  hasError: boolean = false;
  errorMsg: string = '';
  hasImageError: boolean = false;
  errorImageMsg: string = '';

  logs: Map<number, ArticleContentDTO[]> = new Map();
  user: UserModel;
  isEdit: boolean = false;
  dataForm: FormGroup;
  finishRender: boolean = false;
  skReferences: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  relatedArticle$: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  suggestionArticle$: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  locationOptions: BehaviorSubject<Option[]> = new BehaviorSubject([]);
  listUserEditing: UserModel[] = [];

  // preview property
  originArticle: ArticleDTO = null;
  isPreview: boolean = false;
  previewHideTopbar: boolean = false;
  previewAlert: boolean = false;
  previewAlertMessage: string = '';
  isCompare: boolean = false;

  // save and send
  userOptions: Option[] = [];
  isHasSend: boolean = false;
  saveAndSend = {
    sendTo: {
      username: '',
      email: '',
    },
    sendNote: ''
  }
  selectedSaveAndSend: Option = craeteEmptyOption();

  // accordion
  selectedAccordion: ArticleContentDTO;
  tooltips = TOOL_TIPS;
  isAccEdit: boolean = false;
  editContentId: number = 0;
  editContentParent: number[] = [];
  openFragment: string = '';

  //cdk drag and drop
  allIds: Array<string> = []
  public get allIdConnected(): Array<string> {
    return this.allIds
  }
  dragMovedSubject = new Subject<any>();
  idAccordionSelected: string
  fakeDragAndDropStatus: boolean = false

  constructor(
    private cdr: ChangeDetectorRef,
    private article: ArticleService,
    private skService: SkReferenceService,
    private router: Router,
    private auth: AuthService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private struktur: StrukturService,
    private confirm: ConfirmService,
    private modalService: NgbModal,
    private configModel: NgbModalConfig,
    private userService: UserService,
    @Inject(DOCUMENT) private document: Document,
    private viewportScroller: ViewportScroller
  ) {
    this.configModel.backdrop = 'static';
    this.configModel.keyboard = false;
  }

  // validation
  isContentsPass(contents: ArticleContentDTO[]): boolean {
    let result: boolean = true;
    contents.forEach(d => {
      if (!result) return;
      if (!d.title) result = false;
      if (!d.topicTitle) result = false;
      if (!d.topicContent) result = false;
      if (d.children && d.children.length && result)
        result = this.isContentsPass(d.children);
    });
    return result;
  }
  isAllPass(): boolean {
    const _dataForm: ArticleDTO = this.dataForm.value;
    if (!(this.dataForm.valid && this.hasError === false && this.hasImageError === false)) return false;
    if (!_dataForm.desc) return false;
    if (!_dataForm.image) return false;
    const contents = _dataForm.contents;
    const len = contents.length;
    if (len < 1) {
      return false;
    }
    const result = this.isContentsPass(contents);
    return result;
  }

  //cdk drag and drop
  dropListsId(item: { id: number, no: string }[]): string {
    const id = item[item.length - 1].id.toString()
    this.allIds.some(x => x === id) ? '' : this.allIds.push(id)
    return id
  }
  getMaxLevel(dropList: ArticleContentDTO[]) {
    return dropList.reduce((depth, child) => {
      return Math.max(depth, 1 + this.getMaxLevel(child.children));
    }, 0);
  }

  // node calculation
  private findMaxSort(children: ArticleContentDTO[]): number {
    return Math.max(...children.map(d => d.sort)) | 0;
  }
  private findNode(id, dataList: ArticleContentDTO[]): ArticleContentDTO {
    let found = dataList.find(d => d.id == id);
    if (!found) {
      dataList.forEach(d => {
        if (found) return;
        found = this.findNode(id, d.children);
      });
    }
    return found;
  }

  // article action button
  onSave(e) {
    this.confirm.open({
      title: `Simpan`,
      message: `<p>Apakah Kamu yakin ingin menyimpan halaman ini? Halaman akan tersimpan kedalam draft Kamu`,
      btnOkText: 'Simpan',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        const _dataForm = this.dataForm.value;
        this.subscriptions.push(
          this.article.saveArticle(_dataForm).subscribe(resp => {
            if (resp) {
              this.article.formData = null;
              this.article.formAlert = 'Artikel berhasil disimpan ke dalam draft.';
              this.router.navigate(
                [
                  `/article/preview/${_dataForm.id}`,
                ], { replaceUrl: true });
            }
          })
        );
      }
    });
    return false;
  }
  onCancel(e) {
    this.confirm.open({
      title: `Batal Tambah Artikel`,
      message: `<p>Apakah Kamu yakin ingin keluar dan membatalkan membuat artikel baru?`,
      btnOkText: 'Ya, Batal Tambah',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.subscriptions.push(
          this.article.cancelArticle(this.dataForm.value.id).subscribe(resp => {
            if (resp) {
              if (this.isEdit) {
                let currentUrl = this.router.url;
                this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                  this.router.navigate([currentUrl]);
                });
              } else {
                this.article.formParam = null;
                this.article.formData = null;
                this.router.navigate(['/homepage'], { replaceUrl: true });
              }
            }
          })
        );
      }
    });
    return false;
  }
  onSaveAndSend(e) {
    const sender: ArticleSenderDTO = this.dataForm.value.sender;
    // reset form
    this.saveAndSend.sendTo.username = '';
    this.saveAndSend.sendTo.email = '';
    this.saveAndSend.sendNote = '';
    if (sender) {
      this.saveAndSend.sendTo.username = sender.username;
      this.saveAndSend.sendTo.email = sender.email;
    }

    this.modalService.open(this.formSaveAndSend);
    return false;
  }
  doSaveAndSend(e) {
    const _dataForm = this.dataForm.value;
    this.subscriptions.push(
      this.article.saveArticle(_dataForm, true, this.saveAndSend).subscribe(resp => {
        if (resp) {
          this.modalService.dismissAll();
          this.article.formData = null;
          this.article.formAlert = 'Artikel berhasil disimpan dan dikirim.';
          this.router.navigate(
            [
              `/article/preview/${_dataForm.id}`,
            ], { replaceUrl: true });
        }
      })
    );
    return false;
  }
  onPreview(show: boolean, hideTopbar: boolean = false, alert: boolean = false, msg: string = '') {
    this.article.formData = show ? this.dataForm.value as ArticleDTO : null;
    this.previewHideTopbar = hideTopbar;
    this.previewAlert = alert;
    this.previewAlertMessage = msg;
    this.isPreview = show;
    return false;
  }
  onVersionHistory(e) {
    return false;
  }

  // Right icon event
  btnAddClick(e, data: ArticleContentDTO) {
    this.subscriptions.push(
      this.article.getContentId().subscribe(resp => {
        if (resp) {
          const _contents = this.dataForm.get('contents').value as ArticleContentDTO[];
          let maxSort: number = 0;
          let listParent: ArticleParentDTO[] = [];
          let level: number = 1;
          let parent: number = 0;
          let no: string = '';
          if (data == null) {
            maxSort = this.findMaxSort(_contents);
          } else {
            maxSort = this.findMaxSort(data.children);
            level = data.level + 1;
            listParent = JSON.parse(JSON.stringify(data.listParent));
            if (data.level >= 2) {
              listParent.push({ id: 0, no: data.no, title: data.title });
            }
            parent = data.id;
            no = `${data.children.length + 1}`;
          }
          const newNode: ArticleContentDTO = {
            id: resp,
            articleId: this.dataForm.value.id,
            title: '',
            intro: '',
            topicContent: '',
            topicTitle: '',
            level,
            parent,
            sort: maxSort + 1,
            children: [],
            expanded: true,
            listParent,
            no,
            isEdit: true,
            isNew: true,
          }
          if (data) {
            data.expanded = true;
            data.children.push(newNode);
          } else {
            _contents.push(newNode);
          }
          this.addLog(newNode);
          this.cdr.detectChanges();
        }
      })
    );
    e.stopPropagation();
    return false;
  }
  btnEditClick(e, data: ArticleContentDTO) {
    data.isEdit = true;
    data.expanded = true;
    this.cdr.detectChanges();
    e.stopPropagation();
    return false;
  }
  btnDeleteClick(e, data: ArticleContentDTO) {
    this.confirm.open({
      title: `Hapus Topik`,
      message: `<p>Apakah Kamu yakin ingin menghapus topik “<b>${data.title}</b>”?`,
      btnOkText: 'Hapus',
      btnCancelText: 'Batal'
    }).then((confirmed) => {
      if (confirmed === true) {
        this.subscriptions.push(
          this.article.deleteContent(data.id).subscribe(resp => {
            this.deleteNode(data);
            // this.toast.showSuccess('Hapus Data Accordion Berhasil');
          })
        );
      }
    });
    e.stopPropagation();
    return false;
  }
  private deleteNode(data: ArticleContentDTO) {
    let _contents: ArticleContentDTO[] = JSON.parse(JSON.stringify(this.dataForm.get('contents').value));
    const _parent = this.findNode(data.parent, _contents);
    if (data.level > 1) {
      _parent.children = _parent.children.filter(d => d.id != data.id);
    } else {
      _contents = _contents.filter(d => d.id != data.id);
    }
    this.recalculateChildren(_contents, []);
    this.dataForm.get('contents').setValue(_contents);
    this.removeLog(data);
    this.cdr.detectChanges();
  }

  toggleTooltip(tooltip, user, i, show: boolean) {
    if (!show) {
      tooltip.close();
    } else {
      tooltip.open({ user, i });
    }
  }

  checkUniq(value) {
    this.hasError = false;
    if (!value) return;
    const checkUniqSubrcr = this.article.checkUniq(value.trim(), this.dataForm.value.id)
      .pipe(
        catchError((err) => {
          this.hasError = true;
          this.errorMsg = JSON.parse(err.message).message;
          return of(null);
        }),
        map(resp => resp),
      )
      .subscribe(
        resp => {
          if (resp === false) {
            this.hasError = false;
            this.errorMsg = '';
          }
          this.cdr.detectChanges();
        }
      );
    this.subscriptions.push(checkUniqSubrcr);
  }

  drop(event: CdkDragDrop<any[]>, levelParent: number, parent) {
    this.clearFakePlaceholder()
    if (this.fakeDragAndDropStatus) {
      this.addArray(this.dataForm.controls.contents.value, this.idAccordionSelected, event.item.data, parent)
      transferArrayItem(event.previousContainer.data, [], event.previousIndex, 0)
      this.recalculateChildren(event.previousContainer.data, event.previousContainer.data.length > 0 ? event.previousContainer.data[0].listParent : [])
      this.fakeDragAndDropStatus = false
      return
    }
    const level = event.container.data.length === 0 ? levelParent : event.container.data[0].level
    if (level + this.getMaxLevel(event.item.data.children) > 5) {
      return
    }
    if (event.previousContainer === event.container) {
      console.log('move dalam satu list');
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      console.log('pindah list');
      console.log('dari', event.previousContainer.data, event.previousIndex);
      console.log('ke', event.container.data, event.currentIndex);
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
    this.recalculateChildren(event.previousContainer.data, event.previousContainer.data.length > 0 ? event.previousContainer.data[0].listParent : []);
    this.recalculateChildren(event.container.data, event.container.data.filter((x, i) => i !== event.currentIndex)[0] === undefined ? parent : event.container.data.filter((x, i) => i !== event.currentIndex)[0].listParent);
    this.recalculateLevelChildren(event.container.data, level)
  }

  dragMoved(event) {
    let e = this.document.elementFromPoint(event.pointerPosition.x, event.pointerPosition.y);
    if (!e) {
      this.clearFakePlaceholder()
      this.fakeDragAndDropStatus = false
      return
    }
    let container = e.classList.contains("drop-area-accordion") ? e : e.closest(".drop-area-accordion");
    if (!container) {
      this.clearFakePlaceholder()
      this.fakeDragAndDropStatus = false
      return
    }
    const id = container.getAttribute("data-id")
    if (!id) {
      this.clearFakePlaceholder()
      this.fakeDragAndDropStatus = false
      return
    }
    this.fakeDragAndDropStatus = true
    this.document.getElementById('plc' + id).classList.add('fake-drag-placeholder-show')
    this.document.querySelectorAll('.drag-placeholder').forEach(element => element.classList.add('drag-placeholder-hidden'))
    this.idAccordionSelected = id
  }

  addArray(arr, id, value, parent) {
    arr.forEach(element => {
      if (element.id.toString() === id) {
        let listParent = [...element.listParent]
        let level = element.level + 1
        if (level > 2) {
          listParent.push({ id: element.id, title: element.title, no: element.no })
        }
        element.children.push(value)
        this.recalculateChildren(element.children, listParent)
        this.recalculateLevelChildren(element.children, level)
        return
      }
      if (element.children.length > 0) {
        this.addArray(element.children, id, value, parent)
      }
    });
  }

  clearFakePlaceholder() {
    this.document.querySelectorAll('.fake-drag-placeholder').forEach(element => element.classList.remove('fake-drag-placeholder-show'))
    this.document.querySelectorAll('.drag-placeholder').forEach(element => element.classList.remove('drag-placeholder-hidden'))
  }

  dragMovedDebounce(event) {
    this.dragMovedSubject.next(event)
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

  // User saerch
  searchUser(keyword) {
    if (keyword) {
      this.subscriptions.push(
        this.userService.searchUserNotReader(keyword).subscribe(resp => {
          if (resp) {
            this.userOptions = this.userService.usersToOptions(resp);
          }
        })
      );
    } else {
      this.userOptions = [];
    }
  }
  userChange(item: Option) {
    this.saveAndSend.sendTo.email = item.value;
  }

  // accordion event
  numberingFormat(data: ArticleContentDTO): string {
    const listParent = data.listParent;
    const strNoParent = listParent.map(d => d.no);
    strNoParent.push(data.no);
    return strNoParent.join(".");
  }
  accSaveAddEdit(content: ArticleContentDTO) {
    this.subscriptions.push(
      this.article.saveContent(content).subscribe(resp => {
        if (resp) {
          content.isEdit = false;
          content.isNew = false;
          // this.toast.showSuccess('Simpan Data Accordion Berhasil');
          this.addLog(content);
        }
        this.cdr.detectChanges();
      })
    );
  }
  accCancel(content: ArticleContentDTO) {
    if (content.isNew) {
      // data baru belum pernah simpan, langsung hapus
      this.deleteNode(content);
    } else
      if (this.logs.has(content.id)) {
        const _values: ArticleContentDTO[] = this.logs.get(content.id);
        const _value = _values[_values.length - 1];
        content.title = _value.title;
        content.topicTitle = _value.topicTitle;
        content.topicContent = _value.topicContent;
      } else {
        content.title = '';
        content.topicTitle = '';
        content.topicContent = '';
      }
    content.isEdit = false;
    this.cdr.detectChanges();
  }

  private populateAliasUser(users: UserModel[]): UserModel[] {
    if (users && users.length) {
      users.forEach(d => {
        d.alias = d.firstname[0];
        if (d.lastname) d.alias += d.lastname[0];
      });
    }
    return users;
  }
  private getUserEditing(id: number) {
    this.subscriptions.push(this.article.checkArticleEditing(id).subscribe(resp => {
      if (resp) { this.listUserEditing = this.populateAliasUser(resp); this.cdr.detectChanges() }
    }))
  }
  private getArticle(id: number, isEdit: boolean) {
    this.subscriptions.push(
      this.article.getById(id, isEdit).subscribe((resp: ArticleDTO) => {
        this.setArticle(resp);
      })
    );
  }
  private recalculateChildren(children: ArticleContentDTO[], listParent: ArticleParentDTO[]) {
    if (children && children.length) {
      children.forEach((d, i) => {
        if (!d.expanded) d.expanded = false;
        if (!d.isEdit) d.isEdit = false;
        d.listParent = listParent;
        if (this.editContentId) { // check apakah accordion di edit dari reader view
          d.expanded = this.editContentParent.find(elm => elm == d.id) ? true : false;
          if (this.editContentId == d.id) {
            d.expanded = true;
            d.isEdit = true;
          }
        }
        if (d.level == 1) {
          d.no = '';
          d.sort = i + 1;
          this.recalculateChildren(d.children, listParent.concat([]));
        } else {
          d.no = `${i + 1}`;
          d.sort = i + 1;
          const { id, title, no } = d;
          this.recalculateChildren(d.children, listParent.concat([{ id, title, no }]));
        }
        if (!d.topicTitle && d.title) d.topicTitle = d.title;
        if (!d.topicContent && d.intro) d.topicContent = d.intro;
      });
    }
  }
  private recalculateLevelChildren(children: ArticleContentDTO[], level: number) {
    if (children && children.length) {
      children.forEach((d, i) => {
        d.level = level
        this.recalculateLevelChildren(d.children, level + 1)
      });
    }
  }
  private getParentsId(id: number, dataList: ArticleContentDTO[], parents: number[]): ArticleContentDTO {
    let found = null;
    if (dataList && dataList.length) {
      found = dataList.find(d => d.id == id);
      if (found) {
        found.parentsId = parents
        return found;
      }
      dataList.forEach(d => {
        if (found) return;
        found = this.getParentsId(id, d.children, parents.concat(d.id));
      })
    }
    return found;
  }
  private setUserEditing(id: number) {
    this.article.checkArticleEditing(id).subscribe(resp => {

    });
  }
  private setArticle(article: ArticleDTO) {
    if (article) {
      console.log('setArticle', { article });
      this.getUserEditing(article.id);
      const { structureParentList, structureId } = article;
      article.structureOption = {
        id: `${structureId}`,
        text: structureParentList[structureParentList.length - 1].title,
        value: structureParentList.map(d => d.title).join(' > ')
      };

      // set expanded default value
      if (this.editContentId) {
        const _contents = JSON.parse(JSON.stringify(article.contents));
        const _found = this.getParentsId(this.editContentId, _contents, []);
        if (_found) {
          this.editContentParent = _found.parentsId;
        }
      }
      this.recalculateChildren(article.contents, []);

      // parsing referances to options
      if (article.references && article.references.length) {
        article.references.forEach(d => {
          d.value = d.no;
          d.text = `${d.no} - ${d.title}`;
        })
      }

      // parsing related article to options
      if (article.related && article.related.length) {
        article.related.forEach(d => {
          d.value = `${d.id}`;
          d.text = `${d.title}`;
        })
      }

      // parsing suggestions to options
      if (article.suggestions && article.suggestions.length) {
        article.suggestions.forEach(d => {
          d.value = `${d.id}`;
          d.text = `${d.title}`;
        })
      }

      this.isEdit = !article.isNew;
      if (this.isEdit) {
        this.setUserEditing(article.id);
        this.isCompare = true;

      }

      this.originArticle = JSON.parse(JSON.stringify(article));
      this.dataForm.reset(article);

      if (this.isEdit == false && !article.isEmptyTemplate) {
        this.subscriptions.push(this.dataForm.get('title').valueChanges.subscribe(val => {
          const contents: ArticleContentDTO[] = this.dataForm.get('contents').value;
          // TODO : sesuaikan dengan template yg sudah terpilih
          contents[0].title = contents[0].topicTitle = `Ketentuan ${val}`;
          contents[1].title = contents[1].topicTitle = `Prosedur ${val}`;
          contents[2].title = contents[2].topicTitle = `Formulir ${val}`;
        }));
      }

      if (article.sender) {
        const { username, fullname, email } = article.sender;
        this.selectedSaveAndSend = { id: username, value: email, text: fullname };
      }

      this.addLogs(article.contents);
      this.cdr.detectChanges();
    } else {
      this.goBackToAdd('Data article tidak valid');
    }
  }
  private addLogs(values: ArticleContentDTO[] = null) {
    if (values == null) return;
    values.forEach(d => {
      this.addLog(d);
      this.addLogs(d.children);
    });
  }
  private addLog(v: ArticleContentDTO) {
    if (v) { // add all
      const { id } = v;
      const _newData: ArticleContentDTO = Object.assign({}, v, {
        children: [], listParent: v.listParent
      });
      if (this.logs.has(id)) {
        this.logs.get(id).push(_newData);
      } else {
        this.logs.set(id, [_newData]);
      }
    }
  }
  private removeLog(value: ArticleContentDTO) {
  }
  onImageChange(e) {
    if (e.target.files && e.target.files.length) {
      const [file] = e.target.files;
      this.dataForm.patchValue({
        image: file
      });
      const $this = this;

      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function (e) {
        var image = new Image();
        image.src = e.target.result as string;
        image.onload = function (_) {
          const { width, height } = image;
          const { size } = file;
          console.log({ width, height, size });
          if (width < 307 || height < 425) {
            $this.hasImageError = true;
            $this.errorImageMsg = 'Resolusi gambar minimal 307x425 px';
            $this.cdr.detectChanges();
            return false;
          }
          if (size > 700000) {
            $this.hasImageError = true;
            $this.errorImageMsg = 'Ukuran gambar maksimal 700kb';
            $this.cdr.detectChanges();
            return false;
          }

          $this.hasImageError = false;
          $this.errorImageMsg = '';
          $this.cdr.detectChanges();
          return true;
        };
      }
    };
  }
  showImageName() {
    if (typeof (this.dataForm.value.image) === "string") {
      return this.dataForm.value.image;
    } else {
      return this.dataForm.value.image?.name;
    }
  }
  changeLocation(value) {
    if (value) this.dataForm.get('structureId').setValue(value.id);
  }

  private goBackToAdd(msg: string) {
    alert(msg);
    // this.router.navigate(['article/add']);
  }

  // SK SE Event
  private convertSkToOption(dataList: any[]): Option[] {
    const result = [];
    if (dataList && dataList.length) {
      dataList.forEach(d => {
        const { id, title, no } = d;
        result.push({ id, value: no, text: `${no} - ${title}` })
      })
    }
    return result;
  }
  getDataSkSeReference(keyword: string) {
    this.subscriptions.push(
      this.skService.search(keyword).subscribe(resp => {
        if (resp) {
          this.skReferences.next(this.convertSkToOption(resp));
        }
      })
    );
  }

  // Related Article Event
  private convertArticleToOption(dataList: any[]): Option[] {
    const result = [];
    if (dataList && dataList.length) {
      dataList.forEach(d => {
        const { id, title } = d;
        result.push({ id, value: id, text: title })
      })
    }
    return result;
  }
  getDataRelatedArticle(keyword: string) {
    this.subscriptions.push(
      this.article.searchArticle(keyword, this.dataForm.value.id).subscribe(resp => {
        if (resp) this.relatedArticle$.next(this.convertArticleToOption(resp.list));
      })
    );
  }
  getDataSuggestionArticle(keyword: string) {
    const exclude = [this.dataForm.value.id].concat(this.dataForm.value.suggestions.map(d => d.id));
    const structureId = this.dataForm.value.structureId;
    this.subscriptions.push(
      this.article.suggestionArticle(keyword, structureId, exclude).subscribe(resp => {
        if (resp) this.suggestionArticle$.next(this.convertArticleToOption(resp.list));
      })
    );
  }
  onSelectSuggestionArticle(arg: { item: any, keyword: string }) {
    const { keyword, item } = arg;
    const exclude = [this.dataForm.value.id].concat(this.dataForm.value.suggestions.map(d => d.id)).concat([item.id]);
    const structureId = this.dataForm.value.structureId;
    this.subscriptions.push(
      this.article.suggestionArticle(keyword, structureId, exclude).subscribe(resp => {
        if (resp) this.suggestionArticle$.next(this.convertArticleToOption(resp.list));
      })
    );
  }

  scroll(id) {
    if (!id) return;
    const elmnt = document.getElementById(id);
    if (elmnt) {
      setTimeout(() => {
        elmnt.scrollIntoView({ behavior: "auto", block: "start", inline: "start" });
        const top = window.scrollY;
        setTimeout(() => {
          window.scrollTo(0, top - 110);
        }, 100);
      }, 10);
    } else {
      setTimeout(() => {
        this.scroll(this.openFragment);
      }, 100);
    }
  }
  // CKEDITOR5 function
  public onReady(editor, value: string = '') {
    if (value) editor.setData(value); // cara paksa isi ckeditor
    this.finishRender = true;
  }
  public onReadyDesc(editor) {
    this.finishRender = true;
  }
  public onChange({ editor }: ChangeEvent) {
  }

  // Angular
  private initForm() {
    this.dataForm = this.fb.group({
      id: [0],
      title: [defaultValue.title, Validators.compose([Validators.required, Validators.maxLength(50)])],
      structureId: [defaultValue.structureId, Validators.compose([Validators.required])],
      structureOption: [defaultValue.structureOption, Validators.compose([Validators.required])],
      desc: [defaultValue.desc],
      image: [defaultValue.image],
      video: [defaultValue.video],
      contents: [defaultValue.contents],
      references: [defaultValue.references],
      related: [defaultValue.related],
      suggestions: [defaultValue.suggestions],
      isEmptyTemplate: [defaultValue.isEmptyTemplate, Validators.compose([Validators.required])],
      sender: [defaultValue.sender]
    });
  }
  ngOnInit(): void {
    this.initForm();
    this.subscriptions.push(this.route.params.subscribe(params => {
      const isEdit: boolean = params.isEdit;
      if (params.contentId) { this.editContentId = parseInt(params.contentId); }
      if (params['id']) {
        this.getArticle(parseInt(params['id']), isEdit);
      } else {
        this.goBackToAdd('Silahkan tambah artikel terlebih dahulu');
      }
    }));
    this.subscriptions.push(this.auth.currentUserSubject.subscribe((resp: UserModel) => {
      this.user = resp;
    }));
    this.subscriptions.push(this.struktur.categories$.subscribe(resp => {
      if (resp) {
        const locationOptions = this.struktur.parseToOptions(resp);
        this.locationOptions.next(locationOptions);
        this.cdr.detectChanges();
      }
    }));
    this.subscriptions.push(this.dragMovedSubject.pipe(debounceTime(100)).pipe(distinctUntilChanged()).subscribe(event => {
      this.dragMoved(event)
    }))
  }
  ngOnDestroy(): void {
    this.subscriptions.forEach(sb => sb.unsubscribe());
  }
  ngAfterViewInit(): void {
    this.finishRender = true;
    this.route.fragment
      .pipe(first())
      .subscribe((fragment) => {
        if (fragment) {
          this.openFragment = fragment;
          this.scroll(this.openFragment);
        }
      });
  }

}
