import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonModule } from 'primeng/button';
import { FluidModule } from 'primeng/fluid';
import { ToastModule } from 'primeng/toast';
import { SpinnerComponent } from '../../../shared/spinner';
import { InputGroupModule } from 'primeng/inputgroup';
import { Role } from '../../../../model/role';
import { EmployeeService } from '../../../../service/employee/employee.service';
import { ToastMessageService } from '../../../../service/toastmessage/toast-message.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { finalize } from 'rxjs';
import { Employee } from '../../../../model/employee';
import { CommonUtil } from '../../../../util/CommonUtil.service';
import { FloatLabelModule } from 'primeng/floatlabel';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ToggleButtonModule } from 'primeng/togglebutton';

@Component({
  selector: 'app-assign-roles',
  imports: [ButtonModule, CommonModule, ToastModule, FluidModule, FormsModule, ReactiveFormsModule, TranslateModule, SpinnerComponent, InputGroupModule, FloatLabelModule,
    AutoCompleteModule, ToggleButtonModule],
  templateUrl: './assign-roles.component.html',
  styleUrl: './assign-roles.component.scss'
})
export class AssignRolesComponent {

  allroles: Role[] = [];
  selectedEmployee!: any;
  employeeRoles: Role[] = [];

  filteredEmployees: any[] = [];

  constructor(private employeeService: EmployeeService, private messageService: ToastMessageService, private spinner: NgxSpinnerService) { }

  ngOnInit() {
    this.spinner.show();
    this.employeeService.getAllRoles().pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
      .subscribe({
        next: (response: any) => {
          this.allroles = response.dataList;
        },
        error: (error: any) => {
          this.messageService.showError(error);
        }
      });
  }

  fetchEmployeeRoles() {
    this.spinner.show();
    this.employeeService.getEmployeeRoles(this.selectedEmployee.rootid).pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
      .subscribe({
        next: (response: any) => {
          this.employeeRoles = response.dataList;
          response.dataList.forEach((empRole: any) => {
            let rr: any = this.allroles.find(role => empRole.rootid == role.rootid);
            if (CommonUtil.isNotNullOrEmptyOrUndefined(rr)) {
              rr.selected = true;
            }
          });
        },
        error: (error: any) => {
          this.messageService.showError(error);
        }
      });
  }

  filterEmployees(event: any) {
    this.employeeService.searchEmployeesByName(event.query)
      .subscribe({
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

  updateEmployeeRoles() {
    this.spinner.show();
    this.employeeService.assignRolesToEmployee(this.selectedEmployee.rootid,
      this.allroles.filter(role => role.selected).map(role => role.rootid))
      .pipe(
        finalize(() => {
          this.spinner.hide();
        })
      )
      .subscribe({
        next: (response: any) => {
          this.messageService.showSuccessMessage("Roles assigned successfully");
        },
        error: (error: any) => {
          this.messageService.showError(error);
        }
      });
  }

}
