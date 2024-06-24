import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BuyRequest } from '../models/BuyRequest.all';
import { Observable } from 'rxjs';
import { BuyResponse } from '../models/BuyResponse.all';

@Injectable({
  providedIn: 'root'
})
export class BuyService {

  constructor(private httpClient: HttpClient) { }

  register(buyRequest: BuyRequest): Observable<BuyResponse>{
    return this.httpClient.post<BuyResponse>('http://localhost:8080/compras', buyRequest);
  }

  search(name: string): Observable<BuyResponse>{
    return this.httpClient.get<BuyResponse>(`http://localhost:8080/compras/${name}`);
  }

  get(nombreComprobante?: string, 
    fecha_inicio?: string, 
    fecha_fin?: string, 
    proveedor?: string,
    tipoPago?: string,
    totalMayoA?: number,
    totalMenorA?: number,
    dadaDeBaja?: boolean,
    numeroTelefono?: string,
    email ?: string
  ): Observable<BuyResponse[]>{

    let params = new HttpParams();
    if(nombreComprobante){
      params = params.set('nombreComprobante', nombreComprobante);
    }
    if(fecha_inicio){
      params = params.set('fecha_inicio', fecha_inicio);
    }
    if(fecha_fin){
      params = params.set('fecha_fin', fecha_fin);
    }
    if(proveedor){
      params = params.set('proveedor', proveedor);
    }
    if(tipoPago){
      params = params.set('tipoPago', tipoPago);
    }
    if(totalMayoA){
      params = params.set('totalMayoA', totalMayoA);
    }
    if(totalMenorA){
      params = params.set('totalMenorA', totalMenorA);
    }
    if(dadaDeBaja){
      params = params.set('dadaDeBaja', dadaDeBaja);
    }
    if(numeroTelefono){
      params = params.set('numeroTelefono', numeroTelefono);
    }
    if(email){
      params = params.set('email', email);
    }

    return this.httpClient.get<BuyResponse[]>('http://localhost:8080/compras', {params});
  }

  delete(name: string): Observable<BuyResponse>{
    return this.httpClient.delete<BuyResponse>(`http://localhost:8080/compras/${name}/darDeBaja`);
  }

  getTipoPago(): Observable<string[]>{
    return this.httpClient.get<string[]>('http://localhost:8080/compras/tipoPagos');
  }

  getTipoFactura(): Observable<string[]>{
    return this.httpClient.get<string[]>('http://localhost:8080/compras/tipoFacturas');
  }
}
