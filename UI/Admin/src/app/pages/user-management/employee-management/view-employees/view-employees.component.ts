import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { FluidModule } from 'primeng/fluid';
import { FloatLabelModule } from 'primeng/floatlabel';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { CommonModule } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { FilterMatchMode } from 'primeng/api';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { SpinnerComponent } from '../../../shared/spinner';
import { CommonUtil } from '../../../../util/CommonUtil.service';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { Employee } from '../../../../model/employee';
import { EmployeeService } from '../../../../service/employee/employee.service';
import { ToastMessageService } from '../../../../service/toastmessage/toast-message.service';
import { Table, TableModule } from 'primeng/table';
import { KnobModule } from 'primeng/knob';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { ProgressBarModule } from 'primeng/progressbar';
import { TagModule } from 'primeng/tag';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { DialogModule } from 'primeng/dialog';
import { RatingModule } from 'primeng/rating';
import { InputGroupModule } from 'primeng/inputgroup';
import { SliderModule } from 'primeng/slider';
import { AvatarModule } from 'primeng/avatar';
import { ToolbarModule } from 'primeng/toolbar';
import { OnboardEmployeesComponent } from "../onboard-employees/onboard-employees.component";
import { SearchDTO } from '../../../../model/searchDTO';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-view-employees',
  imports: [TableModule, ButtonModule, CommonModule, ToastModule, FluidModule, FormsModule, ReactiveFormsModule, TranslateModule, InputTextModule, DialogModule, DynamicDialogModule,
    FloatLabelModule, KnobModule, NgxSpinnerModule, IconFieldModule, InputIconModule, ProgressBarModule, TagModule, RatingModule,
    InputGroupModule, ToggleButtonModule, SpinnerComponent, SliderModule, SelectModule, AvatarModule, ToolbarModule, OnboardEmployeesComponent],
  templateUrl: './view-employees.component.html',
  styleUrl: './view-employees.component.scss'
})
export class ViewEmployeesComponent {

  constructor(private employeeService: EmployeeService, private messageService: ToastMessageService, private spinner: NgxSpinnerService,
              private translate: TranslateService
  ) { }

  employees!: Employee[];
  cachedEmployees!: Employee[];
  selectedEmployees!: Employee[];
  statuses: any[] = [];
  tableLoading: boolean = false;
  totalRecords!: number;
  pageSize: number = 10;
  pageNumber: number = 0;
  activityValues: number[] = [0, 100];
  searchValue: string | undefined;
  sortField: string = '';
  sortOrder: string = '';

  mobileFilterMatchModes!: any[];

  createEmployeeDialog: boolean = false;

  getGlobalFilterValue(event: any) {
    return event.target.value;
  }

  ngOnInit() {
    this.mobileFilterMatchModes = [
      { label: this.translate.instant('equals'), value: FilterMatchMode.EQUALS }
    ];
  this.statuses = [ this.translate.instant('Active'), this.translate.instant('Inactive')];
  }

  clear(table: Table) {
    table.clear();
    this.searchValue = ''
    this.employees = this.cachedEmployees;
  }

  applyGlobalFilter(keyword: string, table: Table) {
    this.searchValue = keyword;
    if(CommonUtil.isNotNullOrEmptyOrUndefined(this.searchValue) && this.searchValue.length >= 3){
      this.employees.length = 0;
      this.cachedEmployees.forEach((employee: Employee) => {
        if(employee.fname.toLowerCase().includes(this.searchValue!.toLowerCase()) || employee.lname.toLowerCase().includes(this.searchValue!.toLowerCase())
         || employee.emailid.toLowerCase().includes(this.searchValue!.toLowerCase()) || employee.mobile.toLowerCase().includes(this.searchValue!.toLowerCase())){
          this.employees.push(employee);
        }
      });
    }
  }

  loadEmployeesLazy(event: any) {
    this.tableLoading = true;
    this.pageNumber = event.first / event.rows;
    this.pageSize = event.rows;
    this.sortField = CommonUtil.isNullOrEmptyOrUndefined(event.sortField) ? '' : event.sortField;
    this.sortOrder = event.sortOrder == -1 ? 'desc' : 'asc';

    let body:SearchDTO[] = new Array();
    Object.keys(event.filters).forEach(key => {
      event.filters[key].forEach((filter: any) => {
        if(CommonUtil.isNotNullOrEmptyOrUndefined(filter.value)){
          if(key == 'status'){
            body.push(new SearchDTO('active', filter.matchMode, filter.operator, filter.value.value == 'Active'? true : false));
          }
          else{
            body.push(new SearchDTO(key, filter.matchMode, filter.operator, filter.value));
          }
        }
      })
    });

    if(body.length > 0){
      this.employeeService.filterAllEmployees(this.pageSize, this.pageNumber + 1, this.sortField, this.sortOrder, body).pipe(
        finalize(() => {
          this.spinner.hide();
        })
      )
      .subscribe({
        next: (response: any) => {
          this.employees = response.data.content;
          this.cachedEmployees = response.data.content.slice(); // creates a shallow copy of the array
          this.totalRecords = response.data.totalElements;
        },
        error: (error: any) => {
          this.messageService.showErrorMessage('Error while filtering employees');
        },
        complete: () => {
          this.tableLoading = false;
        }
      });
    }
    else{
      this.employeeService.getAllEmployees(this.pageSize, this.pageNumber, this.sortField, this.sortOrder).pipe(
        finalize(() => {
          this.spinner.hide();
        })
      )
      .subscribe({
        next: (response: any) => {
          this.employees = response.data.content;
          this.cachedEmployees = response.data.content.slice();
          this.totalRecords = response.data.totalElements;
        },
        error: (error: any) => {
          this.messageService.showErrorMessage('Error while fetching employees');
        },
        complete: () => {
          this.tableLoading = false;
        }
      });
    }
    
  }

  onBoardEmployee(){
    this.createEmployeeDialog = true;
  }

  toggleEmployeeStatus(){

  }

}
