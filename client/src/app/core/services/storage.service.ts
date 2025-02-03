import {Inject, inject, Injectable, PLATFORM_ID} from '@angular/core';
import {isPlatformBrowser} from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(@Inject(PLATFORM_ID) public platformId: Object) {
  }

  set(key: string, value: any): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(key, JSON.stringify(value));
    }
  }

  get(key: string): any | null {
    if (isPlatformBrowser(this.platformId)) {
      let obj: string | null = localStorage.getItem(key);
      if (obj) {
        return JSON.parse(obj);
      }
    }
    return null;
  }

  remove(key: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(key);
    }
  }

}
