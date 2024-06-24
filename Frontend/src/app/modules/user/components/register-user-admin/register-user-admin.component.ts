import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserRequestDTO, UserRequestEmployeeDTO } from '../../models/UserRequestDTO';
import { Toast, ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register-user-admin',
  templateUrl: './register-user-admin.component.html',
  styleUrls: ['./register-user-admin.component.css']
})
export class RegisterUserAdminComponent implements OnInit, OnDestroy{
  private subscription: Subscription = new Subscription();

  roles: any;

  private userRequestEdit: UserRequestDTO = {
    email: '',
    nombre: '',
    apellido: '',
    telefono: '',
    direccion: '',
    role: ''
  }

  private userEmployeeRequest: UserRequestEmployeeDTO = {
    email: '',
    nombre: '',
    apellido: '',
    telefono: '',
    direccion: '',
    password: ''
  }

  form!: FormGroup;

  constructor(
    private _userService: UserService, private dialogRef: MatDialogRef<RegisterUserAdminComponent>
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string, email: string, user: any}
    , private formBuilder: FormBuilder, private _alert: ToastrService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: [this.transformText(this.data.user.email), Validators.required],
      nombre: [this.transformText(this.data.user.nombre)],
      apellido: [ this.transformText(this.data.user.apellido)],
      telefono: [this.data.user.telefono],
      direccion: [this.transformText(this.data.user.direccion)],
      password: [this.transformText(this.data.user.role), Validators.required],
    });

    if (this.data.message === 'Editar Usuario') {
      this.form.patchValue({
        password: this.data.user.role // Asegúrate de que `this.data.user.role` contiene el valor del rol
      });
    }

    this.desactivarEmail();

    this.roles = [
      { value: 'ROLE_EMPLOYER', label: 'Empleado' },
      { value: 'ROLE_USER', label: 'Usuario' },
      { value: 'ROLE_ADMIN', label: 'Administrador' }
    ];
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  reset() {
    this.form.reset();
  }
  
  save() {
    if(this.data.message == 'Editar Usuario') {
      this.userRequestEdit.email = this.form.get('email')!.value;
      this.userRequestEdit.nombre = this.form.get('nombre')!.value;
      this.userRequestEdit.apellido = this.form.get('apellido')!.value;
      this.userRequestEdit.telefono = this.form.get('telefono')!.value;
      this.userRequestEdit.direccion = this.form.get('direccion')!.value;
      this.userRequestEdit.role = this.form.get('password')!.value;
      this.subscription.add(this._userService.editarUsuario(this.userRequestEdit).subscribe({
        next: (data) => {
         this._alert.success('Usuario editado correctamente', 'Usuario Editado');
          this.dialogRef.close();
        },
        error: (error) => {
          console.error('Error en la petición:', error);
        }
      }));
    } else {
      this.userEmployeeRequest.email = this.form.get('email')!.value;
      this.userEmployeeRequest.nombre = this.form.get('nombre')!.value;
      this.userEmployeeRequest.apellido = this.form.get('apellido')!.value;
      this.userEmployeeRequest.telefono = this.form.get('telefono')!.value;
      this.userEmployeeRequest.direccion = this.form.get('direccion')!.value;
      this.userEmployeeRequest.password = this.form.get('password')!.value;
      this.subscription.add(this._userService.crearUsuarioEmpleado(this.userEmployeeRequest).subscribe({
        next: (data) => {
          this._alert.success('Usuario creado correctamente', 'Usuario Creado');
          this.dialogRef.close();
        },
        error: (error) => {
          console.error('Error en la petición:', error);
        }
      }));
    }
  }

  desactivarEmail() {
    if (!this.form.get('email')!.touched) {
      const emailControl = this.form.get('email');
      if (this.data.email) {
        emailControl!.disable();
      } else {
        emailControl!.enable();
      }
    };
  }

    
  transformText(text: string): string {
    if (!text) return text;
  
    // Divide el texto en oraciones utilizando un punto seguido de un espacio como delimitador.
    const sentences = text.split('. ');
  
    // Transforma cada oración.
    const transformedSentences = sentences.map(sentence => {
      if (sentence.length > 0) {
        // Convierte la primera letra en mayúscula y las demás en minúsculas.
        sentence = sentence.charAt(0).toUpperCase() + sentence.slice(1).toLowerCase();
      }
      return sentence;
    });
  
    // Une las oraciones transformadas con '. ' para formar el texto final.
    return transformedSentences.join('. ');
  }

}
