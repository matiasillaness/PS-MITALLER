import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { YesNoComponent } from 'src/app/modules/common/components/yes-no/yes-no.component';
import { UserDTO } from '../../models/UserDTO';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-modal-user-client',
  templateUrl: './modal-user-client.component.html',
  styleUrls: ['./modal-user-client.component.css']
})
export class ModalUserClientComponent implements OnInit{

  form!: FormGroup;
  userDto: UserDTO = {
    email: '',
    firstname: '',
    lastname: '',
    telefono: '',
    direccion: '',
    id: 0,
    role: ''
  }


  constructor(private dialogRef: MatDialogRef<YesNoComponent>
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string}
    , private formBuilder: FormBuilder, private alerts : ToastrService, private _serviceUser: UserService
  ) { }



  ngOnInit(): void {
    this.obtenerUsuarioEnSesion();
    this.form = this.formBuilder.group({
      nombre: [this.userDto.firstname],
      apellido: [this.userDto.lastname],
      email: [this.userDto.email],
      direccion: [this.userDto.direccion],
      phone: [this.userDto.telefono]
    });
    this.desactivarInputEmail();
  }

  editar(){
    this.alerts.warning("no implementado")  
  }

  obtenerUsuarioEnSesion(){
    console.log('Obtener usuario en sesión');
    this.userDto = JSON.parse(localStorage.getItem('user')!);
    console.log('Usuario en sesión:', this.userDto);
  }

  eliminar() {
    this._serviceUser.borrarCuentaUsuario(this.userDto.email).subscribe(data => {
      this.alerts.success('Cuenta eliminada con éxito');
      localStorage.removeItem('user');
      localStorage.removeItem('token');
      this.dialogRef.close();
      setTimeout(()=> (window as any).dispatchEvent(new Event('resize')), 1)

    });

    setTimeout(()=> (window as any).dispatchEvent(new Event('resize')), 1)
  }

    desactivarInputEmail(){
      this.form.controls['email'].disable();
    }

   
}
