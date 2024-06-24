import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'lowerCaseFirstUpper'
})
export class LowerCaseFirstUpperPipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    return value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
  }

}
