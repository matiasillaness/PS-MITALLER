import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { OrderPartialRequest } from '../../models/Order.request.all';
import { OrderClientService } from '../../services/order-client.service';

@Component({
  selector: 'app-create-orders',
  templateUrl: './create-orders.component.html',
  styleUrls: ['./create-orders.component.css']
})
export class CreateOrdersComponent implements OnInit, OnDestroy {

  guardar() {
    this.orders = this.formGroup.value.fechas;
    console.log(this.orders);
    this.serviceOrder.guardarOrdenParcial(this.orders).subscribe((data) => {
      console.log(data);
    });
  }
  orders: OrderPartialRequest[] = [];
  formGroup!: FormGroup;

  constructor(private formBuild: FormBuilder,
    private serviceOrder: OrderClientService
  ) { }

  ngOnInit(): void {
    this.formGroup = this.formBuild.group({
      fechas: this.formBuild.array([])
    });
    this.agregarFecha();
  }

  ngOnDestroy(): void {
    
  }

  get fechas(): FormArray {
    return this.formGroup.get('fechas') as FormArray;
  }

  agregarFecha(): void {
    const nuevaFecha = this.formBuild.group({
      fecha: new FormControl(''),
      total: new FormControl('')
    });
    this.fechas.push(nuevaFecha);
  }

  eliminarFecha(_t15: number) {
    this.fechas.removeAt(_t15);
  }
}
