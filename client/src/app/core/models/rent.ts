import {User} from './user';
import {Book} from './book';

export interface Rent {
  id: number;
  user: User;
  book: Book;
  rentedAt: string;
  returnedAt?: string;
  fine: number;
  quantity: number;
}
