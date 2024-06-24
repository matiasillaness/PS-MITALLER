import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SupplierRequestDTO } from '../models/SupplierRequestDTO';
import { SupplierResponseDTO } from '../models/SupplierResponseDTO';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {



  constructor(private _httpClient: HttpClient) { }

  obtenerTodosLosProveedores(
    nombre?: string,
    numeroDeTelefono?: string,
    email?: string,
    tipoProveedor?: string,
    size?: number,
    page?: number
  ): Observable<SupplierResponseDTO[]> {

    let params = new HttpParams();
    if (nombre) params = params.set('nombre', nombre);
    if (numeroDeTelefono) params = params.set('numeroDeTelefono', numeroDeTelefono);
    if (email) params = params.set('email', email);
    if (tipoProveedor) params = params.set('tipoProveedor', tipoProveedor);
    if (size !== undefined && size !== null) params = params.set('size', size.toString())
      else params = params.set('size', '300');
    if (page !== undefined && page !== null) params = params.set('page', page.toString())
      else params = params.set('page', '1');
    return this._httpClient.get<SupplierResponseDTO[]>('http://localhost:8080/proveedores', { params: params });
  }

  guardarProveedor(proveedor: SupplierRequestDTO): Observable<SupplierRequestDTO> {
    return this._httpClient.post<SupplierRequestDTO>('http://localhost:8080/proveedor', proveedor);
  }

  editarProveedor(id:number,proveedor: SupplierRequestDTO): Observable<boolean> {
    return this._httpClient.put<boolean>('http://localhost:8080/proveedor/' + id, proveedor);
  }

  eliminarProveedor(id: Number): Observable<Boolean> {
    return this._httpClient.delete<Boolean>('http://localhost:8080/proveedor/' + id);
  }

  //todo: completar el backend para dar de baja un proveedor
  bajaProveedor(id: number): Observable<SupplierRequestDTO> {
    return this._httpClient.put<SupplierRequestDTO>('http://localhost:8080/proveedores/baja/' + id, null);
  }
}
