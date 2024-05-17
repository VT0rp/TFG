import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GenerateService {
  private printingSubject = new Subject<boolean>();
  printing$: Observable<boolean> = this.printingSubject.asObservable();

  constructor() {
    window.addEventListener('beforeprint', () => this.printingSubject.next(true));
    window.addEventListener('afterprint', () => this.printingSubject.next(false));
  }

  setPrinting(isPrinting: boolean) {
    this.printingSubject.next(isPrinting);
  }
}
