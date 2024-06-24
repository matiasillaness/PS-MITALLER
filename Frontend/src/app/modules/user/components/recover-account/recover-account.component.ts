import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { RecoverAccount } from '../../models/RecoverAccount';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-recover-account',
  templateUrl: './recover-account.component.html',
  styleUrls: ['./recover-account.component.css'],
  providers: [MatSnackBar],
})
export class RecoverAccountComponent implements OnInit, OnDestroy{
  suscription: Subscription = new Subscription();
  recoverAccount!: RecoverAccount;
  showSecondForm: boolean = false;
  formFirstStep!: FormGroup;
  formSecondStep!: FormGroup;

  loading = false; // Propiedad para controlar el estado de carga

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
   private _alerts: ToastrService
  ) { }
  
  
  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }
  ngOnInit(): void {
    this.formFirstStep = this.formBuilder.group({
      email: [''],
    });

    this.formSecondStep = this.formBuilder.group({
      code: [''],
      password: [''],
      confirmPassword: [''],
    });
  }

  submitSecond() {
    this.recoverAccount =  {
      email: this.email,
      code: this.formSecondStep.get('code')?.value,
      password: this.formSecondStep.get('password')?.value,
    }
    this.authService.secondStepRecoverPassword(this.recoverAccount).subscribe((data) => {
      if (data) {
        this._alerts.success('Contraseña cambiada con éxito', 'Cerrar', {
          
        });
      } else {
        this._alerts.success('No se ha podido cambiar la contraseña', 'Cerrar', {
          
        });
      }
    });
  }
  email: string = '';
  borrarFormMientrasCarga: boolean = false;
  submit() {
    this.email = this.formFirstStep.get('email')?.value;
    this.loading = true; // Activar el spinner al iniciar la solicitud
    this.borrarFormMientrasCarga = true;
    this.authService.firstStepRecorverPassword(
      this.formFirstStep.get('email')?.value

    ).subscribe((data) => {
      this.loading = false; // Desactivar el spinner al recibir la respuesta

      if (data) {

        this.showSecondForm = true;
        this._alerts.success('Código enviado al correo', 'Cerrar', {
          
        });
      } else {
        this._alerts.success('No se ha podido enviar el correo', 'Cerrar', {
        });
      }
    }, (error) => {
      this.loading = false; // Desactivar el spinner en caso de error
      console.error('Error en la solicitud:', error);
      this._alerts.success('Error en la solicitud', 'Cerrar', {
       
      });
    });
  }
}
