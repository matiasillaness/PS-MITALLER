import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {  VentaRequest } from '../models/Payment.all';
import { Observable } from 'rxjs';
import { FacturaVentaResponse } from '../models/PaymentResponse.all';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private _httpClient: HttpClient) { }

  postPayment(request: VentaRequest) : Observable<FacturaVentaResponse> {
    return this._httpClient.post<FacturaVentaResponse>('http://localhost:8080/venta', request);
  }

  deletePayment(nombre: string) : Observable<boolean> {
    return this._httpClient.delete<boolean>('http://localhost:8080/venta/eliminar/' + nombre);
  }

  getPayments(
    fechaInicio?: string,
    fechaFin?: string,
    tipoPago?: string,
    tipoFactura?: string,
    numeroFactura?: string,
    descripcion?: string,
    cliente?: string,
    dadaDeBaja?: boolean,
  ) : Observable<FacturaVentaResponse[]> {
    let params = new HttpParams();
    if (fechaInicio) {
      params = params.set('fechaInicio', fechaInicio);
    }
    if (fechaFin) {
      params = params.set('fechaFin', fechaFin);
    }
    if (tipoPago) {
      params = params.set('tipoPago', tipoPago);
    }
    if (tipoFactura) {
      params = params.set('tipoFactura', tipoFactura);
    }
    if (numeroFactura) {
      params = params.set('numeroFactura', numeroFactura);
    }
    if (descripcion) {
      params = params.set('descripcion', descripcion);
    }
    if (cliente) {
      params = params.set('cliente', cliente);
    }
    if (dadaDeBaja) {
      params = params.set('dadaDeBaja', dadaDeBaja.toString());
    }
    return this._httpClient.get<FacturaVentaResponse[]>('http://localhost:8080/venta', { params });
  }

  
}
