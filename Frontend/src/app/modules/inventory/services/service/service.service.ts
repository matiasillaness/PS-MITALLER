import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceResponseDTO } from '../../models/service/Service.all';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private _httpClient: HttpClient) { }

  guardarServicio(servicio: ServiceResponseDTO): Observable<ServiceResponseDTO> {
    return this._httpClient.post<ServiceResponseDTO>('http://localhost:8080/servicios', servicio);
  }
  editarServicio(id: number, servicio: ServiceResponseDTO): Observable<ServiceResponseDTO> {
    return this._httpClient.put<ServiceResponseDTO>('http://localhost:8080/servicios/' + id, servicio);
  }
  eliminarServicio(id: number): Observable<Boolean> {
    return this._httpClient.delete<Boolean>('http://localhost:8080/servicios/' + id);
  }
  obtenerTodosLosServicios(nombre?: string, tipo?: string,precio?: number, size?: number, page?: number, activo?: boolean): Observable<ServiceResponseDTO[]> {
    let params = new HttpParams();
    if (nombre) params = params.set('nombre', nombre);
    if (tipo) params = params.set('tipo', tipo);
    if (precio) params = params.set('precio', precio.toString());
    if (size !== undefined && size !== null) params = params.set('size', size.toString())
      else params = params.set('size', '300');
    if (page !== undefined && page !== null) params = params.set('page', page.toString())
      else params = params.set('page', '1');

    if (activo != null)
    params = params.set('activo', activo);


    return this._httpClient.get<ServiceResponseDTO[]>('http://localhost:8080/servicios', { params: params });

  }
}
