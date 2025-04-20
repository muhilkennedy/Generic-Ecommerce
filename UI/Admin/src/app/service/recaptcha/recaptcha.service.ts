import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecaptchaService {

  captchaResponse = new BehaviorSubject<any>(null);

  constructor() { }

  loadCaptchaResponse(response: any) {
    this.captchaResponse.next(response);
  }

}
