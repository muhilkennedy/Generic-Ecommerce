import { Injectable } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { TranslatePipe } from "@ngx-translate/core";

@Injectable({
    providedIn: 'root'
})
export class CommonUtil {

    static KEY_TOKEN = 'x-token';
    static KEY_LOCALE = 'x-locale';
    static DATE_FORMAT_PLAIN = "dd/MM/yyyy";

    static states: string[] = ["Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
        "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
        "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
        "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
        "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
        "Uttar Pradesh", "Uttarakhand", "West Bengal"];
    static locales: any[] = [
        {name: 'English', code: 'en'}, 
        {name: 'Tamil', code: 'ta'},
        {name: 'Kannada', code: 'kn'}];

    static isNullOrEmptyOrUndefined(value: any): boolean {
        return (value == undefined || value == null || value == '' || value == 'undefined');
    }

    static isNotNullOrEmptyOrUndefined(value: any): boolean {
        return !this.isNullOrEmptyOrUndefined(value);
    }

    static convertSize(bytes: number, decimals = 2): string {
        let units = ['B', 'KB', 'MB', 'GB', 'TB'];
        let i = 0;
        for (i; bytes > 1024; i++) {
            bytes /= 1024;
        }
        return parseFloat(bytes.toFixed(decimals)) + ' ' + units[i]
    }

    static getErrorStyleClassForField(formGroup: FormGroup, field: string): string {
        if (formGroup.get(field)?.invalid && formGroup.get(field)?.touched) {
            return 'ng-dirty';
        }
        return '';
    }

    static isInvalidField(formGroup: FormGroup, field: string): boolean {
        const control = formGroup.get(field);
        return control ? control.invalid && control.touched : false;
    }
}