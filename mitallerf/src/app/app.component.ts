import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { PageComponent } from "./modules/landing/page/page.component";
import { MarcasComponent } from './modules/vehiculos/marcas/marcas.component';
import { IngresarComponent } from './modules/usuarios/ingresar/ingresar.component';
import { RegistroComponent } from './modules/usuarios/registro/registro.component';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
    imports: [
        CommonModule, 
        RouterOutlet, 
        RouterModule,
        PageComponent, 
        MarcasComponent, 
        IngresarComponent, 
        RegistroComponent,
    ]
})
export class AppComponent{
  
}
