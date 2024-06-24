import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BrandService } from '../../services/product/brand.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-table-brand',
  templateUrl: './table-brand.component.html',
  styleUrls: ['./table-brand.component.css']
})
export class TableBrandComponent implements OnInit, OnDestroy {

  form: FormGroup;

  constructor(private dialogRef: MatDialogRef<TableBrandComponent>
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string, id: number, name: string, active: boolean },
    private brand: BrandService, private formBuilder: FormBuilder
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


  createBrand() {
    if (this.form.invalid) {
      return;
    }

    const brandName = this.form.get('name')?.value;

    this.brand.addBrand(brandName).subscribe({
      next: (data: any) => {
        if (data === true) {
          this.dialogRef.close(true);
        } else {
          alert('Error al agregar la marca');
          this.dialogRef.close(false);
        }
      },
      error: (error: any) => {
        console.error('Error al agregar la marca', error);
        alert('Error al agregar la marca');
        this.dialogRef.close(false);
      }
    });
  }

  updateBrand() {
    if (this.form.invalid) {
      return;
    }

    const newName = this.form.get('name')?.value;

    this.brand.updateBrand(this.data.id, newName).subscribe({
      next: (data: any) => {
        if (data === true) {
          this.dialogRef.close(true);
        } else {
          alert('Error al actualizar la marca');
          this.dialogRef.close(false);
        }
      },
      error: (error: any) => {
        console.error('Error al actualizar la marca', error);
        alert('Error al actualizar la marca');
        this.dialogRef.close(false);
      }
    });
  }

  execute() {
    if (this.data.id) {
      this.updateBrand();
    } else {
      this.createBrand();
    }
  }

  close() {
    this.dialogRef.close(false);
  }

} 
