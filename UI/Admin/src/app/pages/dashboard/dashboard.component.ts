import { Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { TenantService } from '../../service/tenant/tenant.service';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { ToastMessageService } from '../../service/toastmessage/toast-message.service';
import { CookieService } from 'ngx-cookie-service';
import { CommonUtil } from '../../util/CommonUtil.service';
import { Router } from '@angular/router';
import { Employee } from '../../model/employee';
import { EmployeeService } from '../../service/employee/employee.service';
import { EmployeeDataService } from '../../service/shared/employee/employee-data.service';
import { SpinnerComponent } from "../shared/spinner";

@Component({
  selector: 'app-dashboard',
  imports: [NgxSpinnerModule, TranslateModule, ToastModule, SpinnerComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  totalTenants: number = 0;
  recentTenants: number = 0;

  constructor(private spinner: NgxSpinnerService, private translate: TranslateService, private cookieService: CookieService,
              private messageService: ToastMessageService, private tenantService: TenantService, private router: Router,
              private employeeService: EmployeeService, private userData: EmployeeDataService,){}

  ngOnInit(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.cookieService.get(CommonUtil.KEY_TOKEN))) {
      this.router.navigate(['/login']);
      return;        
    }
    this.spinner.show();
    this.tenantService.getAllTenantsCountForDashboard().subscribe({
      next: (response: any) => {
        this.totalTenants = response.data.totalTenants;
        this.recentTenants = response.data.recentTenants;
      },
      error: (error) => {
        this.messageService.showErrorMessage("Failed to load Dashboard data.");
        this.spinner.hide();
      },
      complete: () => {
        this.spinner.hide();
      }
    });
  }

}
