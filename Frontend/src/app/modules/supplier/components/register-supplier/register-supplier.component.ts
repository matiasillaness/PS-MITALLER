import { Component, Inject, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { SupplierService } from '../../services/supplier.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RegisterUserAdminComponent } from 'src/app/modules/user/components/register-user-admin/register-user-admin.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { SupplierResponseDTO } from '../../models/SupplierResponseDTO';

@Component({
  selector: 'app-register-supplier',
  templateUrl: './register-supplier.component.html',
  styleUrls: ['./register-supplier.component.css']
})
export class RegisterSupplierComponent implements OnInit, OnChanges{


  form!: FormGroup;
  tipoProveedor: String[] = ['MAYORISTA', 'MINORISTA'];

  

  constructor(
    private _supplierService: SupplierService, private dialogRef: MatDialogRef<RegisterUserAdminComponent>
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string, email: string, supplier: SupplierResponseDTO}
    , private formBuilder: FormBuilder, private _alert: ToastrService
  ) { }
  
  
  ngOnChanges(changes: SimpleChanges): void {
   
  }
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      nombre: [this.data.supplier.nombre ],
      telefono: [this.data.supplier.telefono],
      direccion: [this.data.supplier.direccion],
      email: [this.data.supplier.email],
      descripcion: [this.data.supplier.descripcion],
      tipoProveedor: [this.data.supplier.tipoProveedor]
    });

    if (this.data.message === 'Editar') {
      this.form.patchValue({
        nombre: this.data.supplier.nombre,
        telefono: this.data.supplier.telefono,
        direccion: this.data.supplier.direccion,
        email: this.data.supplier.email,
        descripcion: this.data.supplier.descripcion,
        tipoProveedor: this.data.supplier.tipoProveedor
      });
    }
  }


  reset() {
    this.form.reset();
    }
    save() {
      if (this.form.valid) {
        if (this.data.message == 'Editar') {
          this._supplierService.editarProveedor(this.data.supplier.idProveedor, this.form.value).subscribe(data => {
            if (data) {
              this._alert.success('Proveedor editado correctamente');
              this.dialogRef.close();
            } else {
              this._alert.error('Error al editar el proveedor');
            }
          }, error => {
            this._alert.error('Error al editar el proveedor');
          });
        } else if (this.data.message === 'Registrar Proveedor') {
          this._supplierService.guardarProveedor(this.form.value).subscribe(data => {
            if (data) {
              this._alert.success('Proveedor creado correctamente');
              this.dialogRef.close();
            } else {
              this._alert.error('Error al crear el proveedor');
            }
          }, error => {
            this._alert.error('Error al crear el proveedor');
          });
        }
      } else {
        this._alert.error('Complete los campos requeridos');
      }
    }
 
}
