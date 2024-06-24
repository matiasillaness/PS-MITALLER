import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { YesNoComponent } from '../yes-no/yes-no.component';
import { AuthService } from 'src/app/modules/user/services/auth.service';
import { ModalUserClientComponent } from 'src/app/modules/user/components/modal-user-client/modal-user-client.component';

@Component({
  selector: 'app-modal-mobile',
  templateUrl: './modal-mobile.component.html',
  styleUrls: ['./modal-mobile.component.css']
})
export class ModalMobileComponent {

  openUserDetails() {
    const dialogRef = this.dialog.open(ModalUserClientComponent, {
        data: { message: 'User Details', submessage: 'User Details'}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });

    this.dialogRef.close();
  }

  constructor(private dialogRef: MatDialogRef<YesNoComponent>
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string, login: boolean }
    , private authService: AuthService, private dialog: MatDialog
  ) { }


  close() {
    this.dialogRef.close();
  }

  logout() {
    this.authService.logout();
    this.dialogRef.close();
  }
}
