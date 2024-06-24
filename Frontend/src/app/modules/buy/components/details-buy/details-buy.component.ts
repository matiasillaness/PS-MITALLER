import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { BuyResponse } from '../../models/BuyResponse.all';

@Component({
  selector: 'app-details-buy',
  templateUrl: './details-buy.component.html',
  styleUrls: ['./details-buy.component.css']
})
export class DetailsBuyComponent {

  
  constructor( @Inject(MAT_DIALOG_DATA) public data: { payment: BuyResponse},
     private alerts : ToastrService
  ) { }


}
