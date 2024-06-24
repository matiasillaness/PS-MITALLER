import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { OrderClientService } from '../../services/order-client.service';
import { OrderForClientRequest } from '../../models/Order.request.all';
import { AuthService } from 'src/app/modules/user/services/auth.service';
import { Subscriber, Subscription } from 'rxjs';
import { PatenteValidator } from 'src/app/core/validators/patente-validator';
declare const MercadoPago: any; // Declara MercadoPago para que TypeScript lo reconozca


@Component({
  selector: 'app-order-client',
  templateUrl: './order-client.component.html',
  styleUrls: ['./order-client.component.css']
})
export class OrderClientComponent {
  id!: number;
  form!: FormGroup;
  orderClientRequest!: OrderForClientRequest;
  suscription: Subscription = new Subscription();
  typerOrder = [
    { value: 'MECANICA', viewValue: 'Mecanica' },
    { value: 'ELECTRICIDAD', viewValue: 'Electricidad' },
    { value: 'CHAPA_Y_PINTURA', viewValue: 'Chapa y Pintura' },
    { value: 'SERVICE', viewValue: 'Service' },
    { value: 'REVISION', viewValue: 'Revision' }
  ]
  constructor(private route: ActivatedRoute, 
    private _orderService: OrderClientService,private _userService: AuthService ,private _formBuilder :FormBuilder) {



      
    }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.id = Number(params.get('id'));
    });

    this.form = this._formBuilder.group({
      patenteDelVehiculo: ['', [PatenteValidator.validate()]],  // Usa la validación personalizada
      modeloDelVehiculo: [''], // <-- Asigna un valor inicial aquí
      tipoOrden: [''] // <-- Asigna un valor inicial aquí
    });

    this.orderClientRequest = {
      patenteDelVehiculo: '',
      modeloDelVehiculo: '',
      tipoOrden: '',
      emailDelCliente: ''
    };

    this.form = this._formBuilder.group({
      patenteDelVehiculo: ['', [PatenteValidator.validate()]],  // Usa la validación personalizada
      modeloDelVehiculo: '',
      tipoOrden: ''
    });
  }


 /* initMercadoPago() {
    const mp = new MercadoPago('APP_USR-1dd9c2f6-dcb8-45fd-a295-f71580af7c2d', {
      locale: 'es-AR'
    });

    const user = this._userService.returnUser();
    

    this.orderClientRequest.patenteDelVehiculo = this.form.get('patenteDelVehiculo')?.value;
    this.orderClientRequest.modeloDelVehiculo = this.form.get('modeloDelVehiculo')?.value;
    this.orderClientRequest.tipoOrden = this.form.get('tipoOrden')?.value;
    this.orderClientRequest.emailDelCliente = user.email;

    this.suscription.add(
      this._orderService.guardarOrden(this.id, this.orderClientRequest).subscribe({
        next: (data: any) => {
          this.createCheckoutButton(mp, data);
        },
        error: (error) => {
          console.error('Error al guardar la orden:', error);
        }
      })
    );
      
  
  }

  createCheckoutButton(mp: any, preferenceId: string) {
    const bricksBuilder = mp.bricks();
    const generateButton = async () => {
      if ((window as any).checkoutButton) (window as any).checkoutButton.unmount();
      (window as any).checkoutButton = await bricksBuilder.create('wallet', 'wallet_container', {
        initialization: {
          preferenceId: preferenceId,
        }
      });
    }
    generateButton();
  } */

  initMercadoPago() {
    const mp = new MercadoPago('APP_USR-1dd9c2f6-dcb8-45fd-a295-f71580af7c2d', {
      locale: 'es-AR'
    });

    this.orderClientRequest.patenteDelVehiculo = this.form.get('patenteDelVehiculo')?.value;
    this.orderClientRequest.modeloDelVehiculo = this.form.get('modeloDelVehiculo')?.value;
    this.orderClientRequest.tipoOrden = this.form.get('tipoOrden')?.value;
    this.orderClientRequest.emailDelCliente = this._userService.returnUser().email;

    console.log(this.orderClientRequest);

    this.suscription.add(
      this._orderService.guardarOrden(this.id, this.orderClientRequest).subscribe({
        next: (data: any) => {
          this.createCheckoutButton(mp, data);
        },
        error: (error) => {
          console.error('Error al guardar la orden:', error);
        }
      })
    );
  }

  createCheckoutButton(mp: any, preferenceId: string) {
    const bricksBuilder = mp.bricks();
    const generateButton = async () => {
      if ((window as any).checkoutButton) (window as any).checkoutButton.unmount();
      (window as any).checkoutButton = await bricksBuilder.create('wallet', 'wallet_container', {
        initialization: {
          preferenceId: preferenceId,
        }
      });
    }
    generateButton();
  }
  
}


/* import { Component, OnInit } from '@angular/core';
import { MercadopagoService } from '../mercadopago.service';
declare const MercadoPago: any; // Declara MercadoPago para que TypeScript lo reconozca


@Component({
  selector: 'app-mercadopago',
  templateUrl: './mercadopago.component.html',
  styleUrls: ['./mercadopago.component.css']
})
export class MercadopagoComponent implements OnInit {

  constructor(
    private mercadopagoService: MercadopagoService
  ) { }

  ngOnInit(): void {
  }

  initMercadoPago() {
    const mp = new MercadoPago('APP_USR-1dd9c2f6-dcb8-45fd-a295-f71580af7c2d', {
      locale: 'es-AR'
    });

    const article = {
      title: 'Manzana',
      quantity: 1,
      price: 12
    };

    this.mercadopagoService.createPreference(article).subscribe(preference => {
      this.createCheckoutButton(mp, preference);
    }, error => {
      alert("error: " + error);
    });
  }

  createCheckoutButton(mp: any, preferenceId: string) {
    const bricksBuilder = mp.bricks();
    const generateButton = async () => {
      if ((window as any).checkoutButton) (window as any).checkoutButton.unmount();
      (window as any).checkoutButton = await bricksBuilder.create('wallet', 'wallet_container', {
        initialization: {
          preferenceId: preferenceId,
        }
      });
    }
    generateButton();
  }

} */