import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/modules/user/services/auth.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent {
  constructor(
    private alerts: ToastrService, private _router: Router, private _auth: AuthService
  ) { }

  estaLogeado(){
    const user = this._auth.returnUser();
    if(user){
      this._router.navigate(['/list-order-avaibles']);
    } else {
      this.alerts.error('No tienes permisos para acceder a esta página porfavor inicia sesión', 'Error');
    } 
  }
}
