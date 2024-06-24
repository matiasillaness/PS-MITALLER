import { Component, OnDestroy, OnInit } from '@angular/core';
import { OrderClientService } from '../../services/order-client.service';
import { OrderDisponibleResponse } from '../../models/Order.response.all';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-order-avaibles',
  templateUrl: './list-order-avaibles.component.html',
  styleUrls: ['./list-order-avaibles.component.css']
})
export class ListOrderAvaiblesComponent implements OnInit, OnDestroy{

  ordenes: OrderDisponibleResponse[] = [];
  suscription: Subscription = new Subscription();


  constructor(private _orderService: OrderClientService, private _route: Router) { }
  
  ngOnInit(): void {
    this.suscription.add(
      this._orderService.obtenerOrdenesDisponibles().subscribe(data => {
        console.log(data);
        this.ordenes = data;
      })
    );
  }

  ngOnDestroy(): void {
    if(this.suscription){
      this.suscription.unsubscribe();
    }
  }

  reservar(id: number) {
    this._route.navigate(['order-client', id]);
  }
}
