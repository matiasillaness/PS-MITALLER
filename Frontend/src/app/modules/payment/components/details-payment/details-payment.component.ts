import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FacturaVentaResponse } from '../../models/PaymentResponse.all';

@Component({
  selector: 'app-details-payment',
  templateUrl: './details-payment.component.html',
  styleUrls: ['./details-payment.component.css']
})
export class DetailsPaymentComponent {
  constructor( @Inject(MAT_DIALOG_DATA) public data: { payment: FacturaVentaResponse},
  private alerts : ToastrService
) { }

}
