import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CarResponseDTO } from '../models/carResponseDTO';
import { carRequestDTO } from '../models/carRequestDTO';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  
  

  

  constructor(private _httpClient: HttpClient) { }

  BASE_URL = 'http://localhost:8080/vehiculos';

  obtenerTiposVehiculos(): Observable<String[]> {
    return this._httpClient.get<String[]>(this.BASE_URL + '/tipos')
  }

  obtenerColoresVehiculos(): Observable<String[]> {
    return this._httpClient.get<String[]>(this.BASE_URL + '/colores')
  }

  obtenerAutos(
    modelo?: string, 
    tipoVehiculo?: string, 
    patente?: string,
    activo?: boolean,
    nombreMarca?: string,
    size?: number, 
    page?: number
  ): Observable<CarResponseDTO[]> {
  
    let params = new HttpParams();
    if (modelo) params = params.set('modelo', modelo);
    if (tipoVehiculo) params = params.set('tipoVehiculo', tipoVehiculo);
    if (patente) params = params.set('patente', patente);
    if (activo !== undefined && activo !== null) params = params.set('activo', activo.toString());
    if (nombreMarca) params = params.set('nombreMarca', nombreMarca);
    if (size !== undefined && size !== null) params = params.set('size', size.toString());
    if (page !== undefined && page !== null) params = params.set('page', page.toString());

    if (size === undefined || size === null) params = params.set('size', '300');
    if (page === undefined || page === null) params = params.set('page', '1');
  
    return this._httpClient.get<CarResponseDTO[]>(this.BASE_URL, { params: params });
  }

  obtenerAuto(id: number): Observable<CarResponseDTO> {
    return this._httpClient.get<CarResponseDTO>(this.BASE_URL + '/' + id);
  }

  obtenerAutoPorPatente(patente: string): Observable<CarResponseDTO> {
    return this._httpClient.get<CarResponseDTO>('http://localhost:8080/vehiculos/patente/' + patente);
  }

  crearAuto(carRequest: carRequestDTO): Observable<Boolean> {
    return this._httpClient.post<boolean>(this.BASE_URL, carRequest);
  }

  activarDesactivarAuto(id: number, activo: Boolean): Observable<Boolean> {
    let params = new HttpParams();
    if (activo) params = params.set('activo', 'true');
    else params = params.set('activo', 'false');

    return this._httpClient.patch<boolean>(this.BASE_URL + '/activar-desactivar/' + id, {}, { params });
  }

  editarAuto(id: number, carRequest: carRequestDTO): Observable<Boolean> {
    return this._httpClient.put<boolean>(this.BASE_URL + '/' + id, carRequest);
  }
}
