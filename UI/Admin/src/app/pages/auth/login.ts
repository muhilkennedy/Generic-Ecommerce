import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { AppFloatingConfigurator } from '../../layout/component/app.floatingconfigurator';
import { EmployeeService } from '../../service/employee/employee.service';
import { CookieService } from 'ngx-cookie-service';
import { CommonUtil } from '../../util/CommonUtil.service';
import { HttpResponse } from '@angular/common/http';
import { EmployeeDataService } from '../../service/shared/employee/employee-data.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [ButtonModule, CheckboxModule, InputTextModule, PasswordModule, FormsModule, RouterModule, RippleModule, AppFloatingConfigurator, NgxSpinnerModule, ReactiveFormsModule],
    template: `
        <ngx-spinner type='ball-clip-rotate-multiple'></ngx-spinner>
        <app-floating-configurator />
        <div class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] overflow-hidden">
            <div class="flex flex-col items-center justify-center">
                <div style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
                    <div class="w-full bg-surface-0 dark:bg-surface-900 py-20 px-8 sm:px-20" style="border-radius: 53px">
                        <div class="text-center mb-8">
                            <svg style="display:inline" baseProfile="tiny" height="50" version="1.2" width="200" xmlns="http://www.w3.org/2000/svg" xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xlink="http://www.w3.org/1999/xlink"><defs /><image height="100%" width="100%" x="0" xlink:href="data:image/png;base64,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" y="0" /></svg>
                            <div class="text-surface-900 dark:text-surface-0 text-3xl font-medium mb-4">Welcome to MKEN!</div>
                            <span class="text-muted-color font-medium">Sign in to continue</span>
                        </div>
                        <div>
                            <label for="email1" class="block text-surface-900 dark:text-surface-0 text-xl font-medium mb-2">Email</label>
                            <input pInputText id="email1" type="text" placeholder="Email address" class="w-full md:w-[30rem] mb-8" [(ngModel)]="email" />

                            <label for="password1" class="block text-surface-900 dark:text-surface-0 font-medium text-xl mb-2">Password</label>
                            <p-password id="password1" [(ngModel)]="password" placeholder="Password" [toggleMask]="true" styleClass="mb-4" [fluid]="true" [feedback]="false"></p-password>

                            <div class="flex items-center justify-between mt-2 mb-8 gap-8">
                                <div class="flex items-center">
                                    <p-checkbox [(ngModel)]="checked" id="rememberme1" binary class="mr-2"></p-checkbox>
                                    <label for="rememberme1">Remember me</label>
                                </div>
                                <span class="font-medium no-underline ml-2 text-right cursor-pointer text-primary">Forgot password?</span>
                            </div>
                            <p-button label="Sign In" styleClass="w-full" (click)="login()"></p-button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
})
export class Login {
    email: string = '';
    password: string = '';
    checked: boolean = false;

    constructor(private employeeService: EmployeeService, private route: Router, private spinner: NgxSpinnerService,
        private cookieService: CookieService, private employeeData: EmployeeDataService){}

    login(){
        this.spinner.show();
        this.employeeService.login(this.email, this.password, this.checked)
            .subscribe({
                next: (resp: HttpResponse<any>) => {
                    this.cookieService.set(CommonUtil.KEY_TOKEN, resp.headers.get(CommonUtil.KEY_TOKEN)!);
                    this.cookieService.set(CommonUtil.KEY_LOCALE, resp.body.locale);
                    this.employeeData.setCurrentEmployeeUser(resp.body);
                    this.route.navigate(['/dashboard']);
                    this.spinner.hide();
                },
                error: (err: any) => {
                    this.route.navigate(['/auth/error']);
                }
            })
    }
}
