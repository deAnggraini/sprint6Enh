import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export declare interface PakarRawState {
  url: string,
  data: any;
  scroll: number
}

export declare interface PakarState {
  [id: number]: PakarRawState
}

@Injectable({
  providedIn: 'root'
})
export class PakarStateService {

  state: PakarState = {};
  state$: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor() { }
}
