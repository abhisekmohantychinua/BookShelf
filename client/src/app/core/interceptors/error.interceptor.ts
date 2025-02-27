import {HttpInterceptorFn} from '@angular/common/http';
import {catchError} from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req)
    .pipe(
      catchError(error => {
        console.log(error);
        throw new Error(error);
      })
    );
};
