import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, map, throwError } from 'rxjs';
import { RegisterRequestDTO } from '../models/RegisterRequestDTO';
import { LoginRequestDTO } from '../models/LoginRequestDTO';
import { AuthResponseDTO } from '../models/AuthResponseDTO';
import { ToastrService } from 'ngx-toastr';
import { RecoverAccount } from '../models/RecoverAccount';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  urlBase = 'http://localhost:8080/user/';
  
  constructor(
    private http: HttpClient,
    private router: Router,
  ) { }

  // #region Métodos de Login y Logout
  login(userLoggin: LoginRequestDTO): Observable<AuthResponseDTO> {
    console.log('Datos recibidos por el servicio:', userLoggin); // Añade un log para verificar los datos

    return this.http.post<AuthResponseDTO>('http://localhost:8080/auth/login', userLoggin)
      .pipe(
        map((res: AuthResponseDTO) => {
          console.log('Respuesta del servidor:', res); // Añade un log para la respuesta
          try {
            if (res.status == 200) {      
              if(res.userRegister.role === "ROLE_ADMIN"){
                console.log('Usuario:', res.userRegister);
                localStorage.setItem('token', res.token);
                localStorage.setItem('expires', res.expiresIn);
                localStorage.setItem('user', JSON.stringify(res.userRegister));
                this.router.navigate(['/panel/car/table']);
                return res;
              } else if(res.userRegister.role == 'ROLE_USER'){
                console.log('Usuario:', res.userRegister);
                localStorage.setItem('token', res.token);
                localStorage.setItem('expires', res.expiresIn);
                localStorage.setItem('user', JSON.stringify(res.userRegister));
                this.router.navigate(['/']);
                return res;
              }else{
                throw new Error('Error en la respuesta del servidor');
              }

            } else {
              throw new Error('Error en la respuesta del servidor');
            }
          } catch (e) {
            console.log(e);
            throw e;
          }
        }),
        catchError((err) => {
          console.error('Error durante la autenticación:', err);
          return throwError(() => err);
        })
      );
}
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('expires');
    this.router.navigate(['/']);
  }

  isLogged(): boolean {
    return !!localStorage.getItem('token');
  }

  returnUser(): any {
    const userString = localStorage.getItem('user');
    if (userString !== null) {
      return JSON.parse(userString);
    } else {
      return null;
    }
  }

  isAdmin(): boolean {
    const userString = localStorage.getItem('user');
    if (userString !== null) {
        const user = JSON.parse(userString);
        return user.role === 'ROLE_ADMIN';
    } else {
        return false;
    }
  }

  isEmployee(): boolean {
    const userString = localStorage.getItem('user');
    if (userString !== null) {
        const user = JSON.parse(userString);
        return user.role === 'employee';
    } else {
        return false;
    }
  }

  isClient(): boolean {
    const userString = localStorage.getItem('user');
    if (userString !== null) {
        const user = JSON.parse(userString);
        return user.role === 'client';
    } else {
        return false;
    }
  }

  // #endregion

  // #region Métodos de Registro
  registerUser(user: RegisterRequestDTO): Observable<AuthResponseDTO> {
    return this.http.post<AuthResponseDTO>(`http://localhost:8080/auth/register`, user)
      .pipe(
        map((res: AuthResponseDTO) => {
          try {
            if (res.status == 200) {
              localStorage.setItem('token', res.token);
              localStorage.setItem('expires', res.expiresIn);
              localStorage.setItem('user', JSON.stringify(res.userRegister));
              this.router.navigate(['/']);
              return res;
            } else {
              throw new Error('Error en la respuesta del servidor');
            }
          } catch (e) {
            console.log(e);
            throw e;
          }
        }),
        catchError((err) => {
          console.error('Error durante el registro:', err);
          return throwError(() => err);
        })
      );
  }

  registerEmployee(user: RegisterRequestDTO): Observable<AuthResponseDTO> {
    return this.http.post<AuthResponseDTO>(`${this.urlBase}/register`, user) //cambiar la url
      .pipe(
        map((res: AuthResponseDTO) => {
          try {
            if (res.status == 200) {
              localStorage.setItem('token', res.token);
              localStorage.setItem('expires', res.expiresIn);
              localStorage.setItem('user', JSON.stringify(res.userRegister));
              this.router.navigate(['/panel']);
              return res;
            } else {
              throw new Error('Error en la respuesta del servidor');
            }
          } catch (e) {
            console.log(e);
            throw e;
          }
        }),
        catchError((err) => {
          console.error('Error durante el registro:', err);
          return throwError(() => err);
        })
      );
  }

  registerAdmin(user: RegisterRequestDTO): Observable<AuthResponseDTO> {
      
     
      return this.http.post<AuthResponseDTO>(`${this.urlBase}/register`, user) //cambiar la url
        .pipe(
          map((res: AuthResponseDTO) => {
            try {
              if (res.status == 200) {
                localStorage.setItem('token', res.token);
                localStorage.setItem('expires', res.expiresIn);
                localStorage.setItem('user', JSON.stringify(res.userRegister));
                this.router.navigate(['/panel']);
                return res;
              } else {
                throw new Error('Error en la respuesta del servidor');
              }
            } catch (e) {
              console.log(e);
              throw e;
            }
          }),
          catchError((err) => {
            console.error('Error durante el registro:', err);
            return throwError(() => err);
          })
        );
    }
  
    firstStepRecorverPassword(email: string): Observable<any> {
      let params = new HttpParams();
      params = params.set('email', email);
    
      // Aquí construyes la solicitud correctamente
      return this.http.post('http://localhost:8080/cambiar-contrasenia-primer-paso', null, { params: params })
        .pipe(
          catchError((err) => {
            console.error('Error durante el registro:', err);
            return throwError(() => err);
          })
        );
    }

    secondStepRecoverPassword(r: RecoverAccount): Observable<any> {
      return this.http.post('http://localhost:8080/cambiar-contrasenia-segundo-paso', r)
        .pipe(
          catchError((err) => {
            console.error('Error durante el registro:', err);
            return throwError(() => err);
          })
        );
    }

  // #endregion
}
