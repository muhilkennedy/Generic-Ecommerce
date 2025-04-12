import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ToastMessageService } from '../../../service/toastmessage/toast-message.service';
import { NgxSpinnerModule } from 'ngx-spinner';
import { FileUploadModule } from 'primeng/fileupload';
import { SpinnerComponent } from "../spinner";
import { ToastModule } from 'primeng/toast';
import { TranslateModule } from '@ngx-translate/core';
import { environment } from '../../../../environments/environment';
import { CommonModule } from '@angular/common';
import { FileStore } from '../../../model/fileStore';
import { FormsModule } from '@angular/forms';
import { CommonUtil } from '../../../util/CommonUtil.service';

@Component({
  selector: 'app-file-upload',
  imports: [NgxSpinnerModule, FileUploadModule, SpinnerComponent, ToastModule, TranslateModule, CommonModule, FormsModule],
  templateUrl: './file-upload.component.html',
  styleUrl: './file-upload.component.scss'
})
export class FileUploadComponent {

  selectedFiles: any[] = [];
  uploadedFiles: any[] = [];
  isFileUploaded: boolean = false;
  @Input() multiple: boolean = false;
  @Input() internalFile: boolean = false;
  @Input() label: string = '';
  @Input() acceptType: string = 'image/*'; //default image type
  @Output() fileDetails = new EventEmitter<FileStore[]>();
  fileDetail: FileStore[] = [];

  constructor(private messageService: ToastMessageService) { }

  onSelect(event: any) {
    if(event.files.length == 1 && !this.multiple) {
      this.selectedFiles.push(event.files[0]);
    }
    else{
      event.files.forEach((file: any) => {
        this.selectedFiles.push(file);
      });
    }
  }

  onUpload(event: any) {
    event.files.forEach((file: any) => {
      this.uploadedFiles.push(file);
      this.selectedFiles.push(file);
    });
    event.originalEvent.body.dataList.forEach((file: any) => {
      this.fileDetail.push(file);
      this.messageService.showSuccessMessage(`File ${file.fileName} uploaded successfully.`);
    });
    this.fileDetails.emit(this.fileDetail);
    this.isFileUploaded = true;
  }

  uploadError(event: any) {
    if(CommonUtil.isNotNullOrEmptyOrUndefined(event.error)){
      this.messageService.showErrorMessage(event.error.error.message, event.error.error.errorCode);
    }
    else{
      this.messageService.showErrorMessage('Error while uploading file');
    }
  }

  onRemove() {
    this.uploadedFiles.length = 0;
    this.selectedFiles.length = 0;
  }

  getUploadFilesLength(): number {
    return this.fileDetail.length;
  }

  getUploadUrl(): string {
    return `${environment.apiUrl}/tm/admin/file/upload?internalFile=${this.internalFile}`;
  }

}
