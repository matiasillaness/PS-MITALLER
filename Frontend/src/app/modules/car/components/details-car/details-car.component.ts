import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { CarResponseDTO } from '../../models/carResponseDTO';
import { CarService } from '../../services/car.service';
import { ModalBrandComponent } from '../modal-brand/modal-brand.component';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-details-car',
  templateUrl: './details-car.component.html',
  styleUrls: ['./details-car.component.css']
})
export class DetailsCarComponent implements OnInit, OnDestroy{

  suscription: Subscription = new Subscription();
  
  constructor(private dialogRef: MatDialogRef<DetailsCarComponent>, private _alerts: ToastrService
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string, id: number},
    private _carService: CarService,
  ){}


  car: CarResponseDTO = {
    idVehiculo: 0,
    patente: '',
    marca: '',
    modelo: '',
    color: '',
    tipo_vehiculo: '',
    observaciones: '',
    kilometraje: 0,
    numero_chasis: 0,
    activo: false
  
  };
  
  
  ngOnInit(): void {

    console.log("awdawdawdawd",this.data.id);

    this._carService.obtenerAuto(this.data.id).subscribe({
      next: (data) => {
        this.car = data;
      }
    });

  }
  ngOnDestroy(): void {
    if (this.suscription) {
      this.suscription.unsubscribe();
    }
  }

}
