import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { SupplierService } from 'src/app/modules/supplier/services/supplier.service';
import { BuyService } from '../../service/buy.service';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { SupplierResponseDTO } from 'src/app/modules/supplier/models/SupplierResponseDTO';
import { RepuestoResponseDTO } from 'src/app/modules/inventory/models/product/Repuesto.all';
import { ToastrService } from 'ngx-toastr';
import { BuyRequest, DetailsBuyRequest } from '../../models/BuyRequest.all';
import { ProductService } from 'src/app/modules/inventory/services/product/product.service';

@Component({
  selector: 'app-register-buy',
  templateUrl: './register-buy.component.html',
  styleUrls: ['./register-buy.component.css']
})
export class RegisterBuyComponent implements OnInit, OnDestroy {

  form:FormGroup = this.formBuilder.group({});
  supplierResponse: SupplierResponseDTO[] = [];
  repuesto: RepuestoResponseDTO[] = [];
  typePayment: string[] = [];
  tipoFactura: string[] = [];

  buyPost: BuyRequest = {} as BuyRequest;
  detailsPost: DetailsBuyRequest[] = [];
  
  private suscription: Subscription = new Subscription();
  total: number = 0;

  constructor(
    private supplierService: SupplierService,
    private buyService: BuyService,
    private repuestoService: ProductService,
    private formBuilder: FormBuilder,
    private alerts: ToastrService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      descripcion: [''],
      iva: [''],
      idProveedor: [''],
      tipoPago: [''],
      detalleCompraRequest: this.formBuilder.array([])
    });

    this.chargeSuppliers();
    this.chargeTypePayment();
    this.chargeTypeInvoice();
    this.chargeRepuesto();
    this.addDetalleCompraRequest();

    this.detalleCompraRequest.valueChanges.subscribe(() => {
      this.total = this.getTotal();
      this.updateRepuestoOptions();
    });
  }

  ngOnDestroy(): void {
    this.suscription.unsubscribe();
  }

  getTotal(): number {
    return this.detalleCompraRequest.controls.reduce((acc, control) => {
      const cantidad = control.get('cantidad')?.value || 0;
      const precioUnitario = control.get('precioUnitario')?.value || 0;
      return acc + (cantidad * precioUnitario);
    }, 0);
  }

  save(){
    this.detalleCompraRequest.controls.forEach(c => {
      const detalle = {
        idRepuesto: c.get('idRepuesto')?.value,
        cantidad: c.get('cantidad')?.value,
        precioUnitario: c.get('precioUnitario')?.value
      } as DetailsBuyRequest;
      this.detailsPost.push(detalle);
    });

    this.buyPost.descripcion = this.descripcion?.value;
    this.buyPost.iva = this.iva?.value;
    this.buyPost.idProveedor = this.idProveedor?.value;
    this.buyPost.tipoPago = this.tipoPago?.value;
    this.buyPost.detalleCompraRequest = this.detailsPost;

    console.log(this.buyPost);

    if(this.form.invalid){
      this.alerts.error('Faltan campos por llenar');
      return;
    }
    if(this.buyPost.detalleCompraRequest.length == 0){
      this.alerts.error('Debe agregar al menos un detalle de compra');
      return;
    }

    this.suscription.add(
      this.buyService.register(this.buyPost).subscribe({
          next: data => {
              this.alerts.success('Compra registrada correctamente');
              this.form.reset();
              this.detailsPost = [];
              this.detalleCompraRequest.clear();
              this.addDetalleCompraRequest();
          },
          error: error => {
              this.alerts.error('Error al registrar la compra');
          }
      })
  );
  }

  addDetalleCompraRequest(){
  
    const detalleFormGroup = this.formBuilder.group({
      idRepuesto: [''],
      cantidad: [''],
      precioUnitario: ['']
    });
    this.detalleCompraRequest.push(detalleFormGroup);

    detalleFormGroup.valueChanges.subscribe(() => {
      this.total = this.getTotal();
      this.updateRepuestoOptions();
    });

   

    this.updateRepuestoOptions();
  }

  deleteDetalleCompraRequest(index: number){
    this.detalleCompraRequest.removeAt(index);
    this.detailsPost.splice(index, 1);
    this.total = this.getTotal();
    this.updateRepuestoOptions();
  }

  updateRepuestoOptions() {
    const selectedRepuestos = new Set(
      this.detalleCompraRequest.controls.map(control => control.get('idRepuesto')?.value)
    );

    this.repuesto.forEach(r => {
      r.disabled = selectedRepuestos.has(r.idRepuesto);
    });
  }

  chargeSuppliers(){
    this.suscription.add(
      this.supplierService.obtenerTodosLosProveedores().subscribe(data => {
        this.supplierResponse = data;
      })
    );
  }

  chargeTypePayment(){
    this.suscription.add(
      this.buyService.getTipoPago().subscribe(data => {
        this.typePayment = data;
      })
    );
  }

  chargeTypeInvoice(){
    this.suscription.add(
      this.buyService.getTipoFactura().subscribe(data => {
        this.tipoFactura = data;
      })
    );
  }

  chargeRepuesto(){
    this.suscription.add(
      this.repuestoService.obtenerTodosLosRepuestos().subscribe(data => {
        this.repuesto = data;
      })
    );
  }

  get detalleCompraRequest(){
    return this.form.get('detalleCompraRequest') as FormArray;
  }
  get descripcion(){
    return this.form.get('descripcion');
  }
  get iva(){
    return this.form.get('iva');
  }
  get idProveedor(){
    return this.form.get('idProveedor');
  }
  get tipoPago(){
    return this.form.get('tipoPago');
  }

}