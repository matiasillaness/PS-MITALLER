import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'ingresar',
        loadChildren: () => import('../app/modules/usuarios/ingresar/ingresar.component').then(m => m.IngresarComponent)
    },
    {
        path: 'registro',
        loadChildren: () => import('../app/modules/usuarios/registro/registro.component').then(m => m.RegistroComponent)
    }
];
