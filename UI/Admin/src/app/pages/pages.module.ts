import { CUSTOM_ELEMENTS_SCHEMA, NgModule, Provider } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { MenuModule } from 'primeng/menu';
import { CommonModule } from '@angular/common';
import { StyleClassModule } from 'primeng/styleclass';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { NgxSpinnerModule } from "ngx-spinner";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AppModule } from '../../app.module';

@NgModule({
  declarations:[
    //DashboardComponent,
  ],
  imports: [
    AppModule,
    BrowserModule,
    RouterModule,
    MenuModule,
    CommonModule,
    StyleClassModule,
    TableModule,
    ToastModule,
    BrowserAnimationsModule,
    NgxSpinnerModule.forRoot()
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
  ]
})
export class PagesModule {}