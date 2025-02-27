import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {inject} from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const token: string | undefined = inject(AuthService).$token();
  const router: Router = inject(Router);
  if (!token) {
    router.navigate(['/auth']).then();
    return false;
  }
  return !!token;
};
