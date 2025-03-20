import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { EmployeeService } from '../employee/employee.service';
import { TenantDataService } from '../shared/tenant/tenant-data.service';
import { EmployeeDataService } from '../shared/employee/employee-data.service';
import { CommonUtil } from '../../util/CommonUtil.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { PrimeNG } from 'primeng/config';

var isLocaleLoaded: boolean = false;
var isUserLoaded: boolean = false;

@Injectable({
  providedIn: 'root'
})
export class TenantinitalizerService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private translateService: TranslateService,
    private cookieService: CookieService,
    private employeeService: EmployeeService,
    private tenantData: TenantDataService,
    private userData: EmployeeDataService,
    private spinner: NgxSpinnerService,
    private primeNg: PrimeNG
  ) { }

  initializeApp(): Promise<any> {
    console.log('Initializing app...');
    this.spinner.show();
    return new Promise((resolve, reject) => {
      this.http.get(`${environment.apiUrl}/tm/ping`)
        .subscribe({
          next: (resp: any) => {
            console.log('Tenant details loaded:', resp);
            this.tenantData.setCurrentTenant(resp.data);
            loadLocale(resp.data.locale, this.translateService, this.primeNg);
            //Lets try to resolve only after locale is loaded!
            loadUser(this.cookieService, this.router, this.employeeService, this.userData);
            const interval =  setInterval(() => {
              if (isLocaleLoaded && isUserLoaded) {
                resolve(true);
                clearInterval(interval);
                this.spinner.hide();
                this.router.navigate(['/dashboard']);
              }
            }, 500);
          },
          error: (error) => {
            console.error('Error loading tenant details:', error);
            resolve(false);
            this.router.navigate(['/auth/error']);
          },
          complete: () => console.log('App initialization complete.')
        });
    });
  }
}

async function loadLocale(localeId: string, translateService: TranslateService, primeng: PrimeNG) {
  try {
    translateService.setDefaultLang(localeId);
    translateService.get('primeng').subscribe(res => primeng.setTranslation(res));
    isLocaleLoaded = true;
  } catch (error) {
    console.error(`Failed to load locale ${localeId}: `, error);
  }
}

async function loadUser(cookieService: CookieService, router: Router, employeeService: EmployeeService, userData: EmployeeDataService) {
  try {
    if (cookieService.get(CommonUtil.KEY_TOKEN)) {
      employeeService.pingUser()
        .subscribe({
          next: (resp: any) => {
            isUserLoaded = true;
            userData.setCurrentEmployeeUser(resp.data);
            router.navigate(['/dashboard']);
          },
          error: (error: any) => {
            console.log(error);
            router.navigate(['/login']);
            isUserLoaded = true;
          }
        })
    }
    else {
      isUserLoaded = true;
      router.navigate(['/login']);
    }
  } catch (error) {
    console.error(`Failed to load user`);
  }
}

