import { Component, ViewChild } from '@angular/core';
import { ServiceResponseDTO } from '../../models/service/Service.all';
import { ServiceService } from '../../services/service/service.service';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { RegisterServiceComponent } from '../register-service/register-service.component';

@Component({
  selector: 'app-table-service',
  templateUrl: './table-service.component.html',
  styleUrls: ['./table-service.component.css']
})
export class TableServiceComponent {

  servicios: ServiceResponseDTO[] = [];
  dataSource = new MatTableDataSource<ServiceResponseDTO>();
  totalItems = 0; // Total de elementos, para el paginador
  displayedColumns: string[] = ['nombre','descripcion', 'precio', 'tipo', 'activo', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;

  roles: any;
  suscription: Subscription = new Subscription();
  form!: FormGroup;
  
  //crear el array de tipo servicio clave valor 
  tipoServicio: any = [
    {value: 'Mantenimiento', viewValue: 'Mantenimiento'},
    {value: 'Reparación', viewValue: 'Reparación'},
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


  constructor(private _service: ServiceService, private _alerts: ToastrService, private _formBuilder: FormBuilder, private _dialog: MatDialog) { }

  ngOnInit(): void {
    this.suscription.add(
      this._service.obtenerTodosLosServicios().subscribe(data => {
        this.servicios = data;
        this.dataSource = new MatTableDataSource(this.servicios);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      })
    );
   
    this.form = this._formBuilder.group({
      nombre: [''],
      tipo: [''],
      precio: [''],
      activo: [undefined]
    });
  }

  ngOnDestroy(): void {
  }

    abrirModalRegistro() {
      const dialogRef = this._dialog.open(RegisterServiceComponent, {
        width: '600px',
        data: {
          message: 'Registrar',
          submessage: 'Ingrese los datos del servicio',
          service: {
            nombre: '',
            descripcion: '',
            precio: 0,
            tipo: ''
          }
        }
      });
      
      dialogRef.afterClosed().subscribe({
        next: (data) => {
          this.aplicarFiltros();
        }
      });
      
    }

    abrirModalRegistroEdicion(response: ServiceResponseDTO) {
      const dialogRef = this._dialog.open(RegisterServiceComponent, {
        width: '600px',
        data: {
          message: 'Editar',
          submessage: 'Ingrese los datos del servicio',
          service: response
        }
      });
      
      dialogRef.afterClosed().subscribe({
        next: (data) => {
          this.aplicarFiltros();
        }
      });

    }

    aplicarFiltros() {
      let nombre = this.form.get('nombre')?.value;
      let tipo = this.form.get('tipo')?.value;
      let precio = this.form.get('precio')?.value;
      let activo = this.form.get('activo')?.value;

      console.log(this.form.value);
      

      this.suscription.add(
        this._service.obtenerTodosLosServicios(nombre, tipo, precio, undefined, undefined, activo ).subscribe(data => {
          this.servicios = data;
          this.dataSource = new MatTableDataSource(this.servicios);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        })
      );
    }
}
