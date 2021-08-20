import { Component, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'pakar-scroll-top-down',
  templateUrl: './scroll-top-down.component.html',
  styleUrls: ['./scroll-top-down.component.scss']
})
export class ScrollTopDownComponent implements OnInit {

  disableTopBtn: boolean = true;
  disableBotBtn: boolean = false;
  threshold: number = 170;

  constructor() { }

  goTop() {
    if (this.disableTopBtn) return;
    window.scroll({
      left: 0,
      top: 0,
      behavior: 'smooth'
    });
  }

  goDown() {
    if (this.disableBotBtn) return;
    window.scroll({
      left: 0,
      top: document.body.scrollHeight,
      behavior: 'smooth'
    });
  }

  @HostListener('window:scroll', ['$event'])
  onScrollEvent(event: Event) {
    const doc: Document = event.target as Document;
    const body = doc.body;
    const scrollHeight = body.scrollHeight;
    let pos = (doc.documentElement.scrollTop || body.scrollTop) + doc.documentElement.offsetHeight;
    if (pos + this.threshold >= scrollHeight) {
      this.disableBotBtn = true;
    } else {
      this.disableBotBtn = false;
    }
    if (window.scrollY <= this.threshold) {
      this.disableTopBtn = true;
    } else {
      this.disableTopBtn = false;
    }
  }

  ngOnInit(): void {
  }

}
