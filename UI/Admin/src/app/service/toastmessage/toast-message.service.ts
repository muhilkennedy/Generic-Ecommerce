import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { CommonUtil } from '../../util/CommonUtil.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class ToastMessageService {

  constructor(private messageService: MessageService, private translate: TranslateService) { }

  showErrorMessage(message: string, title?: string): void;
  showErrorMessage(message: string, title?: string, sticky?: boolean): void;
  // Lets stick error messages by default
  showErrorMessage(message: string, title?: string, sticky: boolean = true) {
    this.messageService.add(
      {
        severity: "error",
        detail: this.translate.instant(message),
        summary: CommonUtil.isNullOrEmptyOrUndefined(title) ? this.translate.instant("Error") : title,
        sticky: sticky,
      });
  }

  showTempErrorMessage(message: string): void;
  showTempErrorMessage(message: string, title?: string){
    return this.showErrorMessage(message, title, false);
  };

  showWarningMessage(message: string, title?: string): void;
  showWarningMessage(message: string, title?: string, sticky?: boolean): void;
  showWarningMessage(message: string, title?: string, sticky?: boolean) {
    this.messageService.add(
      {
        severity: "warn",
        detail: this.translate.instant(message),
        summary: CommonUtil.isNullOrEmptyOrUndefined(title) ? this.translate.instant("Warning") : title,
        sticky: CommonUtil.isNullOrEmptyOrUndefined(sticky) ? false : sticky,
      });
  }

  showSuccessMessage(message: string, title?: string): void;
  showSuccessMessage(message: string, title?: string, sticky?: boolean): void;
  showSuccessMessage(message: string, title?: string, sticky?: boolean) {
    this.messageService.add(
      {
        severity: "success",
        detail: this.translate.instant(message),
        summary: CommonUtil.isNullOrEmptyOrUndefined(title) ? this.translate.instant("Success") : title,
        sticky: CommonUtil.isNullOrEmptyOrUndefined(sticky) ? false : sticky,
      });
  }

  showInfoMessage(message: string, title?: string): void;
  showInfoMessage(message: string, title?: string, sticky?: boolean): void;
  showInfoMessage(message: string, title?: string, sticky?: boolean) {
    this.messageService.add(
      {
        severity: "info",
        detail: this.translate.instant(message),
        summary: CommonUtil.isNullOrEmptyOrUndefined(title) ? this.translate.instant("Info") : title,
        sticky: CommonUtil.isNullOrEmptyOrUndefined(sticky) ? false : sticky,
      });
  }


}
