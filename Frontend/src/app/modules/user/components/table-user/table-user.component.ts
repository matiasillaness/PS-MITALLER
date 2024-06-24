import { AfterViewInit, Component, OnChanges, OnDestroy, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserResponseDTO } from '../../models/UserResponseDTO';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { RegisterUserAdminComponent } from '../register-user-admin/register-user-admin.component';

@Component({
  selector: 'app-table-user',
  templateUrl: './table-user.component.html',
  styleUrls: ['./table-user.component.css']
})
export class TableUserComponent implements OnInit, OnDestroy, AfterViewInit{
  abrirModalMails() {

  }
  
  abrirModalRegistrarUsuario() {
   this._dialog.open(RegisterUserAdminComponent, {
      data: {
        message: 'Registrar Empleado',
        submessage: 'Registrar',
        email: '',
        user: {
          nombre: '',
          apellido: '',
          telefono: '',
          direccion: '',
          password: ''
        }
      },
      width: '700px',
      height: 'auto'
    }).afterClosed().subscribe({
      next: (data) => {
        if (data) {
          this.obtenerUsuarios();
        }
      }
    });
  }

  abrirModalEditarUsuario(email: string, user: any) {
    this._dialog.open(RegisterUserAdminComponent, {
      data: {
        message: 'Editar Usuario',
        submessage: 'Editar',
        email: email,
        user: user
      },
      width: '700px',
      height: 'auto'
    }).afterClosed().subscribe({
      next: (data) => {
        this.aplicarFiltros();
      }
    });
  }



  users: UserResponseDTO[] = [];
  dataSource = new MatTableDataSource<UserResponseDTO>();
  totalItems = 0; // Total de elementos, para el paginador
  displayedColumns: string[] = ['nombre','apellido', 'email', 'telefono', 'direccion','rol','activo', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;
  form!: FormGroup<any>;
  roles: any;
  suscription: Subscription = new Subscription();


 

  constructor(private _userService: UserService, private _formBuilder: FormBuilder, private _dialog: MatDialog) { }

  
  ngOnDestroy(): void {
    this.suscription.unsubscribe();
    
  }

  ngOnInit(): void {
    this.obtenerUsuarios();

    this.form = this._formBuilder.group({
      email: [''],
      nombre: [''],
      apellido: [''],
      activo: [null],
      rol: [''],
      telefono: ['']
    });

    this.roles = [
      { value: 'ROLE_EMPLOYER', label: 'Empleado' },
      { value: 'ROLE_USER', label: 'Usuario' },
      { value: 'ROLE_ADMIN', label: 'Administrador' }
    ];

  }


  obtenerUsuarios(
    email?: string, 
    nombre?: string, 
    apellido?: string,
    activo?: boolean,
    rol?: string,
    telefono?: string, 
    size?: number,
    page?: number
  ) {
    this._userService.obtenerUsuarios(
      email, 
      nombre, 
      apellido, 
      activo, 
      rol, 
      telefono, 
      size, 
      page
    ).subscribe({
      next: (data) => {
         // Agrega los nuevos autos al arreglo existente
         if (this.users) {
          this.users.push(...data);
        } else {
          this.users = data;
        }
        this.dataSource.data = this.users;
        console.log(this.users);
      }
    });
  }


  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  
  indexPorLosCualesYaDesplaze: number[] = [];
  cambioDePagina($event: PageEvent) {
    this.indexPorLosCualesYaDesplaze.push($event.pageIndex);
    
    if (this.indexPorLosCualesYaDesplaze.includes($event.pageIndex)) {
      return;
    }

    this.obtenerUsuarios(undefined, undefined, undefined, undefined, undefined, undefined, $event.pageSize, $event.pageIndex + 1);
  }

  
  aplicarFiltros() {
    this.users = [];
    this.dataSource.data = this.users;
    this.obtenerUsuarios(
      this.form.value.email, 
      this.form.value.nombre, 
      this.form.value.apellido, 
      this.form.value.activo, 
      this.form.value.rol, 
      this.form.value.telefono
    );
    this.form.reset();
    
  }

  activarODesactivar(activo: boolean, email: string) {
    this._userService.activarDesactivarUsuario(
      email, 
      activo
    ).subscribe({
      next: (data) => {
        this.aplicarFiltros();
      }
    });

  }
}
