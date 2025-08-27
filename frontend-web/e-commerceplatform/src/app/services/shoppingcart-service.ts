import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BackendService } from './backend-service';
import { AuthService } from './auth-service';
import { Shoppingcart } from '../models/shoppingcart';
import { Observable } from 'rxjs/internal/Observable';
import { ShoppingCartProduct } from '../models/shoppingcart-product';
import { switchMap } from 'rxjs/internal/operators/switchMap';

@Injectable({
  providedIn: 'root'
})
export class ShoppingcartService {
  private http = inject(HttpClient);
  private backendService = inject(BackendService);
  private authService = inject(AuthService);

  getShoppingCart(userId: string) : Observable<Shoppingcart> {
    const url = this.backendService.getShoppingCartUrlWithUserId(userId);
    return this.http.get<Shoppingcart>(url);
  }

  createShoppingCart(userId: string) {
    const url = this.backendService.getShoppingCartUrlWithUserId(userId);
    return this.http.post(url, {});
  }

  addItemToCart(userId: string, shoppingCartProduct: ShoppingCartProduct, shoppingCartId: number): Observable<any> {
  const url = `${this.backendService.getShoppingCartUrl()}/${shoppingCartId}/addProduct`;
  return this.http.put(url, shoppingCartProduct);
}

removeItemFromCart(userId: string, productId: number): Observable<any> {
  return this.getShoppingCart(userId).pipe(
    switchMap((shoppingCart: Shoppingcart) => {
      const url = `${this.backendService.getShoppingCartUrl()}/${shoppingCart.id}/removeProduct/${productId}`;
      return this.http.put(url, {});
    })
  );
}
}
