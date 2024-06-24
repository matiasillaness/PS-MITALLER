import { Component, OnDestroy, OnInit } from '@angular/core';
import {  FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { BrandCarResponseDTO } from '../../models/brandCarResponseDTO';
import { carRequestDTO } from '../../models/carRequestDTO';
import { BrandCarService } from '../../services/brand-car.service';
import { CarService } from '../../services/car.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrls: ['./update-car.component.css']
})
export class UpdateCarComponent implements OnDestroy, OnInit{

  private suscription: Subscription = new Subscription();
  tipoColor!: String[];
  tipoAutos!: String[];
  marcas!: BrandCarResponseDTO[];
  nombrePatente!: string;
  form!: FormGroup ;
  id: number = 0;
  carRequest: carRequestDTO = {
    patente: '',
    marca_id: 0,
    modelo: '',
    color: '',
    tipo_vehiculo: '',
    observaciones: '',
    kilometraje: 0,
    numero_chasis: 0
  };



  

  constructor(
    private _carService: CarService,
    private _formBuilder: FormBuilder,
    private _brandService: BrandCarService,
    private _alerts: ToastrService,
    private _router: ActivatedRoute
  ) { }


  editarAuto() {
    if (this.form.invalid) {
      this._alerts.error('Error', 'Formulario incompleto');
      return;
    }
    this.carRequest.patente = this.form.get('patente')?.value;
    this.carRequest.marca_id = this.form.get('marca_id')?.value;
    this.carRequest.modelo = this.form.get('modelo')?.value;
    this.carRequest.color = this.form.get('color')?.value;
    this.carRequest.tipo_vehiculo = this.form.get('tipo_vehiculo')?.value;
    this.carRequest.observaciones = this.form.get('observaciones')?.value;
    this.carRequest.kilometraje = this.form.get('kilometraje')?.value;
    this.carRequest.numero_chasis = this.form.get('numero_chasis')?.value;
    console.log('Auto:', this.carRequest);
    
    this.suscription.add(
      this._carService.editarAuto(this.id, this.carRequest).subscribe({
        next: (data) => {
          this._alerts.success('Exito', 'Auto editado correctamente');
          this.limpiarFormulario();
        },
        error: (err) => {
          this._alerts.error('Error', 'Error al editar el auto');
          console.error('Error al editar el auto:', err);
        }
      })
    );

  }
  limpiarFormulario() {
    this.form.reset();
  }


  
  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  ngOnInit(): void {
    this.form = this._formBuilder.group({
      patente: ['', [Validators.required]],
      marca_id: [0, [Validators.required]],
      modelo: ['', [Validators.required]],
      color: ['', [Validators.required]],
      tipo_vehiculo: ['', [Validators.required]],
      observaciones: ['', [Validators.required]],
      kilometraje: [null, [Validators.required]],
      numero_chasis: [null, [Validators.required]]
    });

    this._router.params.subscribe(params => {
      this.id = +params['id']; 
      if(this.id) {
        this.obtenerAuto(this.id);
      }
    });

    this._carService.obtenerColoresVehiculos().subscribe({
      next: (data) => {
        this.tipoColor = data;
      },
      error: (err) => {
        console.error('Error al obtener los colores de los vehiculos:', err);
      }
    });

    this._carService.obtenerTiposVehiculos().subscribe({
      next: (data) => {
        this.tipoAutos = data;
      },
      error: (err) => {
        console.error('Error al obtener los tipos de vehiculos:', err);
      }
    });

    this._brandService.obtenerMarcasParaAuto().subscribe({
      next: (data) => {
        this.marcas = data;
      },
      error: (err) => {
        console.error('Error al obtener las marcas de los vehiculos:', err);
      }
    });
    
  
  }



  obtenerAuto(id: number) {
    console.log('id:', id);
    this.suscription.add(
      this._carService.obtenerAuto(id).subscribe({
        next: (data) => {
            this.carRequest.color = data.color;
            this.carRequest.kilometraje = data.kilometraje;
            this.carRequest.modelo = data.modelo;
            this.carRequest.numero_chasis = data.numero_chasis;
            this.carRequest.observaciones = data.observaciones;
            this.carRequest.patente = data.patente;
            this.carRequest.tipo_vehiculo = data.tipo_vehiculo;
            

            this.form.setValue({
              patente: data.patente,
              marca_id: data.marca,
              modelo: data.modelo,
              color: data.color,
              tipo_vehiculo: data.tipo_vehiculo,
              observaciones: data.observaciones,
              kilometraje: data.kilometraje,
              numero_chasis: data.numero_chasis
            });

            console.log('Auto:', data);
        },
        error: (err) => {
          console.error('Error al obtener el auto:', err);
        }
      })
    );
  }


  buscar() {
    console.log('Patente:', this.nombrePatente);

    if (this.nombrePatente === '' || this.nombrePatente === undefined) {
      this._alerts.info('Patente vacía', 'Ingresa una patente para buscar.');
      return;
    }

    this._carService.obtenerAutos(
      undefined, 
      undefined,
      this.nombrePatente,
      undefined,
      undefined,
    ).subscribe({
      next: (data) => {
        console.log('Autos:', data);

        if (data.length === 0) {
          this._alerts.info('No se encontraron autos', 'No hay autos con la patente ingresada.');
          return;
        }

        this.carRequest.marca_id = 0;
        this.carRequest.modelo = data[0].modelo;
        this.carRequest.color = data[0].color;
        this.carRequest.tipo_vehiculo = data[0].tipo_vehiculo;
        this.carRequest.observaciones = data[0].observaciones;
        this.carRequest.kilometraje = data[0].kilometraje;
        this.carRequest.numero_chasis = data[0].numero_chasis;
        this.carRequest.patente = data[0].patente;

        

        this.form.setValue({
          patente: this.carRequest.patente,
          marca_id: this.carRequest.marca_id,
          modelo: this.carRequest.modelo,
          color: this.carRequest.color,
          tipo_vehiculo: this.carRequest.tipo_vehiculo,
          observaciones: this.carRequest.observaciones,
          kilometraje: this.carRequest.kilometraje,
          numero_chasis: this.carRequest.numero_chasis
        });

      },
      error: (err) => {
        console.error('Error al obtener los autos:', err);
      }
    });
  }
}
