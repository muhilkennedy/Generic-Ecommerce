import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonModule } from 'primeng/button';
import { FluidModule } from 'primeng/fluid';
import { ToastModule } from 'primeng/toast';
import { PickList, PickListModule } from 'primeng/picklist';
import { PanelMenu } from 'primeng/panelmenu';
import { MessageModule } from 'primeng/message';
import { SelectModule } from 'primeng/select';
import { PopoverModule } from 'primeng/popover';
import { FloatLabel } from 'primeng/floatlabel';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputGroupModule } from 'primeng/inputgroup';
import { SpinnerComponent } from '../../../shared/spinner';
import { MenuItem } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastMessageService } from '../../../../service/toastmessage/toast-message.service';
import { EmployeeService } from '../../../../service/employee/employee.service';
import { InputTextModule } from 'primeng/inputtext';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-manage-roles',
  imports: [ButtonModule, CommonModule, ToastModule, FluidModule, FormsModule, ReactiveFormsModule, TranslateModule, SpinnerComponent, InputGroupModule,
    PanelMenu, PickListModule, SelectModule, MessageModule, PopoverModule, FloatLabel, InputGroupAddonModule, InputTextModule],
  templateUrl: './manage-roles.component.html',
  styleUrl: './manage-roles.component.scss'
})
export class ManageRolesComponent {

  items!: MenuItem[];

  allPermissions!: any[];
  sourcePermissions: any[] = new Array<any>();
  targetPermissions: any[] = new Array<any>();

  roles: any[] = [];
  selectedRole: any;
  newRoleName: string = "";

  @ViewChild('pickListRef') pickList!: PickList;

  constructor(private employeeService: EmployeeService,
    private messageService: ToastMessageService, private cdr: ChangeDetectorRef,
    private spinner: NgxSpinnerService) { }

  ngOnInit() {
    this.spinner.show();
    this.employeeService.getAllRoles()
      .pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
    .subscribe({
        next: (response: any) => {
          this.roles = response.dataList;
          this.items = new Array<MenuItem>();
          response.dataList.forEach((role: any) => {
            this.addRole(role);
          })
        },
        error: (error: any) => {
          this.messageService.showErrorMessage("Failed to fetch roles");
        },
        complete: () => {
          this.spinner.hide();
        }
      });
    this.employeeService.getAllPermissions()
      .pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
    .subscribe({
        next: (response: any) => {
          this.allPermissions = response.dataList;
        },
        error: (error: any) => {
          this.messageService.showErrorMessage("Failed to fetch permissions");
        }
      })
  }

  addRole(role: any){
    let permissions: any[] = [];
    role.permissions.forEach((permission: any) => {
      permissions.push({
        label: permission.permission.permission,
        icon: 'pi pi-key',
      });
    })
    this.items.push({
      label: role.rolename,
      icon: 'pi pi-lock',
      items: permissions
    });
  }

  updatePermissionsPickList() {
    let allPerms: any[] = this.allPermissions.slice();
    this.sourcePermissions.length = 0;
    this.targetPermissions.length = 0;
    this.selectedRole.permissions.forEach((permission: any) => {
      if (allPerms.includes(permission.permission.permission)) {
        this.targetPermissions.push({ name: allPerms.splice(allPerms.indexOf(permission.permission.permission), 1)[0] });
      }
    });
    allPerms.forEach((item: any) => {
      this.sourcePermissions.push({ name: item });
    });
    this.sourcePermissions = [...this.sourcePermissions]; // clone to force re-evaluation
    this.targetPermissions = [...this.targetPermissions];
    this.cdr.detectChanges();
  }

  onPermissionSelect(event: any) {
    this.spinner.show();
    this.employeeService.addPermissionToRole({
      "roleId": this.selectedRole.rootid,
      "permissionNames": event.items.map( (item: any) => item.name )
    }).pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
    .subscribe({
      next: (response: any) => {
        this.messageService.showSuccessMessage("Permission added successfully");
      },
      error: (error: any) => {
        this.messageService.showError(error);
      },
      complete: () => {
        this.spinner.hide();
      }
    });
  }

  onPermissionRemove(event: any) {
    this.spinner.show();
    this.employeeService.removePermissionFromRole({
      "roleId": this.selectedRole.rootid,
      "permissionNames": event.items.map( (item: any) => item.name )
    }).pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
    .subscribe({
      next: (response: any) => {
        this.messageService.showSuccessMessage("Permission removed successfully");
      },
      error: (error: any) => {
        this.messageService.showError(error);
      },
      complete: () => {
        this.spinner.hide();
      }
    });
  }

  onSelectAllPermission(event: any) {
    this.spinner.show();
    this.employeeService.addAllPermissionsToRole({
      "roleId": this.selectedRole.rootid
    })
    .pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
    .pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
    .subscribe({
      next: (response: any) => {
        this.messageService.showSuccessMessage("Permission added successfully");
      },
      error: (error: any) => {
        this.messageService.showError(error);
      }
    });
  }

  onRemoveAllPermission(event: any) {
    this.spinner.show();
    this.employeeService.removeAllPermissionsToRole({
      "roleId": this.selectedRole.rootid
    }).pipe(
      finalize(() => {
        this.spinner.hide();
      })
    )
    .subscribe({
      next: (response: any) => {
        this.messageService.showSuccessMessage("Permissions removed successfully");
      },
      error: (error: any) => {
        this.messageService.showError(error);
      }
    });
  }

  createRole() {
      this.spinner.show();
      this.employeeService.createNewRole(this.newRoleName).pipe(
        finalize(() => {
          this.spinner.hide();
        })
      )
      .subscribe({
        next: (response: any) => {
          this.addRole(response.data);
          this.items = [...this.items];
          this.roles.push(response.data);
          this.messageService.showSuccessMessage("New Role Added Successfully");
        },
        error: (error: any) => {
          this.messageService.showError(error);
        }
      })
  }

}
