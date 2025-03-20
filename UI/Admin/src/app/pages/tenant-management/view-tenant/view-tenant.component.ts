import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ButtonModule } from 'primeng/button';
import { DatePicker } from 'primeng/datepicker';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FluidModule } from 'primeng/fluid';
import { TableModule, TableRowCollapseEvent, TableRowExpandEvent } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { InputIconModule } from 'primeng/inputicon';
import { IconFieldModule } from 'primeng/iconfield';
import { ProgressBarModule } from 'primeng/progressbar';
import { TagModule } from 'primeng/tag';
import { InputTextModule } from 'primeng/inputtext';
import { RatingModule } from 'primeng/rating';
import { KnobModule } from 'primeng/knob';
import { DialogModule } from 'primeng/dialog'
import { InputGroupModule } from 'primeng/inputgroup';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { ToastMessageService } from '../../../service/toastmessage/toast-message.service';
import { TenantService } from '../../../service/tenant/tenant.service';
import { DialogService, DynamicDialogModule, DynamicDialogRef } from 'primeng/dynamicdialog';
import { DialogFooter } from '../../../layout/shared/dialog.footer';
import { ViewTenantDetailsComponent } from '../view-tenant-details/view-tenant-details.component';
import { CommonUtil } from '../../../util/CommonUtil.service';

@Component({
  selector: 'app-view-tenant',
  imports: [TableModule, ButtonModule, CommonModule, ToastModule, FluidModule, ReactiveFormsModule, TranslateModule, InputTextModule, DialogModule, DynamicDialogModule,
    FloatLabelModule, KnobModule, NgxSpinnerModule, IconFieldModule, InputIconModule, ProgressBarModule, TagModule, FormsModule, RatingModule,
    InputGroupModule, ToggleButtonModule],
  templateUrl: './view-tenant.component.html',
  styleUrl: './view-tenant.component.scss'
})
export class ViewTenantComponent {

  tenants!: any[];
  expandedRows = {};
  tenantSubscriptions!: any[];

  currentDate = new Date();

  showDialog: boolean = false;
  dialogRef: DynamicDialogRef | undefined;

  constructor(private changeDetector: ChangeDetectorRef, private spinner: NgxSpinnerService, public dialogService: DialogService,
              private messageService: ToastMessageService, private tenantService: TenantService, private translate: TranslateService) { }

  ngAfterViewInit() {
    this.changeDetector.detectChanges();
  }

  ngOnInit() {
    this.spinner.show();
    this.tenantService.getAllTenants().subscribe({
      next: (response: any) => {
        this.tenants = response.dataList;
        this.spinner.hide();
      },
      error: (error: any) => {
        this.spinner.hide();
        this.messageService.showErrorMessage('Failed to fetch tenants');
      }
    })
  }

  ngOnDestroy() {
    if (this.dialogRef) {
        this.dialogRef.close();
    }
  }

  // displayDialog(val: any) {
  //   this.showDialog = true;
  //   this.changeDetector.detectChanges();
  // }

  displayDialog(tenant: any) {
    this.dialogRef = this.dialogService.open(ViewTenantDetailsComponent, {
        header: this.translate.instant('Tenant Details'),
        width: '50vw',
        modal: true,
        contentStyle: { overflow: 'auto' },
        breakpoints: {
            '960px': '75vw',
            '640px': '90vw'
        },
        maximizable: true,
        templates: {
            footer: DialogFooter
        },
        data: tenant
    });

    this.dialogRef.onClose.subscribe((data: any) => {
        //any item selected on dialog
    });

    this.dialogRef.onMaximize.subscribe((value) => {
        //maximize action
    });
  }

  getDaysLeft(start: number, end: number): number {
    if (this.currentDate.getTime() < start) {
      return Math.ceil((end - start) / (1000 * 60 * 60 * 24));
    } else if (this.currentDate.getTime() > end) {
      return 0;
    } else {
      return Math.ceil((end - this.currentDate.getTime()) / (1000 * 60 * 60 * 24));
    }
  }

  getTotalDays(start: number, end: number): number {
    return Math.ceil((end - start) / (1000 * 60 * 60 * 24));
  }

  getSubscriptionSeverity(daysLeft: number): any {
    if (daysLeft <= 10) return 'danger';
    if (daysLeft <= 30) return 'warn';
    return 'success';
  }

  canShowSubsWarning(start: number, end: number): boolean {
    return this.getDaysLeft(start, end) <= 30;
  }

  getTenantLogoThumbnail(tenant: any): string {
    return CommonUtil.isNullOrEmptyOrUndefined(tenant.tenantDetail.logoThumbnail) ? '../../../../assets/app/logo_mken_thumbnail.png' : tenant.tenantDetail.logoThumbnail;
  }

  expandAll() {
    this.expandedRows = this.tenants.reduce((acc, p) => (acc[p.rootid] = true) && acc, {});
  }

  collapseAll() {
    this.expandedRows = {};
  }

  loadTenantSubscriptions(tenantId: number) {
    this.spinner.show();
    this.tenantService.getTenantSubscriptions(tenantId).subscribe({
      next: (response: any) => {
        this.tenantSubscriptions = response.dataList;
      },
      error: (error: any) => {
        this.messageService.showErrorMessage('Failed to fetch tenant subscriptions');
      },
      complete: () => {
        this.spinner.hide();
      }
    })
  }

  onRowExpand(event: TableRowExpandEvent) {
    //this.messageService.add({ severity: 'info', summary: 'Product Expanded', detail: event.data.name, life: 3000 });
  }

  onRowCollapse(event: TableRowCollapseEvent) {
    //this.messageService.add({ severity: 'success', summary: 'Product Collapsed', detail: event.data.name, life: 3000 });
  }

  saveContact(){
    //TODO
  }

  toggleTenantStatus(){
    //TODO
  }

}
