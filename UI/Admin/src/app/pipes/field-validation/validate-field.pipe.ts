import { Pipe, PipeTransform } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Pipe({
  name: 'validateField'
})
export class ValidateFieldPipe implements PipeTransform {

  transform(fieldName: string, formGroup: FormGroup): boolean {
    const control = formGroup.get(fieldName);
    return control ? control.invalid && control.touched : false;
  }

}
