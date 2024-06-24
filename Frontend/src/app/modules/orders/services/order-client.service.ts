import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderDisponibleResponse, OrderResponse } from '../models/Order.response.all';
import { OrderForClientRequest, OrderPartialRequest } from '../models/Order.request.all';

@Injectable({
  providedIn: 'root'
})
export class OrderClientService {

  constructor(private _httpClient: HttpClient) { }

  BASE_URL = 'http://localhost:8080/orden';


  obtenerOrdenesDisponibles(
    fechaInicio?: string,
    fechaFin?: string,
  ):Observable<OrderDisponibleResponse[]> {

    let params = new HttpParams();
    if (fechaInicio) {
      params = params.set('fechaInicio', fechaInicio);
    }
    if (fechaFin) {
      params = params.set('fechaFin', fechaFin);
    }

    return this._httpClient.get<OrderDisponibleResponse[]>(this.BASE_URL + '/disponibles', { params });
  }

  guardarOrden(
    id: number,
    order: OrderForClientRequest
  ):Observable<any> {
    return this._httpClient.post('http://localhost:8080/orden/guardar/' + id, order, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      responseType: 'text' // Esto es importante para obtener el texto de respuesta como es.
    });
  }



  obtenerOrdenesCliente(): Observable<OrderResponse[]> {
    return this._httpClient.get<OrderResponse[]>('http://localhost:8080/orden/ocupadas');
  }

  guardarOrdenParcial(ordenes: OrderPartialRequest[]): Observable<any> {
    return this._httpClient.post('http://localhost:8080/orden/guardar-parcial', ordenes);
  }
}
