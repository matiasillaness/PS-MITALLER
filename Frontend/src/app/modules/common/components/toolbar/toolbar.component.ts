import { Component, ViewChild } from '@angular/core';
import { BreakpointObserver } from '@angular/cdk/layout';
import { MatSidenav } from '@angular/material/sidenav';
import { UserDTO } from 'src/app/modules/user/models/UserDTO';


@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent {
  title = 'material-responsive-sidenav';
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile= true;
  isCollapsed = true;
  panelDe!: string;
  name!: string;
  user!: UserDTO;

  constructor(private observer: BreakpointObserver) {}

  ngOnInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      if(screenSize.matches){
        this.isMobile = true;
      } else {
        this.isMobile = false;
      }
    });

    this.user = JSON.parse(localStorage.getItem('user') || '{}');

    this.name = this.user.firstname + ' ' + this.user.lastname;
   
    if(this.user.role == 'ROLE_ADMIN'){
      this.panelDe = 'Administrador';
    }
    if(this.user.role == 'ROLE_EMPLOYE'){
      this.panelDe = 'Empleado';
    }
  }

  toggleMenu() {
    if(this.isMobile){
      this.sidenav.toggle();
      this.isCollapsed = false;
    } else {
      this.sidenav.open();
      this.isCollapsed = !this.isCollapsed;
    }
  }

  cerrarSesion() {
    localStorage.removeItem('user');
    window.location.href = '/login';
    }

}
