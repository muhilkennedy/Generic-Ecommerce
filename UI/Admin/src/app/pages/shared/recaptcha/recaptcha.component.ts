import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { get } from 'scriptjs';
import { environment } from '../../../../environments/environment';
import { CommonModule } from '@angular/common';
import { BehaviorSubject } from 'rxjs';
import { RecaptchaService } from '../../../service/recaptcha/recaptcha.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';

declare const grecaptcha: any;

@Component({
  selector: 'app-recaptcha',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, ButtonModule],
  templateUrl: './recaptcha.component.html',
  styleUrl: './recaptcha.component.scss'
})
//DEFAULT INVISIBLE RECAPTCHA IMPLEMENTATION.
export class RecaptchaComponent {
  visible: boolean = false;
  scriptLoaded: boolean = false;
  captchaReady: boolean = false;
  recaptchaSiteKey!: string;
  @Output() tokenGenerated = new EventEmitter<string>();
  widgetId!: number;
  captchaToken: string | null = null;

  @Output() clicked = new EventEmitter<void>();
  @Input() disabled:boolean = false;
  @Input() buttonLabel: string = 'Submit';
  @Input() raised: boolean = false;
  @Input() variant: any = 'outlined'; 
  @Input() severity: any = 'primary';
  @Input() iconClass: string = 'pi pi-check';

  constructor(private captchaService: RecaptchaService) { }

  ngAfterViewInit(): void {
    if ((window as any).grecaptcha) {
      this.renderCaptcha();
    } else {
      (window as any).addEventListener('grecaptchaLoaded', () => {
        this.renderCaptcha();
      });
    }
  }

  private renderCaptcha() {
    this.widgetId = (window as any).grecaptcha.render('invisible-recaptcha', {
      sitekey: environment.recaptchaSiteKey,
      size: 'invisible',
      callback: (token: string) => {
        //console.log('Recaptcha Token:', token);
        console.log('Recaptcha Validated');
        this.tokenGenerated.emit(token);
      }
    });
    this.captchaReady = true;
  }

  execute() {
    if (this.captchaReady && this.widgetId !== undefined) {
      grecaptcha.execute(this.widgetId);
    } else {
      console.warn('Captcha not ready yet.');
    }
  }

  debugExecute() {
    if (this.widgetId !== undefined) {
      grecaptcha.execute(this.widgetId);
    }
  }

  //TODO: Explore more on invisible captcha
  // async ngOnInit() {
  //   this.recaptchaSiteKey = environment.recaptchaSiteKey;
  //   try {
  //       await this.loadCaptchaScript();
  //       console.log('ReCaptcha script loaded.');
  //       this.scriptLoaded = true;
  //       (window as any).onRecaptchaSuccess = (token: string) => {
  //         //console.log('Recaptcha Token:', token);
  //         console.log('Recaptcha Validated');
  //         this.token.emit(token);
  //       };
  //   } catch (error) {
  //     console.error('Failed to load the ReCaptcha script:', error);
  //   }
  // }

  // loadCaptchaScript(): Promise<void> {
  //   return new Promise((resolve, reject) => {
  //     //we need to load this dynamically to make sure captcha container is rendered along with js load.
  //     get(environment.recaptchaUrl, () => {
  //       console.log("Recaptcha loaded");
  //       resolve();
  //     });
  //   });
  // }

}
