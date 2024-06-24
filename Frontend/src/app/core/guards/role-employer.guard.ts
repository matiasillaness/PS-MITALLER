import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const roleEmployerGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const user = JSON.parse(localStorage.getItem('user') || '{}');

  if (user.role === 'ROLE_EMPLOYER') {
    return true;
  } else {
    router.navigate(['/access-denied']); // Redirige a la página de "Acceso Denegado"
    return false;
  }
};
