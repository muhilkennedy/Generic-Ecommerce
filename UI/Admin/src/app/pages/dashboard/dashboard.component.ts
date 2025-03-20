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

@Component({
  selector: 'app-dashboard',
  imports: [ NgxSpinnerModule, TranslateModule, ToastModule ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  totalTenants: number = 0;
  recentTenants: number = 0;

  constructor(private spinner: NgxSpinnerService, private translate: TranslateService, private cookieService: CookieService,
              private messageService: ToastMessageService, private tenantService: TenantService, private router: Router){}

  ngOnInit(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.cookieService.get(CommonUtil.KEY_TOKEN))) {
      this.router.navigate(['/auth/login']);
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
      },
      complete: () => {
        this.spinner.hide();
      }
    });
  }

}
