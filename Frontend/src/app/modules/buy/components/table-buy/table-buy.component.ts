import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RepuestoResponseDTO } from 'src/app/modules/inventory/models/product/Repuesto.all';
import { BuyService } from '../../service/buy.service';
import { BuyResponse, DetailsBuyResponse } from '../../models/BuyResponse.all';
import { DatePipe } from '@angular/common';
import { StoreConfig } from '@ngrx/store';
import { DetailsBuyComponent } from '../details-buy/details-buy.component';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';

pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-table-buy',
  templateUrl: './table-buy.component.html',
  providers: [DatePipe],
  styleUrls: ['./table-buy.component.css']
})
export class TableBuyComponent implements OnInit, OnDestroy {
  darDeBaja(nombre: string) {
    this.suscription.add(
      this.buyService.delete(nombre).subscribe((response) => {
        this.search();
      })
    );
  }

  payments: PaymentResponse[] = [];
  dataSource = new MatTableDataSource<BuyResponse>();
  totalItems = 0; // Total de elementos, para el paginador
  displayedColumns: string[] = [];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;
  suscription: Subscription = new Subscription();
  formFilter!: FormGroup;

  constructor(
    private buyService: BuyService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private router: Router,
    private datePipe: DatePipe
  ) { }

  ngOnDestroy(): void {

  }
  ngOnInit(): void {
    this.formFilter = this.formBuilder.group({
      nombre: [''],
      telefono: [''],
      email: [''],
      proveedor: [''],
      tipoPago: [''],
      totalMayoA: [''],
      totalMenorA: [''],
      fecha_inicio: [''],
      fecha_fin: [''],
      dadaDeBaja: ['']
    });
    this.displayedColumns = [
      'nombreCompra', 'descripcion', 'fecha', 'proveedor', 'tipoPago', 'total', 'detalles', 'dadaDeBaja', 'acciones'
    ];
    this.search();
  }
  search() {
    



    this.suscription.add(
      this.buyService.get(
        this.formFilter.get('nombre')?.value,
        this.datePipe.transform( this.formFilter.get('fecha_inicio')?.value, 'yyyy-MM-dd HH:mm') || undefined,
        this.datePipe.transform( this.formFilter.get('fecha_fin')?.value, 'yyyy-MM-dd HH:mm') || undefined,
        this.formFilter.get('proveedor')?.value,
        this.formFilter.get('tipoPago')?.value,
        this.formFilter.get('totalMayoA')?.value,
        this.formFilter.get('totalMenorA')?.value,
        this.formFilter.get('dadaDeBaja')?.value,
        this.formFilter.get('numeroTelefono')?.value,
        this.formFilter.get('email')?.value
      ).subscribe((response: BuyResponse[]) => {
        this.dataSource = new MatTableDataSource(response);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.totalItems = response.length;
        console.log(response);
      })
    );
  }

  verDetalles(nombreCompra: String) {
    let buy = this.dataSource.data.find((buy) => buy.nombreCompra === nombreCompra);
    this.dialog.open(DetailsBuyComponent, {
      data: { payment: buy }
    });
  }
  imprimir(compra: BuyResponse) {
    const docDefinition: any = {
      content: [
        { text: 'Reporte de Compra', style: 'header' },
        { text: `Nombre de la Compra: ${compra.nombreCompra}`, style: 'subheader' },
        { text: `Fecha: ${compra.fecha}`, style: 'subheader' },
        { text: `Tipo de Pago: ${compra.tipoPago}`, style: 'subheader' },
        { text: ' ', style: 'spacer' },

        { text: 'Datos del Proveedor:', style: 'subheader' },
        {
          columns: [
            { width: '*', text: `Nombre: ${compra.proveedor.nombre}` },
            { width: '*', text: `Teléfono: ${compra.proveedor.telefono}` },
          ]
        },
        {
          columns: [
            { width: '*', text: `Dirección: ${compra.proveedor.direccion}` },
            { width: '*', text: `Email: ${compra.proveedor.email}` },
          ]
        },
        { text: `Descripción: ${compra.proveedor.descripcion}`, margin: [0, 0, 0, 10] },
        { text: `Tipo de Proveedor: ${compra.proveedor.tipoProveedor}`, margin: [0, 0, 0, 10] },

        { text: 'Detalles de la Compra:', style: 'subheader' },
        {
          table: {
            widths: ['*', 50, 100, 100],
            body: [
              [
                { text: 'Nombre Repuesto', style: 'tableHeader' },
                { text: 'Cantidad', style: 'tableHeader' },
                { text: 'Precio Unitario', style: 'tableHeader', alignment: 'right' },
                { text: 'Subtotal', style: 'tableHeader', alignment: 'right' }
              ],
              ...compra.detalles.map((detalle: DetailsBuyResponse) => ([
                detalle.nombreRepuesto,
                detalle.cantidad,
                { text: `$${detalle.precioUnitario.toFixed(2)}`, alignment: 'right' },
                { text: `$${detalle.subtotal.toFixed(2)}`, alignment: 'right' }
              ]))
            ]
          },
          layout: {
            fillColor: function (rowIndex: number) {
              return (rowIndex % 2 === 0) ? '#f3f3f3' : null;
            }
          }
        },
        {
          text: `Total: $${compra.total.toFixed(2)}`,
          style: 'total',
          alignment: 'right',
          margin: [0, 10, 0, 0]
        }
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true,
          margin: [0, 0, 0, 10]
        },
        subheader: {
          fontSize: 14,
          bold: true,
          margin: [0, 10, 0, 5]
        },
        tableHeader: {
          bold: true,
          fontSize: 12,
          color: 'black'
        },
        total: {
          fontSize: 16,
          bold: true
        },
        spacer: {
          margin: [0, 10, 0, 10]
        }
      }
    };

    pdfMake.createPdf(docDefinition).open();
  }

  aplicarFiltros() {
    console.log(this.formFilter.value);
    this.search();
    this.formFilter.reset();
    console.log(this.datePipe.transform(this.fecha_fin, 'yyyy-MM-dd'), this.datePipe.transform(this.fecha_inicio, 'yyyy-MM-dd'));
  }


  get fecha_inicio() {
    return this.formFilter.get('fecha_inicio')?.value;
  }

  get fecha_fin() {
    return this.formFilter.get('fecha_fin')?.value;
  }
}
