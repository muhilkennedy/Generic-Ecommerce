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
import { CommonModule } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { FileUploadModule } from 'primeng/fileupload';
import { MessageService } from 'primeng/api';
import { DatePicker } from 'primeng/datepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { SpinnerComponent } from '../../../shared/spinner';
import { PasswordModule } from 'primeng/password';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { CommonUtil } from '../../../../util/CommonUtil.service';
import { RadioButtonModule } from 'primeng/radiobutton';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { CardModule } from 'primeng/card';
import { Role } from '../../../../model/role';
import { Employee } from '../../../../model/employee';
import { EmployeeService } from '../../../../service/employee/employee.service';
import { ToastMessageService } from '../../../../service/toastmessage/toast-message.service';
import { FileUploadComponent } from "../../../shared/file-upload/file-upload.component";
import { FileStore } from '../../../../model/fileStore';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-onboard-employees',
  imports: [InputTextModule, SpinnerComponent, ButtonModule, SelectModule, FormsModule, InputMaskModule, CommonModule, ToastModule, FileUploadModule, RadioButtonModule,
    FluidModule, TextareaModule, ReactiveFormsModule, TranslateModule, FloatLabelModule, StepperModule, DatePicker, NgxSpinnerModule, PasswordModule,
    AutoCompleteModule, ToggleButtonModule, FileUploadComponent, CardModule],
  templateUrl: './onboard-employees.component.html',
  styleUrl: './onboard-employees.component.scss'
})
export class OnboardEmployeesComponent {

  activeStep: number = 1;

  detailsFormGroup!: FormGroup;
  locales: any[] = [];
  filteredEmployees: any[] = [];

  proofFileId!: number;
  profilePicUrl!: string;
  selectedPicFiles: any[] = [];

  employee: Employee = new Employee();

  roles: Role[] = [];

  constructor(private fb: FormBuilder, private employeeService: EmployeeService, private messageService: ToastMessageService, private spinner: NgxSpinnerService) { }

  ngOnInit() {
    this.locales = CommonUtil.locales;
    this.detailsFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      designation: [''],
      mobile: ['', Validators.required],
      locale: ['', Validators.required],
      reportsTo: [''],
      secondaryEmail: ['', [Validators.required, Validators.email]],
      emailId: ['', [Validators.required, Validators.email]],
      dob: ['', Validators.required],
      gender: ['Male', Validators.required]
    });
  }

  getErrorClassIfPresent(formGroup: FormGroup, fieldName: string): string {
    return CommonUtil.getErrorStyleClassForField(formGroup, fieldName);
  }

  hasError(formGroup: FormGroup, fieldName: string): boolean {
    return CommonUtil.isInvalidField(formGroup, fieldName);
  }

  loadRoles() {
    if (this.roles.length === 0) {
      this.spinner.show();
      this.employeeService.getAllRoles().subscribe({
        next: (response: any) => {
          this.roles = response.dataList;
          this.roles.forEach((role: Role) => {
            role.selected = false;
          });
        },
        error: (error: any) => {
          this.messageService.showErrorMessage('Failed to load roles');
        },
        complete: () => {
          this.spinner.hide();
        }
      });
    }
  }

  uploadedProofFileDetails(event: FileStore[]) {
    this.proofFileId = event[0].rootid;
  }

  uploadedPicFileDetails(event: FileStore[]) {
    this.profilePicUrl = event[0].mediaurl;
  }

  filterEmployees(event: any) {

    this.employeeService.searchEmployeesByName(event.query).subscribe({
      next: (response: any) => {
        this.filteredEmployees = response.dataList;
        this.filteredEmployees.forEach((employee: any) => {
          employee.fullName = `${employee.fname} ${employee.lname}`;
        });
      },
      error: (error: any) => {
        this.messageService.showErrorMessage('Failed to search employees');
      }
    });
  }

  saveEmployee() {
    this.spinner.show();
    let body: any = {
      fname: this.detailsFormGroup.controls['firstName'].value,
      lname: this.detailsFormGroup.controls['lastName'].value,
      locale: this.detailsFormGroup.controls['locale'].value,
      designation: this.detailsFormGroup.controls['designation'].value,
      emailid: this.detailsFormGroup.controls['emailId'].value,
      mobile: this.detailsFormGroup.controls['mobile'].value,
      secondaryemail: this.detailsFormGroup.controls['secondaryEmail'].value,
      reportsto: this.detailsFormGroup.controls['reportsTo'].value.rootid,
      dob: this.detailsFormGroup.controls['dob'].value.toLocaleDateString('en-GB'),
      gender: this.detailsFormGroup.controls['gender'].value,
      prooffileid: this.proofFileId,
      profilepicurl: this.profilePicUrl
    };
    let selectedRoles = this.roles.filter((role: Role) => role.selected).map((role: Role) => role.rootid);
    this.employeeService.onboardEmployee(body).pipe(
      switchMap((resp: any) => {
        return this.employeeService.assignRolesToEmployee(resp.data.rootid, selectedRoles);
      })
    ).subscribe({
      next: (resp: any) => {
        this.employee = resp.data;
        this.messageService.showSuccessMessage('Employee Onboarded Successfully');
      },
      error: (err: any) => {
        this.messageService.showErrorMessage('Error while onboarding employee');
      },
      complete: () => { this.spinner.hide(); }
    })
  }

  canSaveEmployee() {
    return CommonUtil.isNullOrEmptyOrUndefined(this.proofFileId) && CommonUtil.isNullOrEmptyOrUndefined(this.profilePicUrl);
  }

}
