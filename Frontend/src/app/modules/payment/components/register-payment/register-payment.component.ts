import { Component, OnDestroy, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CarService } from 'src/app/modules/car/services/car.service';
import { PaymentService } from '../../services/payment.service';
import { ServiceService } from 'src/app/modules/inventory/services/service/service.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { VentaRequest } from '../../models/Payment.all';
import { CarResponseDTO } from 'src/app/modules/car/models/carResponseDTO';
import { ServiceResponseDTO } from 'src/app/modules/inventory/models/service/Service.all';
import { Subscription } from 'rxjs';
import { BuyService } from 'src/app/modules/buy/service/buy.service';
import { RepuestoResponseDTO } from 'src/app/modules/inventory/models/product/Repuesto.all';
import { ProductService } from 'src/app/modules/inventory/services/product/product.service';
import { UserDTO } from 'src/app/modules/user/models/UserDTO';
import { PatenteValidator } from 'src/app/core/validators/patente-validator';

@Component({
  selector: 'app-register-payment',
  templateUrl: './register-payment.component.html',
  styleUrls: ['./register-payment.component.css']
})
export class RegisterPaymentComponent implements OnInit, OnDestroy{
 



  async save() {
    try {
      await this.findCar(this.patente?.value);
  
      // Asegurar que ventaRequest no sea undefined
      if (!this.ventaRequest) {
        this.ventaRequest = {
          tipoPago: '',
          tipoFactura: '',
          razonSocial: '',
          dniCliente: '',
          direccionCliente: '',
          descuento: 0,
          telefonoCliente: '',
          email: '',
          idVehiculo: 0,
          detalleVentaRequest: [],
          descripcion: '',
          mercadoPago: false
        };
      }
  
      this.ventaRequest.tipoPago = this.tipoPago?.value;
      this.ventaRequest.tipoFactura = this.tipoFactura?.value;
      this.ventaRequest.razonSocial = this.razonSocial?.value;
      this.ventaRequest.dniCliente = this.dniCliente?.value;
      this.ventaRequest.direccionCliente = this.direccionCliente?.value;
      this.ventaRequest.descuento = this.descuento?.value;
      this.ventaRequest.telefonoCliente = this.telefonoCliente?.value;
      this.ventaRequest.descripcion = this.descripcion?.value;
      this.ventaRequest.mercadoPago = false;
      this.ventaRequest.email = this.user.email;
      this.ventaRequest.idVehiculo = this.car.idVehiculo;
  
      this.detalleVentaRepuesto.controls.forEach((element: any) => {
        this.ventaRequest.detalleVentaRequest.push({
          idRepuesto: element.value.idRepuesto,
          cantidad: element.value.cantidad,
          idServicio: null
        });
      });
      this.detalleVentaServicio.controls.forEach((element: any) => {
        this.ventaRequest.detalleVentaRequest.push({
          idServicio: element.value.idServicio,
          cantidad: element.value.cantidad,
          idRepuesto: null
        });
      });
  
      console.log(this.ventaRequest);
  
      this.suscription.add(
        this._paymentService.postPayment(this.ventaRequest).subscribe({
          next: (data) => {
            this._alert.success('Venta realizada con exito');
            this.form.reset();
          },
          error: (error) => {
            this._alert.error('Error al realizar la venta');
          }
        })
      );
    } catch (error) {
      this._alert.error('Error al buscar el auto por patente');
    }
  }
  
  
  ventaRequest: VentaRequest = {
    tipoPago: '',
    tipoFactura: '',
    razonSocial: '',
    dniCliente: '',
    direccionCliente: '',
    descuento: 0,
    telefonoCliente: '',
    email: '',
    idVehiculo: 0,
    detalleVentaRequest: [],
    descripcion: '',
    mercadoPago: false
  }; 
  form!: FormGroup
  car!: CarResponseDTO;
  service: ServiceResponseDTO[] = [];
  repuesto: RepuestoResponseDTO[] = [];
  typePay: string[] = []
  typeBill: string[] = []
  suscription: Subscription = new Subscription();
  total!: string|number;
  user!: UserDTO;


