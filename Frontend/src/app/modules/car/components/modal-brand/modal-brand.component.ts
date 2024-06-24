import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TableBrandComponent } from 'src/app/modules/inventory/components/table-brand/table-brand.component';
import { BrandCarService } from '../../services/brand-car.service';
import { Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-modal-brand',
  templateUrl: './modal-brand.component.html',
  styleUrls: ['./modal-brand.component.css']
})
export class ModalBrandComponent implements OnInit, OnDestroy {

  close() {
    this.dialogRef.close();
  }

  execute() {

    this.save();
  }


  form: FormGroup;

  constructor(private dialogRef: MatDialogRef<ModalBrandComponent>, private _alerts: ToastrService
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string, id: number, name: string, active: boolean },
    private brand: BrandCarService, private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      name: new FormControl('', [Validators.required])
    });
  }

  subscription: Subscription = new Subscription();





  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  ngOnInit(): void {
    if (this.data.id) {
      this.form.get('name')?.setValue(this.data.name);
    }
  }

  save() {
    if (this.form.valid) {
      if (this.data.id) {
        this.subscription.add(this.brand.actualizarMarca(this.form.get('name')?.value, this.data.id).subscribe({
          next: (data) => {
            if (data) {
              this._alerts.success('Marca actualizada correctamente');
            }
            this.dialogRef.close(true);
    
          },
          error: (error) => {
            if (error.status == 500) {
              this._alerts.error('Error en el servidor');
            }
            if (error.status == 404) {
              this._alerts.error('Marca no encontrada');
            }
            if (error.status == 400) {
              this._alerts.error('Error en la solicitud porque la marca ya existe');
            }
            this.dialogRef.close();
          }
        }));
      } else {
        this.subscription.add(this.brand.crearMarca(this.form.get('name')?.value).subscribe({
          next: (data) => {
            if (data) {
              this._alerts.success('Marca creada correctamente');
            }
            this.dialogRef.close(true);
          },
          error: (error) => {
            if (error.status == 500) {
              this._alerts.error('Error en el servidor');
            }
            if (error.status == 400) {
              this._alerts.error('Error en la solicitud porque la marca ya existe');
            }
            this.dialogRef.close();
          }
        }));
      }
    }
  }


}
