import { Component, OnInit, HostListener, ElementRef } from '@angular/core';

@Component({
  selector: 'app-scroll-down',
  templateUrl: './scroll-down.component.html',
  styleUrls: ['./scroll-down.component.scss']
})
export class ScrollDownComponent implements OnInit {

  disableBtn: boolean = false;
  threshold: number = 170;

  constructor() { }

  ngOnInit(): void {
  }

  @HostListener('window:scroll', ['$event'])
  onScrollEvent(event: Event) {
    const doc: Document = event.target as Document;
    const body = doc.body;
    const top = body.scrollTop;
    const offSetHeight = body.offsetHeight;
    const scrollHeight = body.scrollHeight;
    let pos = (doc.documentElement.scrollTop || body.scrollTop) + doc.documentElement.offsetHeight;
    if (pos + this.threshold >= scrollHeight) {
      this.disableBtn = true;
    } else {
      this.disableBtn = false;
    }
  }

  scrollWin() {
    window.scrollTo({
      left: 0,
      top: document.body.scrollHeight,
      behavior: 'smooth'
    });
  }

}