  constructor(
    private _alert: ToastrService, 
    private _carService: CarService, 
    private _repuestoService: ProductService,
    private _paymentService: PaymentService,
    private _serviceServicios: ServiceService,
    private _formBuilder: FormBuilder,
    private _buyService: BuyService
    ) {}
  
  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  ngOnInit(): void {
    this.charges();
    this.form = this._formBuilder.group({
      patente: ['', [Validators.required,PatenteValidator.validate()]],
      tipoPago: '',
      tipoFactura: '',
      razonSocial: '',
      dniCliente: '',
      direccionCliente: '',
      descuento: '',
      telefonoCliente: '',
      descripcion: '',
      detalleVentaRepuesto: this._formBuilder.array([]),
      detalleVentaServicio:  this._formBuilder.array([])
    });

    this.user = JSON.parse(localStorage.getItem('user') || '{}');
    console.log(this.user); 
  }


  

  
  
  charges() {
    this.findService();
    this.chargeTypePay();
    this.chargeTypeBill();
    this.chargeRepuesto();
  }
  
  chargeRepuesto() {
    this.suscription.add(
      this._repuestoService.obtenerRepuestos(undefined, undefined, undefined, undefined, undefined, true).subscribe({
        next: (data) => {
          this.repuesto = data;
        },
        error: (error) => {
          this._alert.error('Error al buscar los repuestos');
        }
      })
    );
  }
  
  findCar(patente: string): Promise<any> {
    return new Promise((resolve, reject) => {
      this.suscription.add(
        this._carService.obtenerAutoPorPatente(patente).subscribe({
          next: (data) => {
            this.car = data;
            console.log(this.car);
            resolve(data);  // Resolver la promesa con los datos del auto
          },
          error: (error) => {
            this._alert.error('Error al buscar el auto');
            reject(error);  // Rechazar la promesa en caso de error
          }
        })
      );
    });
  }
  

  findService() {
    this.suscription.add(
      this._serviceServicios.obtenerTodosLosServicios(undefined, undefined, undefined, undefined, undefined, true).subscribe({
        next: (data) => {
          this.service = data;
        },
        error: (error) => {
          this._alert.error('Error al buscar los servicios');
        }
      })
    );
  }

  chargeTypePay() {
    /*this.suscription.add(
      this._buyService.getTipoPago().subscribe({
        next: (data) => {
          this.typePay = data;
        },
        error: (error) => {
          this._alert.error('Error al buscar los tipos de pago');
        }
      })
    ); */

    this.typePay = ['CHEQUE', 'TARJETA_CREDITO', 'EFECTIVO', 'TRANSFERENCIA', 'TARJETA_DEBITO'];
    
  }

  chargeTypeBill() {
    this.suscription.add(
      this._buyService.getTipoFactura().subscribe({
        next: (data) => {
          this.typeBill = data;
        },
        error: (error) => {
          this._alert.error('Error al buscar los tipos de factura');
        }
      })
    );
  }
  
  addDetailService() {
    const detail = this._formBuilder.group({
      idServicio: '',
      cantidad: '',
    });
    this.detalleVentaServicio.push(detail);
    
  }

  removeDetailService(index: number) {
    this.detalleVentaServicio.removeAt(index);
    this.ventaRequest.detalleVentaRequest.splice(index, 1); 
  }

  addDetailRepuesto() {
    const detail = this._formBuilder.group({
      idRepuesto: '',
      cantidad: '',
    });
    this.detalleVentaRepuesto.push(detail);
  }

  removeDetail(index: number) {
    this.detalleVentaRepuesto.removeAt(index);
    this.ventaRequest.detalleVentaRequest.splice(index, 1);
  }

  public get detalleVentaRepuesto() {
    return this.form.get('detalleVentaRepuesto') as FormArray;
  }

  public get detalleVentaServicio() {
    return this.form.get('detalleVentaServicio') as FormArray;
  }

  public get patente() {
    return this.form.get('patente');
  }


  public get tipoPago() {
    return this.form.get('tipoPago');
  }

  public get tipoFactura() {
    return this.form.get('tipoFactura');
  }

  public get razonSocial() {
    return this.form.get('razonSocial');
  }

  public get dniCliente() {
    return this.form.get('dniCliente');
  }

  public get direccionCliente() {
    return this.form.get('direccionCliente');
  }

  public get descuento() {
    return this.form.get('descuento');
  }

  public get telefonoCliente() {
    return this.form.get('telefonoCliente');
  }

  public get descripcion() {
    return this.form.get('descripcion');
  }

  public get detalleVentaRequest() {
    return this.form.get('detalleVentaRequest');
  }

 
  
}
