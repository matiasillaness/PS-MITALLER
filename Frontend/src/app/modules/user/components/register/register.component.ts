import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { AuthResponseDTO } from '../../models/AuthResponseDTO';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterRequestDTO } from '../../models/RegisterRequestDTO';
import { Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { YesNoComponent } from 'src/app/modules/common/components/yes-no/yes-no.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css','../../../../style.css']
})
export class RegisterComponent implements OnInit, OnDestroy{ 
  authResponse!: AuthResponseDTO;
  registerRequestDTO !: RegisterRequestDTO;
  secondPassword!: string;
  suscription: Subscription = new Subscription();

 

  constructor(private _authService: AuthService,
     private _formBuilder: FormBuilder, 
     private toastr: ToastrService, 
    private _dialog: MatDialog) { }
  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  form = this._formBuilder.group({
    nombre: ['', [Validators.required]],
    password: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    apellido: ['', [Validators.required]],
    direccion: ['', [Validators.required]],
    phone: ['', [Validators.required]],
    secondPassword: ['', [Validators.required]]
  });

  ngOnInit(): void {
  }



  onSubmit() {

    const dialogRef = this._dialog.open(YesNoComponent, {
      data: {
        message: 'Antes de registrar, ¿acepta los términos y condiciones?',
        submessage: 'Al registrarse, <a href="/terms">acepta los términos y condiciones de uso de la plataforma</a>'
      }
    });
    
    dialogRef.afterClosed().subscribe((result) => {
      if(result){
        if (this.form.value.password !== this.form.value.secondPassword) {
          console.error('Las contraseñas no coinciden');
          alert('Las contraseñas no coinciden');
          return;
        }
    
    
        console.log('Formulario:', this.form.value);
        if (this.form.valid) {
       
          this.registerRequestDTO = this.form.value as RegisterRequestDTO;
    
          console.log('Datos a enviar:', this.registerRequestDTO); // Añade un log para verificar los datos
    
          this._authService.registerUser(this.registerRequestDTO).subscribe({
            next: (res) => {
              
              this.toastr.success('Bienvenido', 'Registro exitoso', {
                timeOut: 2000,
                closeButton: true,
                positionClass: 'toast-bottom-full-width',
              });
    
              this.authResponse = res;
              console.log('Respuesta de autenticación:', res); // Añade un log para la respuesta
            },
            error: (err) => {
              if (err.status === 409) {
                this.toastr.error('El correo ya se encuentra registrado', 'Error', {
                  timeOut: 2000,
                  closeButton: true,
                  positionClass: 'toast-center-center',
                });
              }
              console.error('Error en la autenticación:', err);
              
            }
          });
        }
       }
    });

   
  }

}
