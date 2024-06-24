import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable, catchError, throwError } from 'rxjs';
import { BrandCarResponseDTO } from '../models/brandCarResponseDTO';

@Injectable({
  providedIn: 'root'
})
export class BrandCarService {

  constructor(private _httpClient: HttpClient, private _notification: ToastrService) { }

  BASE_URL = 'http://localhost:8080/marcas';

  // #region Métodos de Put Post Delete
  crearMarca(brandCar: String): Observable<Boolean> {
    return this._httpClient.post<boolean>(`${this.BASE_URL}/${brandCar}`, {})
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al crear la marca.');
        })
      );
  }
 
  actualizarMarca(brandCar: String, IdBrand: number): Observable<Boolean> {
    return this._httpClient.put<boolean>(`${this.BASE_URL}/${IdBrand}/${brandCar}`, {})
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al actualizar la marca.');
        })
      );
  }


  activeOrDeactiveCarBrand(id: number, active: boolean): Observable<boolean> {

    let params = new HttpParams();
    if (active) params = params.set('activo', 'true');
    else params = params.set('activo', 'false');



    return this._httpClient.patch<boolean>(this.BASE_URL + "/activar-desactivar/" + id, { }, { params })
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al activar o desactivar la marca.');
        })
      )
  }


  eliminarMarca(id: number): Observable<boolean> {
    return this._httpClient.delete<boolean>(`${this.BASE_URL}/${id}`)
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al eliminar la marca.');
        })
      );
  }

  // #endregion

  // #region Métodos de Get
  obtenerMarcas(): Observable<BrandCarResponseDTO[]> {
    return this._httpClient.get<BrandCarResponseDTO[]>('http://localhost:8080/marcas')
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al obtener las marcas.');
        })
      );
  }

  
  obtenerMarcasParaAuto(): Observable<BrandCarResponseDTO[]> {

    const params = new HttpParams().set('activo', 'true');

    return this._httpClient.get<BrandCarResponseDTO[]>(this.BASE_URL, { params })
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al obtener las marcas.');
        })
      );
  }

  obtenerMarcaPorId(id: number): Observable<BrandCarResponseDTO> {
    return this._httpClient.get<BrandCarResponseDTO>(`${this.BASE_URL}/${id}`)
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al obtener la marca.');
        })
      );
  }

  // #endregion
}
