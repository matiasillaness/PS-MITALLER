import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { CarService } from '../../services/car.service';
import { Subscription } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { carRequestDTO } from '../../models/carRequestDTO';
import { BrandCarResponseDTO } from '../../models/brandCarResponseDTO';
import { BrandCarService } from '../../services/brand-car.service';
import { ToastrService } from 'ngx-toastr';
import { PatenteValidator } from 'src/app/core/validators/patente-validator';


@Component({
  selector: 'app-register-car',
  templateUrl: './register-car.component.html',
  styleUrls: ['./register-car.component.css']
})
export class RegisterCarComponent implements OnInit, OnDestroy {

  private suscription: Subscription = new Subscription();
  tipoColor!: String[];
  tipoAutos!: String[];
  marcas!: BrandCarResponseDTO[];
  form!: FormGroup;
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
    private _alerts: ToastrService
  ) { }


  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  ngOnInit(): void {

    this.form = this._formBuilder.group({
      patente: ['', [Validators.required,PatenteValidator.validate()]],
      marca_id: [null, [Validators.required]],
      modelo: ['', [Validators.required]],
      color: ['', [Validators.required]],
      tipo_vehiculo: ['', [Validators.required]],
      observaciones: ['', [Validators.required]],
      kilometraje: [null, [Validators.required]],
      numero_chasis: [null, [Validators.required]]
    });

    this._carService.obtenerColoresVehiculos().subscribe({
      next: (data) => {
        this.tipoColor = data;
      },
      error: (error) => {
        console.error('Error en la petición:', error);
      }
    });

    this._carService.obtenerTiposVehiculos().subscribe({
      next: (data) => {
        this.tipoAutos = data;
      },
      error: (error) => {
        console.error('Error en la petición:', error);
      }
    });

    this._brandService.obtenerMarcas().subscribe({
      next: (data) => {
        this.marcas = data;
      },
      error: (error) => {
        console.error('Error en la petición:', error);
      }
    });
  }

  guardarAuto() {

    if (!this.form.valid) {
      console.error('Formulario inválido');
      this._alerts.error('Formulario inválido. Completa los datos correctamente.');
      return;
    }
    this.carRequest = this.form.value;
    this._carService.crearAuto(this.carRequest).subscribe({
      next: (data) => {
        console.log('Auto creado:', data);
        this._alerts.success('Auto creado correctamente.');
      },
      error: (error) => {
        console.error('Error en la petición:', error);
        this._alerts.error('Ha ocurrido un error al crear el auto.');
      }
    });
  }


  limpiarFormulario() {
    this.form.reset();
  }

}
