import { Routes } from '@angular/router';
import { ViewTenantComponent } from './tenant-management/view-tenant/view-tenant.component';
import { OnboardTenantComponent } from './tenant-management/onboard-tenant/onboard-tenant.component';

export default [
    // { path: 'documentation', component: Documentation },
    { path: 'onboard-tenant', component: OnboardTenantComponent },
    { path: 'view-tenant', component: ViewTenantComponent },
    { path: '**', redirectTo: '/notfound' }
] as Routes;
