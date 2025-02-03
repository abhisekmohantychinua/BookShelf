import {Injectable, signal, WritableSignal} from '@angular/core';
import {IntrospectionResponse} from '../models/introspection-response';
import {StorageService} from './storage.service';
import {environment} from '../../../environments/environment';
import {map, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {AuthResponse} from '../models/auth-response';
import {AuthRequest} from '../models/auth-request';
import {RegisterRequest} from '../models/register-request';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public $token: WritableSignal<string | undefined> = signal(undefined);
  public $role: WritableSignal<'ADMIN' | 'USER' | undefined> = signal(undefined);

  constructor(private storage: StorageService, private http: HttpClient) {
  }

  init(): void {
    const token = this.storage.get('token');
    if (token) {
      this.introspect()
        .subscribe((res) => this.$token.set(token));
    } else {
      this.$token.set(undefined);
      this.$role.set(undefined);
    }
  }

  login(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.BASE_URL}/token`, credentials)
      .pipe(
        map(response => {
          this.storage.set('token', response.token);
          this.$token.set(response.token);
          return response;
        })
      );
  }

  introspect(): Observable<IntrospectionResponse> {
    return this.http
      .post<IntrospectionResponse>(`${environment.BASE_URL}/introspect?${this.$token() ? this.$token() : ''}`, {})
      .pipe(
        map(response => {
          if (response.valid) {
            this.$role.set(response.role);
          } else {
            this.$role.set(undefined);
            this.$token.set(undefined);
            this.storage.remove('token');
          }
          return response;
        })
      );
  }

  register(credentials: RegisterRequest): Observable<void> {
    return this.http
      .post<void>(`${environment.BASE_URL}/register`, credentials);
  }

  logout(): Observable<void> {
    return this.http
      .post<void>(`${environment.BASE_URL}/revoke?${this.$token() ? this.$token() : ''}`, {})
      .pipe(
        map(response => {
          this.$token.set(undefined);
          this.storage.remove('token');
          this.$role.set(undefined);
        })
      );
  }

}
