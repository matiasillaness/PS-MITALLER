import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { ServiceService } from '../../services/service/service.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ServiceResponseDTO } from '../../models/service/Service.all';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-register-service',
  templateUrl: './register-service.component.html',
  styleUrls: ['./register-service.component.css']
})
export class RegisterServiceComponent implements OnInit, OnDestroy{
  reset() {
   this.form.reset();
  }
  save() {
    if (this.form.valid) {
      if (this.data.message == 'Editar') {
        this._service.editarServicio(this.data.service.idServicio, this.form.value).subscribe(data => {
          this._alert.success('Servicio editado con éxito');
          this.dialogRef.close(true);
        }, error => {
          this._alert.error('Error al editar el servicio');
        });
      
      } else {
        console.log(this.form.value);
        this._service.guardarServicio(this.form.value).subscribe(data => {
          this._alert.success('Servicio registrado con éxito');
          this.dialogRef.close(true);
        }, error => {
          this._alert.error('Error al registrar el servicio');
        });
      }
    }
  }

  suscription: Subscription = new Subscription();
  form!: FormGroup;
    //crear el array de tipo servicio clave valor 
    tipoServicio: any = [
      {value: 'MANTENIMIENTO', viewValue: 'Mantenimiento'},
      {value: 'REPARACION', viewValue: 'Reparación'},
      {value: 'INSTALACION', viewValue: 'Instalación'},
      {value: 'PINTURA', viewValue: 'Pintura'},
      {value: 'CAMBIO_DE_PIEZAS', viewValue: 'Cambio de Piezas'},
      {value: 'LAVADO', viewValue: 'Lavado'},
      {value: 'ALINEACION', viewValue: 'Alineación'},
      {value: 'BALANCEO', viewValue: 'Balanceo'},
      {value: 'CAMBIO_DE_ACEITE', viewValue: 'Cambio de Aceite'},
      {value: 'CAMBIO_DE_FILTROS', viewValue: 'Cambio de Filtros'},
      {value: 'OTROS', viewValue: 'Otros'},
    ];
  
  
  constructor(
    private _service: ServiceService, private dialogRef: MatDialogRef<RegisterServiceComponent>
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string, email: string, service: ServiceResponseDTO}
    , private formBuilder: FormBuilder, private _alert: ToastrService
  ) { }


  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      nombre: [this.data.service.nombre ],
      descripcion: [this.data.service.descripcion],
      precio: [this.data.service.precio],
      tipo: [this.data.service.tipo],
    });
  }
  


}
