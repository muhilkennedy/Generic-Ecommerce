import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AppMenuitem } from './app.menuitem';
import { TranslateService } from '@ngx-translate/core';
import { Permissions } from '../../model/permissions';
import { EmployeeDataService } from '../../service/shared/employee/employee-data.service';
import { CommonUtil } from '../../util/CommonUtil.service';

@Component({
    selector: 'app-menu',
    standalone: true,
    imports: [CommonModule, AppMenuitem, RouterModule],
    template: `<ul class="layout-menu">
        <ng-container *ngFor="let item of model; let i = index">
            <li app-menuitem *ngIf="!item.separator" [item]="item" [index]="i" [root]="true"></li>
            <li *ngIf="item.separator" class="menu-separator"></li>
        </ng-container>
    </ul> `
})
export class AppMenu {
    //model: MenuItem[] = [];
    model: any[] = [];

    // attributes: {
    //     "Permission" : Permissions.MANAGE_USERS
    //   }

    constructor(private translate: TranslateService, private userData: EmployeeDataService, private router: Router) {
        //this.translate.setDefaultLang('en');
    }

    ngOnInit() {
        this.model = [
            {
                label: this.translate.instant('Home'),
                items: [{ label: this.translate.instant('Dashboard'), icon: 'pi pi-fw pi-home', routerLink: ['/dashboard'] }]
            },
            {
                label: this.translate.instant('Tenant Manager'),
                Permission : [ Permissions.SUPER_USER ],
                items: [
                    { label: this.translate.instant('View Tenant'), icon: 'pi pi-fw pi-list', routerLink: ['/pages/view-tenant'] },
                    { label: this.translate.instant('Onboard Tenant'), icon: 'pi pi-fw pi-plus', routerLink: ['/pages/onboard-tenant'] },
                    { label: this.translate.instant('Edit Tenant'), icon: 'pi pi-fw pi-pencil', routerLink: ['/uikit/formlayout'] }
                ]
            },
            {
                label: this.translate.instant('User Manager'),
                icon: 'pi pi-fw pi-briefcase',
                routerLink: ['/pages'],
                Permission : [ Permissions.MANAGE_USERS, Permissions.ADMIN ],
                items: [
                    {
                        label: this.translate.instant('Employee'),
                        icon: 'pi pi-fw pi-users',
                        items: [
                            {
                                label: this.translate.instant('View'),
                                icon: 'pi pi-fw pi-list',
                                routerLink: ['/auth/access']
                            },
                            {
                                label: this.translate.instant('Onboard'),
                                icon: 'pi pi-fw pi-user-plus',
                                routerLink: ['/auth/login'],
                                Permission : [ Permissions.EDIT_USERS ],
                            },
                            {
                                label: this.translate.instant('Edit'),
                                icon: 'pi pi-fw pi-user-edit',
                                routerLink: ['/auth/error'],
                                Permission : [ Permissions.EDIT_USERS ],
                            },
                            {
                                label: this.translate.instant('Permissions'),
                                icon: 'pi pi-fw pi-key',
                                items: [
                                    {
                                        label: this.translate.instant('View'),
                                        icon: 'pi pi-fw pi-list-check',
                                        routerLink: ['/auth/login'],
                                        Permission : [ Permissions.MANAGE_USERS ],
                                    },
                                    {
                                        label: this.translate.instant('Edit'),
                                        icon: 'pi pi-fw pi-pencil',
                                        routerLink: ['/auth/error'],
                                        Permission : [ Permissions.ADMIN ],
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        label: this.translate.instant('Customer'),
                        icon: 'pi pi-fw pi-users',
                        Permission : [ Permissions.MANAGE_USERS ],
                        items: [
                            {
                                label: this.translate.instant('View'),
                                icon: 'pi pi-fw pi-list',
                                routerLink: ['/auth/access']
                            },
                            {
                                label: this.translate.instant('Edit'),
                                icon: 'pi pi-fw pi-user-edit',
                                routerLink: ['/auth/error'],
                                Permission : [ Permissions.EDIT_USERS ],
                            }
                        ]
                    },
                ]
            },
            {
                label: this.translate.instant('Inventory Manager'),
                routerLink: ['/pages'],
                Permission : [ Permissions.MANAGE_PRODUCTS ],
                items: [
                    {
                        label: this.translate.instant('Supplier Manager'),
                        icon: 'pi pi-fw pi-warehouse',
                        Permission : [ Permissions.MANAGE_SUPPLIER ],
                        items: [
                            { label: this.translate.instant('View'), icon: 'pi pi-fw pi-list', routerLink: ['/uikit/formlayout'] },
                            { label: this.translate.instant('Add'), icon: 'pi pi-fw pi-plus', routerLink: ['/uikit/formlayout'] },
                            { label: this.translate.instant('Edit'), icon: 'pi pi-fw pi-pencil', routerLink: ['/uikit/formlayout'] }
                        ]
                    },
                    {
                        label: this.translate.instant('Product Manager'),
                        icon: 'pi pi-fw pi-shopping-bag',
                        Permission : [ Permissions.MANAGE_PRODUCTS ],
                        items: [
                            { label: this.translate.instant('View'), icon: 'pi pi-fw pi-list-check', routerLink: ['/uikit/formlayout'] },
                            { label: this.translate.instant('Add'), icon: 'pi pi-fw pi-plus', routerLink: ['/uikit/formlayout'], Permission : [ Permissions.EDIT_PRODUCTS ] },
                            { label: this.translate.instant('Edit'), icon: 'pi pi-fw pi-pencil', routerLink: ['/uikit/formlayout'], Permission : [ Permissions.EDIT_PRODUCTS ] }
                        ]
                    },
                    { 
                        label: this.translate.instant('Servicable Pincodes'),
                        icon: 'pi pi-fw pi-truck',
                        routerLink: ['/uikit/formlayout'],
                        Permission : [ Permissions.EDIT_PRODUCTS ]
                    }
                ]
            },
            {
                label: this.translate.instant('Sales Manager'),
                routerLink: ['/pages'],
                Permission : [ Permissions.MANAGE_ORDERS ],
                items: [
                    { label: this.translate.instant('Point Of Sale'), icon: 'pi pi-fw pi-shop', routerLink: ['/uikit/formlayout'] },
                    {
                        label: this.translate.instant('Order Management'),
                        icon: 'pi pi-fw pi-shopping-cart',
                        Permission : [ Permissions.EDIT_ORDERS ],
                        items: [
                            { label: this.translate.instant('View'), icon: 'pi pi-fw pi-list', routerLink: ['/uikit/formlayout'] },
                            { label: this.translate.instant('Edit'), icon: 'pi pi-fw pi-cart-plus', routerLink: ['/uikit/formlayout'] }
                        ]
                    },
                    {
                        label: this.translate.instant('Shipping Manager'),
                        icon: 'pi pi-fw pi-truck',
                        Permission : [ Permissions.EDIT_ORDERS ],
                        items: [
                            { label: this.translate.instant('Shipped Products'), icon: 'pi pi-fw pi-list', routerLink: ['/uikit/formlayout'] },
                            { label: this.translate.instant('Create Shipment'), icon: 'pi pi-fw pi-plus-circle', routerLink: ['/uikit/formlayout'] },
                            { label: this.translate.instant('Update Status'), icon: 'pi pi-fw pi-pencil', routerLink: ['/uikit/formlayout'] }
                        ]
                    }
                ]
            },
            {
                label: this.translate.instant('Reporting Manager'),
                Permission : [ Permissions.MANAGE_REPORTING ],
                items: [
                    {
                        label: this.translate.instant('Analytics'),
                        icon: 'pi pi-fw pi-chart-line',
                        routerLink: ['/documentation']
                    }
                ]
            },
            {
                label: this.translate.instant('Promotional Manager'),
                Permission : [ Permissions.MANAGE_PROMOTIONS ],
                items: [
                    {
                        label: this.translate.instant('Coupon Management'), 
                        icon: 'pi pi-fw pi-gift',
                        items: [
                            { label: this.translate.instant('View'), icon: 'pi pi-fw pi-list-check', routerLink: ['/uikit/formlayout'] },
                            { label: this.translate.instant('Add'), icon: 'pi pi-fw pi-plus-circle', routerLink: ['/uikit/formlayout'], Permission : [ Permissions.EDIT_COUPONS ] }
                        ]
                    },
                    {
                        label: this.translate.instant('Receipt Templates'),
                        icon: 'pi pi-fw pi-receipt',
                        routerLink: ['/documentation'],
                        Permission : [ Permissions.EDIT_PROMOTIONS ],
                    },
                    {
                        label: this.translate.instant('Email Templates'),
                        icon: 'pi pi-fw pi-inbox',
                        routerLink: ['/documentation'],
                        Permission : [ Permissions.EDIT_PROMOTIONS ],
                    },
                    {
                        label: this.translate.instant('Home Page Banners'),
                        icon: 'pi pi-fw pi-images',
                        routerLink: ['/documentation'],
                        Permission : [ Permissions.EDIT_PROMOTIONS ],
                    }
                ]
            },
            {
                label: this.translate.instant('Configuration Manager'),
                Permission : [ Permissions.ADMIN ],
                items: [
                    {
                        label: this.translate.instant('Email Configuration'),
                        icon: 'pi pi-fw pi-envelope',
                        routerLink: ['/documentation']
                    },
                    {
                        label: this.translate.instant('Storage Configuration'),
                        icon: 'pi pi-fw pi-cloud',
                        routerLink: ['/documentation']
                    }
                ]
            },
            {
                label: this.translate.instant('Service Manager'),
                Permission : [ Permissions.SUPER_USER ],
                items: [
                    {
                        label: this.translate.instant('Audit Logs'),
                        icon: 'pi pi-fw pi-book',
                        routerLink: ['/documentation']
                    },
                    {
                        label: this.translate.instant('Scheduled Tasks'),
                        icon: 'pi pi-fw pi-hourglass',
                        url: 'https://github.com/primefaces/sakai-ng',
                        target: '_blank'
                    },
                    {
                        label: this.translate.instant('File Storage'),
                        icon: 'pi pi-fw pi-file',
                        routerLink: ['/documentation']
                    }
                ]
            }
        ];

        if(CommonUtil.isNullOrEmptyOrUndefined(this.userData.getCurrentEmployeeUser())){
            this.router.navigate(['/login']);
        }

        // Incase of super user we render all menu items
        if(!this.userData.getCurrentEmployeeUser().userPermissions.includes(Permissions.SUPER_USER)){
            this.model = this.filterModelByPermission(this.model, this.userData.getCurrentEmployeeUser().userPermissions);
        }
    }

    filterModelByPermission(model: any[], allowedPermissions: string[]) {
        return model
          .map(item => {
            // If item has nested items, apply filtering recursively
            if (item.items) {
              item.items = this.filterModelByPermission(item.items, allowedPermissions);
            }
            // Keep the item if:
            // - No permissions defined (considered public)
            // - OR at least one permission matches the allowed permissions
            if (!item.Permission || item.Permission.some((perm: string) => allowedPermissions.includes(perm))) {
              return item;
            }
            return null;
          })
          .filter(item => item !== null);
    }

}


