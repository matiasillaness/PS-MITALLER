import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { FacturaVentaResponse } from 'src/app/modules/payment/models/PaymentResponse.all';
import { OrderClientService } from '../../services/order-client.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { OrderResponse } from '../../models/Order.response.all';

@Component({
  selector: 'app-order-table',
  templateUrl: './order-table.component.html',
  styleUrls: ['./order-table.component.css'],
  providers: []
})
export class OrderTableComponent implements OnDestroy, OnInit{

  orders: OrderResponse[] = [];
  dataSource = new MatTableDataSource<OrderResponse>();
  totalItems = 0; // Total de elementos, para el paginador
  displayedColumns: string[] = [];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;
  suscription: Subscription = new Subscription();
  formFilter!: FormGroup;

  constructor(
    private orderService: OrderClientService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private router: Router,
  ) { }

  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }
  ngOnInit(): void {
    this.displayedColumns = ['id', 'fecha', 'patenteDelVehiculo', 'modeloDelVehiculo', 'nombreDelCliente', 'email' ,'tipoOrden', 'actions'];
    this.search();
  }

  search() {
    this.orderService.obtenerOrdenesCliente().subscribe((data) => {
      this.orders = data;
      this.dataSource = new MatTableDataSource(this.orders);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
}
