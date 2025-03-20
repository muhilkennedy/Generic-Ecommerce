import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TenantService {

  constructor(private http: HttpClient) { }

  createNewTenant(body: any): Observable<any>{
    return this.http.post(`${environment.apiUrl}/tm/admin/tenant`, body);
  }

  createTenantSubscription(body: any, tenantId: number): Observable<any>{
    return this.http.post(`${environment.apiUrl}/tm/admin/tenant/subscription?tenantId=${tenantId}`, body);
  }

  getAllTenants(): Observable<any>{
    return this.http.get(`${environment.apiUrl}/tm/admin/tenant/all`);
  }

  getTenantSubscriptions(tenantId: number): Observable<any>{
    return this.http.get(`${environment.apiUrl}/tm/admin/tenant/subscription?tenantId=${tenantId}`);
  }

  updateTenant(tenantId: string, body: any): Observable<any>{
    return this.http.put(`${environment.apiUrl}/tm/admin/tenant?tenantId=${tenantId}`, body);
  }

  getAllTenantsCountForDashboard(): Observable<any>{
    return this.http.get(`${environment.apiUrl}/tm/admin/tenant/all/count`);
  }

}
