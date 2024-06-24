import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription, catchError, of } from 'rxjs';
import { UserResponseDTO } from 'src/app/modules/user/models/UserResponseDTO';
import { SupplierResponseDTO } from '../../models/SupplierResponseDTO';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SupplierService } from '../../services/supplier.service';
import { MatDialog } from '@angular/material/dialog';
import { RegisterSupplierComponent } from '../register-supplier/register-supplier.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-table-supplier',
  templateUrl: './table-supplier.component.html',
  styleUrls: ['./table-supplier.component.css']
})
export class TableSupplierComponent implements OnInit, OnDestroy {

  tipoProveedor: String[] = ['MAYORISTA', 'MINORISTA', 'AMBOS'];
  suscription: Subscription = new Subscription();
  suppliers: SupplierResponseDTO[] = [];
  dataSource = new MatTableDataSource<SupplierResponseDTO>();
  totalItems = 0; // Total de elementos, para el paginador
  displayedColumns: string[] = ['nombre', 'telefono', 'direccion', 'email', 'descripcion', 'tipoProveedor', 'activo', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;
  roles: any;
  form!: FormGroup<any>;


  constructor(private _supplierService: SupplierService, private _formBuilder: FormBuilder, private _dialog: MatDialog, private _alerts: ToastrService) { }




  ngOnInit(): void {
    this.suscription.add(
      this._supplierService.obtenerTodosLosProveedores().subscribe(data => {
        this.suppliers = data;
        this.dataSource = new MatTableDataSource(this.suppliers);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      })
    );

    this.form = this._formBuilder.group({
      nombre: [''],
      numeroDeTelefono: [''],
      email: [''],
      tipoProveedor: [''],
      telefono: [''],
      activo: []
    });

  }
  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  abrirModalEditarSupplier(supplier: SupplierResponseDTO) {
    this._dialog.open(RegisterSupplierComponent, {
      data: {
        message: 'Editar',
        submessage: 'Seleccione la opción que desee',
        email: '',
        supplier: supplier
      }
    }).afterClosed().subscribe(() => {
      this.aplicarFiltros();
    }
    );
  }


  activarODesactivar(activo: Boolean) {


  }

  abrirModalRegistro() {
    this._dialog.open(RegisterSupplierComponent, {
      data: {
        message: 'Registrar Proveedor',
        submessage: 'Complete los campos para registrar un proveedor',
        email: '',
        supplier: {
          idProveedor: 0,
          nombre: '',
          telefono: '',
          direccion: '',
          email: '',
          descripcion: '',
          tipoProveedor: ''

        }
      }
    }).afterClosed().subscribe(() => {
      this.aplicarFiltros();
    });
  }
  aplicarFiltros() {
    this.suscription.add(
      this._supplierService.obtenerTodosLosProveedores(
        this.form.get('nombre')?.value,
        this.form.get('numeroDeTelefono')?.value,
        this.form.get('email')?.value,
        this.form.get('tipoProveedor')?.value,
        this.form.get('telefono')?.value,
        this.form.get('activo')?.value
      ).subscribe(data => {
        this.suppliers = data;
        this.dataSource = new MatTableDataSource(this.suppliers);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      })
    );
  }

  eliminarSupplier(id: Number) {
    //hacer un next catch
    this.suscription.add(
      this._supplierService.eliminarProveedor(id).pipe(
        catchError(error => {
          this._alerts.error('Error al eliminar el proveedor');
          // Manejar el error aquí, por ejemplo, devolver un observable vacío
          return of(null);
        })
      ).subscribe(data => {
        if (data) {
          this._alerts.success('Proveedor eliminado correctamente');
          this.aplicarFiltros();
        }
      })
    );
  }
}
