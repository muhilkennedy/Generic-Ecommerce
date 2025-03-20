import { CUSTOM_ELEMENTS_SCHEMA, NgModule, Provider } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { HttpInterceptorService } from './app/service/httpinterceptor/http-interceptor.service';
import { RouterModule } from '@angular/router';
import { MenuModule } from 'primeng/menu';
import { CommonModule } from '@angular/common';
import { StyleClassModule } from 'primeng/styleclass';
import { AppConfigurator } from './app/layout/component/app.configurator';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { NgxSpinnerModule } from "ngx-spinner";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    BrowserModule,
    RouterModule,
    MenuModule,
    CommonModule,
    StyleClassModule,
    AppConfigurator,
    TableModule,
    ToastModule,
    BrowserAnimationsModule,
    NgxSpinnerModule.forRoot()
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}

export const httpInterceptorProvider: Provider = { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true };