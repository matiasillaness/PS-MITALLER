import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { DetalleVentaResponse, FacturaVentaResponse } from '../../models/PaymentResponse.all';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Subscription } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PaymentService } from '../../services/payment.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { DetailsPaymentComponent } from '../details-payment/details-payment.component';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';

pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-table-payment',
  templateUrl: './table-payment.component.html',
  styleUrls: ['./table-payment.component.css'],
  providers: [DatePipe]
})
export class TablePaymentComponent implements OnInit, OnDestroy {

  darDeBaja(nombre: string) {
    this.paymentService.deletePayment(nombre).subscribe((data) => {
      if (data) {
        this.search();
      }
    });
  }
  verDetalles(elementos: FacturaVentaResponse) {
      this.dialog.open(DetailsPaymentComponent, {
        data: { payment: elementos }
      });
  }
  aplicarFiltros() {
    console.log(this.formFilter.value);
    this.search();
    this.formFilter.reset();
  }


  imprimir(element: FacturaVentaResponse) {
    const descuentoTotal = element.descuento || 0; 
  
    const subtotal = element.detalles.reduce((acc, detalle) => acc + detalle.subtotal, 0);
    const totalConDescuento = subtotal - descuentoTotal;
    const totalIVA = totalConDescuento * 0.21; 
    const totalFinal = totalConDescuento + totalIVA;
  
    const pdfDefinition: any = {
      content: [
        { text: `Venta N° ${element.numeroFactura}`, style: 'header' },
        { text: `Fecha: ${element.fecha}`, style: 'subheader' },
        { text: ' ', style: 'spacer' }, // Espacio en blanco
        {
          columns: [
            {
              width: '*',
              text: 'Datos del cliente:',
              bold: true,
              margin: [0, 10, 0, 10]
            }
          ]
        },
        {
          columns: [
            {
              width: '*',
              text: [
                { text: `Nombre: ${element.razonSocial}\n`, bold: false },
                { text: `DNI: ${element.dniCliente}\n`, bold: false },
                { text: `Teléfono: ${element.telefonoCliente}\n`, bold: false },
              ]
            }
          ]
        },
        {
          text: 'Concepto:',
          bold: true,
          margin: [0, 10, 0, 5]
        },
        {
          text: `Observaciones: ${element.descripcion}`,
          margin: [0, 0, 0, 10]
        },
        {
          table: {
            widths: ['*', 50, 60, 50, 60, 60],
            body: [
              [
                { text: 'Detalle', style: 'tableHeader' },
                { text: 'Cant.', style: 'tableHeader' },
                { text: 'P.Uni.', style: 'tableHeader' },
                { text: 'Desc.', style: 'tableHeader' },
                { text: 'Subtotal', style: 'tableHeader' },
                { text: 'Total', style: 'tableHeader' }
              ],
              ...element.detalles.map((detalle: DetalleVentaResponse) => ([
                detalle.nombreRepuesto || detalle.nombreServicio,
                detalle.cantidad,
                { text: `$${detalle.precioUnitario.toFixed(2)}`, alignment: 'right' },
                { text: '-', alignment: 'right' }, // No se aplica descuento por item
                { text: `$${detalle.subtotal.toFixed(2)}`, alignment: 'right' },
                { text: `$${(detalle.subtotal).toFixed(2)}`, alignment: 'right' }
              ])),
              [
                { text: 'Subtotal', colSpan: 5, alignment: 'right' }, {}, {}, {}, {},
                { text: `$${subtotal.toFixed(2)}`, alignment: 'right' }
              ],
              [
                { text: 'Descuento', colSpan: 5, alignment: 'right' }, {}, {}, {}, {},
                { text: `$${descuentoTotal.toFixed(2)}`, alignment: 'right' }
              ],
              [
                { text: 'Subtotal con Descuento', colSpan: 5, alignment: 'right' }, {}, {}, {}, {},
                { text: `$${totalConDescuento.toFixed(2)}`, alignment: 'right' }
              ],
              [
                { text: 'IVA (21%)', colSpan: 5, alignment: 'right' }, {}, {}, {}, {},
                { text: `$${totalIVA.toFixed(2)}`, alignment: 'right' }
              ],
              [
                { text: 'Total', colSpan: 5, alignment: 'right', bold: true }, {}, {}, {}, {},
                { text: `$${totalFinal.toFixed(2)}`, alignment: 'right', bold: true }
              ]
            ]
          },
          layout: {
            fillColor: function (rowIndex: number) {
              return (rowIndex % 2 === 0) ? '#f3f3f3' : null; // Alterna el color de fondo de las filas
            }
          }
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
        spacer: {
          margin: [0, 10, 0, 10]
        }
      }
    };
  
    pdfMake.createPdf(pdfDefinition).open();
  }
  

  
  payments: FacturaVentaResponse[] = [];
  dataSource = new MatTableDataSource<FacturaVentaResponse>();
  totalItems = 0; // Total de elementos, para el paginador
  displayedColumns: string[] = [];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;
  suscription: Subscription = new Subscription();
  formFilter!: FormGroup;

  constructor(
    private paymentService: PaymentService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private router: Router,
    private datePipe: DatePipe
  ) { }

  ngOnInit(): void {
    this.formFilter = this.formBuilder.group({
      numeroFactura: [''],
      telefono: [''],
      cliente: [''],
      tipoPago: [''],
      descripcion: [''],
      tipoFactura: [''],
      totalMayoA: [''],
      totalMenorA: [''],
      fechaInicio: [''],
      fechaFin: ['']
    });
    this.search();
    this.displayedColumns = ['numeroFactura', 'fecha', 'cliente', 'tipoPago', 'tipoFactura', 'total', 'descripcion', 'patente','modelo','detalles' ,'dadaDeBaja', 'acciones'];
  }

  ngOnDestroy(): void {
  }

  search(

  ) {
    this.suscription.add(
      this.paymentService.getPayments(
        this.datePipe.transform(this.formFilter.get('fechaInicio')?.value, 'yyyy-MM-dd') || undefined,
        this.datePipe.transform(this.formFilter.get('fechaFin')?.value, 'yyyy-MM-dd') || undefined,
        this.formFilter.get('tipoPago')?.value,
        this.formFilter.get('tipoFactura')?.value,
        this.formFilter.get('numeroFactura')?.value,
        this.formFilter.get('descripcion')?.value,
        this.formFilter.get('cliente')?.value,
      ).subscribe((data) => {
        this.payments = data;
        this.dataSource = new MatTableDataSource(this.payments);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        console.log(data);
      })
    );
  }

}
