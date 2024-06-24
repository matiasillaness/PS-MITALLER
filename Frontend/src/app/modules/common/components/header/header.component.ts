import { AfterViewInit, Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { of } from 'rxjs';
import { ModalMobileComponent } from '../modal-mobile/modal-mobile.component';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/modules/user/services/auth.service';
import { ModalUserClientComponent } from 'src/app/modules/user/components/modal-user-client/modal-user-client.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnChanges, OnDestroy{

  title = 'mitallerf';
  login: boolean = false;
  isAdmin: boolean = false;
  

  constructor( private dialog: MatDialog, private authService: AuthService) { }
  ngOnChanges(changes: SimpleChanges): void {
    this.isAdmin = this.authService.isAdmin();
    this.login = this.authService.isLogged();  }
  ngOnDestroy(): void {
    this.isAdmin = this.authService.isAdmin();
    this.login = this.authService.isLogged();  }
  
  openUserDetails() {
    const dialogRef = this.dialog.open(ModalUserClientComponent, {
        data: { message: 'User Details', submessage: 'User Details', login: this.login}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });

  }

  loadScript(url: string) {
  
  }

  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
    this.login = this.authService.isLogged();
  }

  openMobileNavigation() {
    const dialogRef = this.dialog.open(ModalMobileComponent, {
        data: { message: 'Mobile Navigation', submessage: 'Mobile Navigation', login: this.login}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });


  }

  logout() {
    this.authService.logout();
    this.login = false;
  }

}
