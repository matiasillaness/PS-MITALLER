import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { LoginRequestDTO } from '../../models/LoginRequestDTO';
import { AuthResponseDTO } from '../../models/AuthResponseDTO';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../../../../style.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  suscription: Subscription = new Subscription();
  userToSend!: LoginRequestDTO;
  AuthResponseDTO!: AuthResponseDTO;
  isLogged: String = 'EN CURSO'

  form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private toastr: ToastrService) { }

  ngOnInit(): void {

  }

  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  onSubmit() {

    console.log('Formulario:', this.form.value);
    if (this.form.valid) {
      this.userToSend = this.form.value;
      console.log('Datos a enviar:', this.userToSend); // Añade un log para verificar los datos
      this.authService.login(this.userToSend).subscribe({
        next: (res) => {
        
          this.toastr.success('Bienvenido', 'Inicio de sesión exitoso', {
            timeOut: 2000,
            closeButton: true,
            positionClass: 'toast-bottom-full-width',
          });

          this.AuthResponseDTO = res;
          this.isLogged = 'OK';
          console.log('Respuesta de autenticación:', res); // Añade un log para la respuesta
        },
        error: (err) => {
          this.isLogged = 'ERROR';
          console.error('Error en la autenticación:', err);
        }
      });
    }
  }
}
