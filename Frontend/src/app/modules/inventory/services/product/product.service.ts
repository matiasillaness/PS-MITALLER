import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RepuestoRequestDTO, RepuestoResponseDTO } from '../../models/product/Repuesto.all';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private _httpClient: HttpClient) { }

  guardarRepuesto(repuesto: RepuestoRequestDTO): Observable<RepuestoRequestDTO> {
    return this._httpClient.post<any>('http://localhost:8080/repuestos', repuesto);
  }
  editarRepuesto(id: number, repuesto: RepuestoRequestDTO): Observable<RepuestoRequestDTO> {
    return this._httpClient.put<any>('http://localhost:8080/repuestos/' + id, repuesto);
  }
  eliminarRepuesto(id: number): Observable<Boolean> {
    return this._httpClient.delete<Boolean>('http://localhost:8080/repuestos/' + id);
  }
  obtenerTodosLosRepuestos(nombre?: string, marca?: string,precio?: number, activo?: boolean, size?: number, page?: number): Observable<RepuestoResponseDTO[]> {
   
    let params = new HttpParams();
    if (nombre) params = params.set('nombre', nombre);
    if (marca) params = params.set('marca', marca);
    if (precio) params = params.set('precio', precio);
    if (activo) params = params.set('activo', activo);
    if (size !== undefined && size !== null) params = params.set('size', size.toString())
      else params = params.set('size', '300');
    if (page !== undefined && page !== null) params = params.set('page', page.toString())
      else params = params.set('page', '1');
    return this._httpClient.get<RepuestoResponseDTO[]>('http://localhost:8080/repuestos', { params: params });
  }

  activarDesactivarRepuesto(id: number, activo: boolean): Observable<RepuestoResponseDTO> {
    let params = new HttpParams();
    params = params.set('id', id);
    if (activo) params = params.set('activo', 'true');
    else params = params.set('activo', 'false');
    return this._httpClient.patch<RepuestoResponseDTO>('http://localhost:8080/repuestos/activar-inactivar?id=' + id + '&activo=' + activo, { });
  }


  obtenerRepuestos(
    nombre?: string,
    marca?: string,
    precio?: number,
    page: number = 1,
    size: number = 100,
    activo?: boolean
  ) : Observable<RepuestoResponseDTO[]> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (nombre) {
      params = params.set('nombre', nombre);
    }
    if (marca) {
      params = params.set('marca', marca);
    }
    if (precio) {
      params = params.set('precio', precio);
    }
    if (activo !== undefined) {
      params = params.set('activo', activo);
    }

    return this._httpClient.get<RepuestoResponseDTO[]>('http://localhost:8080/repuestos', { params });
  }
}
