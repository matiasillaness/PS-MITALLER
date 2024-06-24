import { AbstractControl, ValidatorFn } from '@angular/forms';

export class PatenteValidator {
  static patenteRegex = /^[A-Z]{2}\s\d{3}\s[A-Z]{2}$|^[A-Z]{3}\s\d{3}$/;

  static validate(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const forbidden = !PatenteValidator.patenteRegex.test(control.value);
      return forbidden ? { 'patenteInvalida': { value: control.value } } : null;
    };
  }
}