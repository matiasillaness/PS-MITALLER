import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { RepuestoRequestDTO, RepuestoResponseDTO } from '../../models/product/Repuesto.all';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BrandCarResponseDTO } from 'src/app/modules/car/models/brandCarResponseDTO';
import { Subscription } from 'rxjs';
import { BrandCarService } from 'src/app/modules/car/services/brand-car.service';
import { BrandService } from '../../services/product/brand.service';

@Component({
  selector: 'app-register-product',
  templateUrl: './register-product.component.html',
  styleUrls: ['./register-product.component.css']
})
export class RegisterProductComponent implements OnInit, OnDestroy{

  repuesto!: RepuestoRequestDTO;
  suscription: Subscription = new Subscription();
  form!: FormGroup;
  marcas!: BrandCarResponseDTO[];
  constructor(
    private _service: ProductService, private dialogRef: MatDialogRef<RegisterProductComponent>
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string, email: string, repuesto: RepuestoResponseDTO}
    , private formBuilder: FormBuilder, private _alert: ToastrService, private _brandService: BrandService
  ) { 
    this.repuesto = {
      descripcion: '',
      nombre: '',
      precio: 0,
      stock: 0,
      idMarca: null
    };
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      nombre: [''],
      descripcion: [''],
      precio: [''],
      stock: [''],
      idMarca: ['']
    });

    this.suscription.add(
      this._brandService.getBrands().subscribe(data => {
        this.marcas = data;
      })
    );

    if(this.data.message == 'Editar'){
      this.form.get('nombre')?.setValue(this.data.repuesto.nombre);
      this.form.get('descripcion')?.setValue(this.data.repuesto.descripcion);
      this.form.get('precio')?.setValue(this.data.repuesto.precio);
      this.form.get('stock')?.setValue(this.data.repuesto.stock);
      this.form.get('idMarca')?.setValue(this.data.repuesto.idRepuesto);
    }

  }
  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  reset() {
    this.form.reset();
    this.dialogRef.close(false);
    }
    save() {

      if (!this.repuesto) {
        this.repuesto = {
          descripcion: '',
          nombre: '',
          precio: 0,
          stock: 0,
          idMarca: null
        };
      }

      this.repuesto.descripcion = this.form.get('descripcion')?.value;
      this.repuesto.nombre = this.form.get('nombre')?.value;
      this.repuesto.precio = this.form.get('precio')?.value;
      this.repuesto.stock = this.form.get('stock')?.value;
      this.repuesto.idMarca = this.form.get('idMarca')?.value;

      if(this.data.message == 'Registrar'){
        this._service.guardarRepuesto(this.repuesto).subscribe(data => {
          this._alert.success('Repuesto registrado con éxito');
          this.dialogRef.close(true);
        }, error => {
          this._alert.error('Error al registrar el repuesto');
        });
      } else {
        this._service.editarRepuesto(this.data.repuesto.idRepuesto, this.repuesto).subscribe(data => {
          this.dialogRef.close(true);
        }, error => {
          this._alert.error('Error al editar el repuesto');
        });
      }
    
    }

}
