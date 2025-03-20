import { Entity } from "./entity";

export class Employee extends Entity {
    fname!: string;
    lname!: string;
    emailid!: string;
    secondaryemail!: string;
    locale!: string;
    mobile!: string;
    reportsto!: number;
    designation?: string; //optional
    employeeinfo!: EmployeeInfo;
    userPermissions!: Array<string>
}

export class EmployeeInfo extends Entity {
    dob!: Date;
    gender!: string;
    profilepic!: string;
    proofFileId!: number;
}