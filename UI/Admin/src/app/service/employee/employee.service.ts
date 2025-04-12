import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) { }

  pingUser(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/tm/employee`);
  }

  login(email: string, password: string, rememberme: boolean): Observable<any> {
    return this.http.post(`${environment.apiUrl}/tm/user/employee/login`, {
      "emailid": email,
      "password": password
    },
    { observe: 'response' })
  }

  getAllRoles(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/tm/role`);
  }

  searchEmployeesByName(keyword: string): Observable<any> {
    return this.http.get(`${environment.apiUrl}/tm/employee/search?keyword=${keyword}`);
  }

  onboardEmployee(employee: any): Observable<any> {
    return this.http.post(`${environment.apiUrl}/tm/employee`, employee);
  }

  assignRolesToEmployee(employeeId: number, roleIds: number[]): Observable<any> {
    return this.http.post(`${environment.apiUrl}/tm/role/assign/${employeeId}`, roleIds);
  }

  getAllEmployees(pageSize: number, pageNumber: number, sortField: string, sortOrder: string): Observable<any> {
    return this.http.get(`${environment.apiUrl}/tm/employee/all?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=${sortField}&sortOrder=${sortOrder}`);
  }

  filterAllEmployees(pageSize: number, pageNumber: number, sortField: string, sortOrder: string, body: any): Observable<any> {
    return this.http.post(`${environment.apiUrl}/tm/employee/search/filter?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=${sortField}&sortOrder=${sortOrder}`, body);
  }

}
