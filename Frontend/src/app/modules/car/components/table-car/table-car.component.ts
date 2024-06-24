import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { CarService } from '../../services/car.service';
import { ToastrService } from 'ngx-toastr';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { BrandCarResponseDTO } from '../../models/brandCarResponseDTO';
import { CarResponseDTO } from '../../models/carResponseDTO';
import { BrandCarService } from '../../services/brand-car.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { carRequestDTO } from '../../models/carRequestDTO';
import { NumberInput } from '@angular/cdk/coercion';
import { MatDialog } from '@angular/material/dialog';
import { DetailsCarComponent } from '../details-car/details-car.component';
import { Router } from '@angular/router';
import { YesNoComponent } from 'src/app/modules/common/components/yes-no/yes-no.component';

@Component({
  selector: 'app-table-car',
  templateUrl: './table-car.component.html',
  styleUrls: ['./table-car.component.css']
})
export class TableCarComponent implements OnInit, OnDestroy, AfterViewInit {

  tipoAutos!: String[];
  tipoColor!: String[];
  marcas!: BrandCarResponseDTO[];
  form!: FormGroup;

  dataSource = new MatTableDataSource<CarResponseDTO>();
  brands: BrandCarResponseDTO[] = [];
  totalItems = 0; // Total de elementos, para el paginador
  cars: CarResponseDTO[] = []; // Arreglo para acumular los datos
  displayedColumns: string[] = ['patente', 'marca', 'modelo', 'color', 'tipo_vehiculo','kilometraje','numero_chasis', 'observaciones', 'active', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;


  private suscription: Subscription = new Subscription();

  constructor(
    private _carService: CarService,
    private _alerts: ToastrService,
    private _brandService: BrandCarService,
    private _formBuilder: FormBuilder,
    private _dialog: MatDialog,
    private _router: Router
  ) { }





  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  ngOnInit(): void {
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
    this._brandService.obtenerMarcasParaAuto().subscribe({
      next: (data) => {
        this.marcas = data;
      },
      error: (error) => {
        console.error('Error en la petición:', error);
      }
    });

    this.form = this._formBuilder.group({
      patente: '',
      marca: '',
      modelo: '',
      color: '',
      tipoVehiculo: '',
      observaciones: '',
      activo: null
    });

    this.obtenerAutos();

  }


  

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  
  

  
  obtenerAutos(
    modelo?: string, 
    tipoVehiculo?: string, 
    patente?: string,
    activo?: boolean,
    nombreMarca?: string,
    size?: number, 
    page?: number
  ) {
    this._carService.obtenerAutos(
      modelo, 
      tipoVehiculo, 
      patente,
      activo,
      nombreMarca,
      size, 
      page
    ).subscribe({
      next: (data) => {
        // Agrega los nuevos autos al arreglo existente
        if (this.cars) {
          this.cars.push(...data);
        } else {
          this.cars = data;
        }
        this.dataSource.data = this.cars;
        console.log(this.cars);
      },
      error: (error) => {
        console.error('Error en la petición:', error);
      }
    });
  }

  indexPorLosCualesYaDesplaze: number[] = [];
  cambioDePagina($event: PageEvent) {
    this.indexPorLosCualesYaDesplaze.push($event.pageIndex);
    
    if (this.indexPorLosCualesYaDesplaze.includes($event.pageIndex)) {
      return;
    }

    this.obtenerAutos(undefined, undefined, undefined, undefined, undefined, $event.pageSize, $event.pageIndex + 1);
  }

  aplicarFiltros() {
    this.cars = [];
    this.obtenerAutos(
      this.form.get('modelo')?.value,
      this.form.get('tipoVehiculo')?.value,
      this.form.get('patente')?.value,
      this.form.get('activo')?.value,
      this.form.get('marca')?.value
    );

  }

  abrirDetalle(idVehiculo: number) {

    this._dialog.open(DetailsCarComponent, {
      width: '800px',
      height: '800px',
      data: {
        id: idVehiculo,
        message: 'Detalle del auto',
        submessage: 'Detalle'
      }
    }).afterClosed().subscribe((result) => {
     
    });
  }

  activarDesactivarAuto(id: number, activo: Boolean) {
    let confirmationMessage = '';
    activo = !activo;

    if (activo) {
      confirmationMessage = '¿Estás seguro de que deseas activar este auto?';
    } else {
      confirmationMessage = '¿Estás seguro de que deseas desactivar este auto?';
    }

    const dialogRef = this._dialog.open(YesNoComponent, {
      data: {
        message: confirmationMessage,
      }
    });
    
    this._carService.activarDesactivarAuto(id, activo).subscribe({
      next: (data) => {
        console.log('Auto actualizado:', data);
        this._alerts.success('Auto actualizado correctamente.');
      },
      error: (error) => {
        console.error('Error en la petición:', error);
        this._alerts.error('Ha ocurrido un error al actualizar el auto.');
      }
    });
  }

  irHaciaEditComponent(id: number) {
    this._router.navigate(['/panel/car/update-car', id]);
  }
  
}


