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
  showErrorMessage(message: string, title?: string, sticky?: boolean) {
    this.messageService.add(
      {
        severity: "error",
        detail: message,
        summary: CommonUtil.isNullOrEmptyOrUndefined(title) ? this.translate.instant("Error") : title,
        sticky: CommonUtil.isNullOrEmptyOrUndefined(title) ? false : sticky,
      });
  }

  showPermanentErrorMessage(message: string): void;
  showPermanentErrorMessage(message: string, title?: string){
    return this.showErrorMessage(message, title, true);
  };

  showWarningMessage(message: string, title?: string): void;
  showWarningMessage(message: string, title?: string, sticky?: boolean): void;
  showWarningMessage(message: string, title?: string, sticky?: boolean) {
    this.messageService.add(
      {
        severity: "warn",
        detail: message,
        summary: CommonUtil.isNullOrEmptyOrUndefined(title) ? this.translate.instant("Warning") : title,
        sticky: CommonUtil.isNullOrEmptyOrUndefined(title) ? false : sticky,
      });
  }

  showSuccessMessage(message: string, title?: string): void;
  showSuccessMessage(message: string, title?: string, sticky?: boolean): void;
  showSuccessMessage(message: string, title?: string, sticky?: boolean) {
    this.messageService.add(
      {
        severity: "success",
        detail: message,
        summary: CommonUtil.isNullOrEmptyOrUndefined(title) ? this.translate.instant("Success") : title,
        sticky: CommonUtil.isNullOrEmptyOrUndefined(title) ? false : sticky,
      });
  }

  showInfoMessage(message: string, title?: string): void;
  showInfoMessage(message: string, title?: string, sticky?: boolean): void;
  showInfoMessage(message: string, title?: string, sticky?: boolean) {
    this.messageService.add(
      {
        severity: "info",
        detail: message,
        summary: CommonUtil.isNullOrEmptyOrUndefined(title) ? this.translate.instant("Info") : title,
        sticky: CommonUtil.isNullOrEmptyOrUndefined(title) ? false : sticky,
      });
  }


}
