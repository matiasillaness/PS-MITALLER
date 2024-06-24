import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserResponseDTO } from '../models/UserResponseDTO';
import { UserRequestDTO, UserRequestEmployeeDTO } from '../models/UserRequestDTO';
import { AuthResponseDTO } from '../models/AuthResponseDTO';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _httpClient: HttpClient) { }

  BASE_URL = 'http://localhost:8080/';

   
  obtenerUsuarios( email?: string, 
    nombre?: string, 
    apellido?: string,
    activo?: boolean,
    rol?: string,
    telefono?: string, 
    size?: number,
    page?: number): Observable<UserResponseDTO[]> {

    let params = new HttpParams();
    if (email) params = params.set('email', email);
    if (nombre) params = params.set('nombre', nombre);
    if (apellido) params = params.set('apellido', apellido);
    if (activo !== undefined && activo !== null) params = params.set('activo', activo.toString());
    if (rol) params = params.set('rol', rol);
    if (telefono) params = params.set('telefono', telefono);
    if (size !== undefined && size !== null) params = params.set('size', size.toString())
      else params = params.set('size', '300');
    if (page !== undefined && page !== null) params = params.set('page', page.toString())
      else params = params.set('page', '1');

    return this._httpClient.get<UserResponseDTO[]>(this.BASE_URL + 'obtener-todos-los-usuarios', { params: params });

    }

    activarDesactivarUsuario(email: string, activo: boolean): Observable<UserResponseDTO> {

      let params = new HttpParams();
      params = params.set('email', email);

      if (activo) {
        return this._httpClient.put<UserResponseDTO>('http://localhost:8080/dar-de-baja', null, { params: params });
      } else{
        return this._httpClient.put<UserResponseDTO>('http://localhost:8080/dar-de-alta',null, { params: params });
      }
    }

    borrarCuentaUsuario(email: string): Observable<Boolean> {
      let params = new HttpParams();
      params = params.set('email', email);
      return this._httpClient.delete<Boolean>('http://localhost:8080/borrar-cuenta', { params: params });
    }

    crearUsuarioEmpleado(userRequest: UserRequestEmployeeDTO): Observable<AuthResponseDTO> {
      return this._httpClient.post<AuthResponseDTO>(this.BASE_URL + 'auth/register-employer', userRequest);
    }

    editarUsuario(userRequest: UserRequestDTO): Observable<AuthResponseDTO> {
      return this._httpClient.put<AuthResponseDTO>(this.BASE_URL + 'actualizar-usuario-para-admins', userRequest);
    }
  
}
