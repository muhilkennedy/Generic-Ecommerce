import { Component, Inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { FluidModule } from 'primeng/fluid';
import { InputTextModule } from 'primeng/inputtext';
import { InputGroupAddon } from 'primeng/inputgroupaddon';
import { InputIcon, InputIconModule } from 'primeng/inputicon';
import { FloatLabelModule } from 'primeng/floatlabel';
import { TenantService } from '../../../service/tenant/tenant.service';
import { ToastMessageService } from '../../../service/toastmessage/toast-message.service';

@Component({
  selector: 'app-view-tenant-details',
  imports: [ButtonModule, FormsModule, ReactiveFormsModule, InputGroupModule, FloatLabelModule, InputGroupAddon,
            TranslateModule, NgxSpinnerModule, FluidModule, InputTextModule, InputIconModule],
  templateUrl: './view-tenant-details.component.html',
  styleUrl: './view-tenant-details.component.scss'
})
export class ViewTenantDetailsComponent {

  tenant!: any;

  constructor(public config: DynamicDialogConfig, private tenantService: TenantService, private spinner: NgxSpinnerService,
    private messageSerivce: ToastMessageService){
    this.tenant = config.data;
  }

  saveContact(){
    //TODO
  }

  saveLocale(){
    this.spinner.show();
    let body = {
      locale: this.tenant.locale
    };
    this.tenantService.updateTenant(this.tenant.rootid, body).subscribe({
      next: (response) => {
        this.messageSerivce.showSuccessMessage("Tenant locale updated successfully!");
      },
      error: (error) => {
        this.messageSerivce.showErrorMessage("Failed to update tenant locale!");
      },
      complete: () => {
        this.spinner.hide();
        window.location.reload();
      }
    });
  }

}
