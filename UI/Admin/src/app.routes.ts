import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';
import { Notfound } from './app/pages/notfound/notfound';
import { DashboardComponent } from './app/pages/dashboard/dashboard.component';
import { OnboardTenantComponent } from './app/pages/tenant-management/onboard-tenant/onboard-tenant.component';
import { ViewTenantComponent } from './app/pages/tenant-management/view-tenant/view-tenant.component';

export const appRoutes: Routes = [
    {
        path: '',
        component: AppLayout,
        children: [
            { path: 'dashboard', component: DashboardComponent },
            //dev
            // { path: 'uikit', loadChildren: () => import('./app/pages/uikit/uikit.routes') },
            { path: 'pages', loadChildren: () => import('./app/pages/pages.routes') }
        ]
    },
    { path: 'notfound', component: Notfound },
    { path: 'auth', loadChildren: () => import('./app/pages/auth/auth.routes') },
    { path: '**', redirectTo: '/auth/login' }
];
