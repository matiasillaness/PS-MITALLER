import { CanActivateFn } from '@angular/router';

export const roleGuard: CanActivateFn = (route, state) => {
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  if (user.role === 'ROLE_ADMIN' || user.role === 'ROLE_EMPLOYER') {
    return true;
  } else {
    return false;
  }
};
