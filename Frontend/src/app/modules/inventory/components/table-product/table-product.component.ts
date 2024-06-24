import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { ToastrService } from 'ngx-toastr';
import { RepuestoResponseDTO } from '../../models/product/Repuesto.all';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { Subscription } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BrandCarResponseDTO } from 'src/app/modules/car/models/brandCarResponseDTO';
import { BrandCarService } from 'src/app/modules/car/services/brand-car.service';
import { MatDialog } from '@angular/material/dialog';
import { RegisterProductComponent } from '../register-product/register-product.component';
import { YesNoComponent } from 'src/app/modules/common/components/yes-no/yes-no.component';
import { BrandService } from '../../services/product/brand.service';

@Component({
  selector: 'app-table-product',
  templateUrl: './table-product.component.html',
  styleUrls: ['./table-product.component.css']
})
export class TableProductComponent implements OnInit, OnDestroy {

  marcas: BrandCarResponseDTO[] = [];


  repuestos: RepuestoResponseDTO[] = [];
  dataSource = new MatTableDataSource<RepuestoResponseDTO>();
  totalItems = 0; // Total de elementos, para el paginador
  displayedColumns: string[] = ['nombre','descripcion', 'precio', 'marca', 'stock','activo', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;
  form!: FormGroup;  
  roles: any;
  suscription: Subscription = new Subscription();



  constructor(
    private _productService: ProductService, 
    private _alerts: ToastrService,
    private _formBuilder: FormBuilder, 
    private _brandService: BrandService,
    private _dialog: MatDialog) { }

  ngOnInit(): void {
    this.suscription.add(
      this._productService.obtenerTodosLosRepuestos().subscribe(data => {
        this.repuestos = data;
        this.dataSource = new MatTableDataSource(this.repuestos);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      })
    );

    this.form = this._formBuilder.group({
      nombre: [''],
      descripcion: [''],
      precio: [''],
      marca: [''],
      stock: [''],
      activo: ['']
    });

    this.suscription.add(
      this._brandService.getBrands().subscribe(data => {
        data.forEach(element => {
          if(element.active == true)
            this.marcas.push(element); 
        } );
      })
    );
  }


  activarDesactivar(id: number, active: boolean) {
  
    let confirmationMessage = '';
    active = !active;

    if (active) {
      confirmationMessage = '¿Estás seguro de que deseas activar esta repuesto?';
    } else {
      confirmationMessage = '¿Estás seguro de que deseas desactivar esta repuesto?';
    }

    const dialogRef = this._dialog.open(YesNoComponent, {
      data: {
        message: confirmationMessage,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this._productService.activarDesactivarRepuesto(id, active).subscribe({
          next: (data) => {
            this._alerts.success('Repuesto actualizada con éxito');
            this.suscription.add(
              this._brandService.getBrands().subscribe(data => {
                this.marcas = data;
              })
            );
          },
          error: (error) => {
            
            this._alerts.error('Error al actualizar la marca');
          }
        });
      }
    });
  }

  ngOnDestroy(): void {
  }

  aplicarFiltros() {
    let nombre = this.form.get('nombre')?.value;  
    let precio = this.form.get('precio')?.value;
    let marca = this.form.get('marca')?.value;
    let activo = this.form.get('activo')?.value;

    console.log(nombre, precio, marca, activo);

    this._productService.obtenerRepuestos(nombre, marca, precio, 1, 100, activo)
      .subscribe(data => {
        this.repuestos = data;
        this.dataSource = new MatTableDataSource(this.repuestos);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
     
      this.form.reset();
  }

  abrirModalRegistro() {
    this._dialog.open(RegisterProductComponent, {
      width: '600px',
      data: {
        message: 'Registrar',
        submessage: 'Ingrese los datos del repuesto',
        repuesto: {
          nombre: '',
          descripcion: '',
          precio: 0,
          stock: 0,
          idMarca: 0
        }
      }
    }).afterClosed().subscribe(data => {
      this.suscription.add(
        this._productService.obtenerTodosLosRepuestos().subscribe(data => {
          this.repuestos = data;
          this.dataSource = new MatTableDataSource(this.repuestos);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        })
      );
      this._alerts.success('Repuesto registrado con éxito');
    });
  }

  abrirModalRegistroEdicion(response: RepuestoResponseDTO) {
    this._dialog.open(RegisterProductComponent, {
      width: '600px',
      data: {
        message: 'Editar',
        submessage: 'Ingrese los datos del repuesto',
        repuesto: response
      }
    }).afterClosed().subscribe(data => {
      this.suscription.add(
        this._productService.obtenerTodosLosRepuestos().subscribe(data => {
          this.repuestos = data;
          this.dataSource = new MatTableDataSource(this.repuestos);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        })
      );
    });
  }

}
