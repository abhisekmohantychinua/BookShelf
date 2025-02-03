import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth.service';

export const adminGuard: CanActivateFn = (route, state) => {
  const role: 'ADMIN' | 'USER' | undefined = inject(AuthService).$role();
  const router: Router = inject(Router);
  if (role !== 'ADMIN') {
    router.navigate(['/dashboard']).then();
    return false;
  }
  return role === 'ADMIN';
};
