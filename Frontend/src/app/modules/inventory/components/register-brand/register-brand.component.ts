import { LiveAnnouncer } from '@angular/cdk/a11y';
import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { BrandService } from '../../services/product/brand.service';
import { BrandResponseDTO } from '../../models/product/Brand.all';
import { MatDialog } from '@angular/material/dialog';
import { LoginComponent } from 'src/app/modules/user/components/login/login.component';
import { YesNoComponent } from 'src/app/modules/common/components/yes-no/yes-no.component';
import { TableBrandComponent } from '../table-brand/table-brand.component';
import { Subscription, catchError, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register-brand',
  templateUrl: './register-brand.component.html',
  styleUrls: ['./register-brand.component.css']
})
export class RegisterBrandComponent implements AfterViewInit, OnInit, OnDestroy {


  dataSource = new MatTableDataSource<BrandResponseDTO>();
  brands: BrandResponseDTO[] = [];
  displayedColumns: string[] = ['id_brand', 'name', 'active', 'actions'];
  @ViewChild(MatPaginator) paginatior !: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;
  suscrition: Subscription = new Subscription();

  constructor(private brand: BrandService, private dialog: MatDialog, private _alerts: ToastrService) { }
  ngOnDestroy(): void {
    if (this.suscrition) {
      this.suscrition.unsubscribe();
    }
  }

  ngOnInit(): void {
    this.getBrands();
  }

  ngAfterViewInit(): void {
    this.getBrands();
  }


  getBrands() {
    this.brand.getBrands().subscribe((data) => {
      data.sort((a, b) => a.id_brand - b.id_brand);
      this.brands = data;
      this.dataSource = new MatTableDataSource(this.brands);
      this.dataSource.paginator = this.paginatior;
      this.dataSource.sort = this.sort;
    });
  }

  filterChange(data: Event) {
    const value = (data.target as HTMLInputElement).value;
    this.dataSource.filter = value.trim().toLowerCase();
  }

  openpopup(id: number, name: string, active: boolean) {
    const dialogRef = this.dialog.open(TableBrandComponent, {
      data: {
        message: 'Editar Marca',
        submessage: 'Editar',
        id: id,
        name: name,
        active: active
      }
    });
    this.getBrands();
    dialogRef.afterClosed().subscribe(result => {
      console.log("se cerro el dialogo");
      if (result === true) {
        this.getBrands();
      }
    });
  }

  openpupupAdd() {
    const dialogRef = this.dialog.open(TableBrandComponent, {
      data: {
        message: 'Agregar Marca',
        submessage: 'Registrar',
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.getBrands();
      }
    });
  }

  activarOdesactivar(id: number, active: boolean, name: string) {
    let confirmationMessage = '';
    active = !active;

    if (active) {
      confirmationMessage = '¿Estás seguro de que deseas activar esta marca?';
    } else {
      confirmationMessage = '¿Estás seguro de que deseas desactivar esta marca?';
    }

    const dialogRef = this.dialog.open(YesNoComponent, {
      data: {
        message: confirmationMessage,
        submessage: 'Marca: ' + name
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.brand.activeOrDeactiveBrand(id, active).subscribe((data) => {
          if (data) {
            this.getBrands();
            this._alerts.success('Marca actualizada correctamente');
          }
        });
      } else {
        this.getBrands();
      }
    });
  }

  delete(id: number) {
    console.log('Eliminar marca');
    this.brand.deleteBrand(id).subscribe(
      {
        next: (data) => {
          if (data) {
            this._alerts.success('Marca eliminada correctamente');
            this.getBrands();
          }
        },
        error: (error) => {
          if (error.status == 500) {
            this._alerts.error('Error en el servidor');
          }
          if (error.status == 404) {
            this._alerts.error('Marca no encontrada');
          }
          if (error.status == 400) {
            this._alerts.error('Error en la solicitud porque la marca esta asociada a un producto');
          }
        }
      }
    );
  }
}
