import { Routes } from '@angular/router';
import { ViewTenantComponent } from './tenant-management/view-tenant/view-tenant.component';
import { OnboardTenantComponent } from './tenant-management/onboard-tenant/onboard-tenant.component';
import { ViewEmployeesComponent } from './user-management/employee-management/view-employees/view-employees.component';
import { OnboardEmployeesComponent } from './user-management/employee-management/onboard-employees/onboard-employees.component';
import { ManageRolesComponent } from './user-management/roles-management/manage-roles/manage-roles.component';
import { AssignRolesComponent } from './user-management/roles-management/assign-roles/assign-roles.component';

export default [
    // { path: 'documentation', component: Documentation },
    { path: 'onboard-tenant', component: OnboardTenantComponent },
    { path: 'view-tenant', component: ViewTenantComponent },
    { path: 'onboard-employee', component: OnboardEmployeesComponent },
    { path: 'view-employees', component: ViewEmployeesComponent },
    { path: 'manage-roles', component: ManageRolesComponent },
    { path: 'assign-roles', component: AssignRolesComponent },
    { path: '**', redirectTo: '/notfound' }
] as Routes;
