export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  role: 'ADMIN' | 'USER';
}
