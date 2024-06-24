import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { BrandCarResponseDTO } from '../../models/brandCarResponseDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Subscription } from 'rxjs';
import { BrandCarService } from '../../services/brand-car.service';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ModalBrandComponent } from '../modal-brand/modal-brand.component';
import { YesNoComponent } from 'src/app/modules/common/components/yes-no/yes-no.component';

@Component({
  selector: 'app-register-brand-car',
  templateUrl: './register-brand-car.component.html',
  styleUrls: ['./register-brand-car.component.css']
})
export class RegisterBrandCarComponent implements OnInit, OnDestroy, OnChanges{


  dataSource = new MatTableDataSource<BrandCarResponseDTO>();
  brands: BrandCarResponseDTO[] = [];
  displayedColumns: string[] = ['id_brand', 'name', 'active', 'actions'];
  @ViewChild(MatPaginator) paginatior !: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;
  suscrition: Subscription = new Subscription();

  constructor(private brand: BrandCarService, private dialog: MatDialog, private _alerts: ToastrService) { }
  
  ngOnChanges(changes: SimpleChanges): void {
    this.getBrands();
  }
  
  ngOnDestroy(): void {
    if (this.suscrition) {
      this.suscrition.unsubscribe();
    }
  }
  ngOnInit(): void {
    this.getBrands();
  }



  delete(id: number) {
    console.log('Eliminar marca');
    this.brand.eliminarMarca(id).subscribe(
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
            this._alerts.error('Error en la solicitud porque la marca esta asociada a un auto');
          }
        }
      }
    );
  }


  openpopupEdit(id: number, name: string, active: boolean) {
    this.dialog.open(ModalBrandComponent, {
      data: {
        message: 'Editar Marca',
        submessage: 'Editar',
        id: id,
        name: name,
        active: active
      }
    }).afterClosed().subscribe((result) => {
      if (result) {
        this.getBrands();
      }
    });
  }
  
  
  activarOdesactivar(id: number, active: boolean) {
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
        this.brand.activeOrDeactiveCarBrand(id, active).subscribe((data) => {
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


  openpupupAdd() {
    this.dialog.open(ModalBrandComponent, {
      data: {
        message: 'Agregar Marca',
        submessage: 'Registrar',
      }
    }).afterClosed().subscribe((result) => {
      if (result) {
        this.getBrands();
      }
    });

  }


  filterChange(data: Event) {
    const value = (data.target as HTMLInputElement).value;
    this.dataSource.filter = value.trim().toLowerCase();
  }

  getBrands() {
    this.brand.obtenerMarcas().subscribe((data) => {
      data.sort((a, b) => a.id_brand - b.id_brand);
      this.brands = data;
      this.dataSource = new MatTableDataSource(this.brands);
      this.dataSource.paginator = this.paginatior;
      this.dataSource.sort = this.sort;
    });
  }
}
