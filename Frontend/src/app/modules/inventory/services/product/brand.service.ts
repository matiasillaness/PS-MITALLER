import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { BrandResponseDTO } from '../../models/product/Brand.all';

@Injectable({
  providedIn: 'root'
})
export class BrandService {

  constructor(private http: HttpClient) { 
  }

  options: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  });

  BASE_URL = 'http://localhost:8080/repuesto-marca';


  addBrand(name: string): Observable<boolean> {
    return this.http.post<boolean>(this.BASE_URL + "/crear-marca/" + name, {})
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al agregar la marca.');
        })
      );
  }
  
  getBrands(name?: string, id?: number, active?: boolean): Observable<BrandResponseDTO[]> {
   
    let params = new HttpParams();
    if (name) params = params.set('nombre', name.toString());
    if (id) params = params.set('id', id.toString());
    if (active) params = params.set('activo', active.toString());

    return this.http.get<BrandResponseDTO[]>(this.BASE_URL)
  }
  

  updateBrand(id: number, name: string): Observable<boolean> {
    return this.http.put<boolean>(this.BASE_URL + "/" + id + "/" + name, {}) //todo: dsp completar la jwttoken
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al actualizar la marca.');
        })
      );
  }

  deleteBrand(id: number): Observable<boolean> {
    return this.http.delete<boolean>(this.BASE_URL + "/" + id)
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error);
        })
      );
  }

  activeOrDeactiveBrand(id: number, active: boolean): Observable<boolean> {

    let params = new HttpParams();
    if (active) params = params.set('activo', 'true');
    else params = params.set('activo', 'false');



    return this.http.patch<boolean>(this.BASE_URL + "/activar-desactivar/" + id, { }, { params })
      .pipe(
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          return throwError(() => error || 'Ha ocurrido un error al activar o desactivar la marca.');
        })
      );
  }

}
