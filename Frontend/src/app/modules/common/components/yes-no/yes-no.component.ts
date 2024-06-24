import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-yes-no',
  templateUrl: './yes-no.component.html',
  styleUrls: ['./yes-no.component.css']
})
export class YesNoComponent {

  constructor(private dialogRef: MatDialogRef<YesNoComponent>
    , @Inject(MAT_DIALOG_DATA) public data: { message: string, submessage: string}
  ) { }



  onNo() {
    this.dialogRef.close(false);
  }

  onYes() {
    this.dialogRef.close(true);
  }

  



}
