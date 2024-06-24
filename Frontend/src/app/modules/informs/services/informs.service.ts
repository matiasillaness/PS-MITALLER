import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiciosMasUtilizados } from '../da/ServiciosMasUtilizados';
import { PlataUsada } from '../da/PlataUsada';


@Injectable({
  providedIn: 'root'
})
export class InformsService {

  constructor(private _httoClient: HttpClient) { }

  getServiciosMasUtilizados(): Observable<ServiciosMasUtilizados[]> {
    return this._httoClient.get<ServiciosMasUtilizados[]>('http://localhost:8080/serviciosMasUtilizados');
  }

  getPlataPorMes(): Observable<PlataUsada[]> {
    let param = new HttpParams();

    let anioActual = new Date().getFullYear();

    param = param.set('year', anioActual);

    return this._httoClient.get<PlataUsada[]>('http://localhost:8080/cantidadDeVentaPorMes', { params: param });
  }

  getPlataQueMasSeGasta(): Observable<PlataUsada[]> {
    let param = new HttpParams();

    let anioActual = new Date().getFullYear();

    param = param.set('year', anioActual);
    return this._httoClient.get<PlataUsada[]>('http://localhost:8080/mesesConMasPlataInvertida', { params: param });
  }


  getTotalGastado(): Observable<number> {
    return this._httoClient.get<number>('http://localhost:8080/totalGastado');
  }

  getTotalGanado(): Observable<number> {
    return this._httoClient.get<number>('http://localhost:8080/totalFacturado');
  }
}
