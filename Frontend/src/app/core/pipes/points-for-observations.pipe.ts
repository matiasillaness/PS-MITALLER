import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'pointsForObservations'
})
export class PointsForObservationsPipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    if (!value) return value;

    if (value.length > 8) {
      // Buscar el último espacio dentro de los primeros 8 caracteres
      const lastSpaceIndex = value.substring(0, 15).lastIndexOf(' ');
      // Si se encuentra un espacio, cortar en ese punto; de lo contrario, cortar en el 8vo carácter
      return lastSpaceIndex !== -1 ? value.substring(0, lastSpaceIndex) + '...' : value.substring(0, 8) + '...';
    }

    return value;
  }

}
