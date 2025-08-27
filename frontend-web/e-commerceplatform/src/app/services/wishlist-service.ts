import { HttpClient } from "@angular/common/http";
import { inject } from "@angular/core/primitives/di";
import { BackendService } from "./backend-service";
import { AuthService } from "./auth-service";
import { ShoppingCartProduct } from "../models/shoppingcart-product";
import { Inject, Injectable } from "@angular/core";
import { Wishlist } from "../models/wishlist";

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
    private http = inject(HttpClient);
  private backendService = inject(BackendService);
  private authService = inject(AuthService);

  createWishlist(userId: string) {
    const url = this.backendService.getWishlistUrlWithUserId(userId);
    return this.http.post(url, {});
  }

  getWishlist(userId: string) {
    const url = `${this.backendService.getWishlistUrlWithUserId(userId)}`;
    return this.http.get<Wishlist>(url);
  }

  addItemToWishlist(listId: number, product: ShoppingCartProduct) {
    const url = `${this.backendService.getWishlistUrl()}/${listId}/addProduct`;
    return this.http.put(url, product);
  }

  removeItemFromWishlist(listId: number, productId: string) {
    const url = `${this.backendService.getWishlistUrl()}/${listId}/removeProduct/${productId}`;
    return this.http.put(url, {});
  }

}
