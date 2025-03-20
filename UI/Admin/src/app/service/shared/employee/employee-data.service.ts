import { Injectable } from '@angular/core';
import { Employee } from '../../../model/employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeDataService {

  employee!: Employee;

  constructor() { }

  setCurrentEmployeeUser(emp: Employee){
    this.employee = emp;
  }

  getCurrentEmployeeUser(){
    return this.employee;
  }
  
}
