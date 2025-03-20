import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { FluidModule } from 'primeng/fluid';
import { TextareaModule } from 'primeng/textarea';
import { InputMaskModule } from 'primeng/inputmask';
import { StepperModule } from 'primeng/stepper'
import { FloatLabelModule } from 'primeng/floatlabel';
import { TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { CommonUtil } from '../../../util/CommonUtil.service';
import { environment } from '../../../../environments/environment';
import { CommonModule } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { FileUploadModule } from 'primeng/fileupload';
import { MessageService } from 'primeng/api';
import { DatePicker } from 'primeng/datepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ToastMessageService } from '../../../service/toastmessage/toast-message.service';
import { TenantService } from '../../../service/tenant/tenant.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-onboard-tenant',
  imports: [InputTextModule, ButtonModule, SelectModule, FormsModule, InputMaskModule, CommonModule, ToastModule, FileUploadModule,
    FluidModule, TextareaModule, ReactiveFormsModule, TranslateModule, FloatLabelModule, StepperModule, DatePicker, NgxSpinnerModule],
  templateUrl: './onboard-tenant.component.html',
  styleUrl: './onboard-tenant.component.scss'
})
export class OnboardTenantComponent {

  timeZones!: string[];
  cities!: any[];
  states: string[] = ["Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
    "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
    "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
    "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
    "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
    "Uttar Pradesh", "Uttarakhand", "West Bengal"];
  locales: string[] = ['en', 'ta'];

  postalLoad: boolean = false;
  activeStep: number = 1;
  selectedFiles: any[] = [];
  uploadedFiles: any[] = [];
  isFileUploaded: boolean = false;
  rangeDates!: Date[];
  logoFileId!: number;

  detailsFormGroup!: FormGroup;

  constructor(private http: HttpClient, private fb: FormBuilder, private tenantService: TenantService,
    private messageService: ToastMessageService, private spinner: NgxSpinnerService) {
    this.timeZones = Intl.supportedValuesOf('timeZone');
  }

  ngOnInit() {
    this.detailsFormGroup = this.fb.group({
      name: ['', Validators.required],
      uniqueName: ['', Validators.required],
      tagLine: ['', Validators.required],
      street: ['', Validators.required],
      mobile: ['', Validators.required],
      pincode: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      locale: ['', Validators.required],
      timeZone: ['', Validators.required],
      businessEmail: ['', [Validators.required, Validators.email]],
      emailId: ['', [Validators.required, Validators.email]],
    });
  }

  getErrorClassIfPresent(formGroup: FormGroup, fieldName: string): string {
    return CommonUtil.getErrorStyleClassForField(formGroup, fieldName);
  }

  hasError(formGroup: FormGroup, fieldName: string): boolean {
    return CommonUtil.isInvalidField(formGroup, fieldName);
  }

  searchPincode() {
    let pincode: any = this.detailsFormGroup.controls['pincode'].value;
    if (!CommonUtil.isNullOrEmptyOrUndefined(pincode) && !pincode.includes("_")) {
      this.postalLoad = true;
      this.http.get(`${environment.apiUrl}/tm/common/pincode/${pincode}`)
        .subscribe({
          next: (resp: any) => {
            this.cities = resp.dataList[0].PostOffice;
            this.detailsFormGroup.patchValue({ state: this.cities[0].State });
            this.postalLoad = false;
          },
          error: (err: any) => {
            console.log(err);
          }
        })
    }
  }

  onSelect(event: any) {
    this.selectedFiles[0] = event.files[0];
    // for (const file of event.files) {
    //   this.uploadedFiles.push(file);
    // }
  }

  onUpload(event: any) {
    this.uploadedFiles[0] = event.files[0];
    this.logoFileId = event.originalEvent.body.data.rootid;
    this.isFileUploaded = true;
  }

  onRemove() {
    this.uploadedFiles.length = 0;
  }

  getUploadUrl(): string {
    return `${environment.apiUrl}/tm/admin/file/upload?internalFile=false`;
  }

  canSaveTenant() {
    return CommonUtil.isNullOrEmptyOrUndefined(this.rangeDates);
  }

  saveTenant() {
    this.spinner.show();
    let body: any = {
      name: this.detailsFormGroup.controls['name'].value,
      uniqueName: this.detailsFormGroup.controls['uniqueName'].value,
      locale: this.detailsFormGroup.controls['locale'].value,
      timeZone: this.detailsFormGroup.controls['timeZone'].value,
      logoFileId : this.logoFileId,
      tagLine: this.detailsFormGroup.controls['tagLine'].value,
      emailId: this.detailsFormGroup.controls['emailId'].value,
      mobile: this.detailsFormGroup.controls['mobile'].value,
      businessEmail: this.detailsFormGroup.controls['businessEmail'].value,
      street: this.detailsFormGroup.controls['street'].value,
      city: this.detailsFormGroup.controls['city'].value.Name,
      state: this.detailsFormGroup.controls['state'].value,
      pincode: this.detailsFormGroup.controls['pincode'].value,
    };
    this.tenantService.createNewTenant(body)
        .pipe(
          switchMap((resp: any) => {
            body = {
              startDate: this.rangeDates[0].toLocaleDateString('en-GB'),
              endDate : this.rangeDates[1].toLocaleDateString('en-GB'),
            };
            return this.tenantService.createTenantSubscription(body, resp.data.rootid);
          })
        )
        .subscribe(
          {
            next: (resp: any) => {
              this.messageService.showSuccessMessage('Tenant Onboarded Successfully');
            },
            error: (err: any) => {
              this.messageService.showErrorMessage('Error while onboarding tenant');
            },
            complete: () => { this.spinner.hide(); }
          }
        );
    }

}
