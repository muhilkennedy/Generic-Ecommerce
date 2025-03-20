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
}
