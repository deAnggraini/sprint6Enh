import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'maxLength'
})
export class MaxLengthPipe implements PipeTransform {

  transform(value: string, max: number, ellipsis: string = "..."): string {
    if (!value) return "";
    if (value.length > max) return `${value.substr(0, max)}${ellipsis}`;
    return value;
  }

}
