import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const token: string | undefined = inject(AuthService).$token();

  if (isExcluded(req.url) || !token) {
    return next(req);
  }

  const newRequest = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`),
  });
  return next(newRequest);
};

const excludedEndpoints: string[] = [
  '/token', '/register', '/introspect', '/revoke'
];

const isExcluded = (path: string): boolean => {
  for (let e of excludedEndpoints) {
    if (path.includes(e)) return true;
  }
  return false;
};
