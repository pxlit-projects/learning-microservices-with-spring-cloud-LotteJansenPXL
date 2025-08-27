import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BackendService {
    private gatewayUrl = 'http://localhost:8083';

  // Product Catalog Service
  getProductUrl(): string {
    return `${this.gatewayUrl}/product/api/product`;
  }
  getCategoryUrl(): string {
    return `${this.gatewayUrl}/product/api/category`;
  }

  // Shopping Cart Service
  getShoppingCartUrlWithUserId(userId: string): string {
    return `${this.gatewayUrl}/shoppingcart/api/shoppingcart/${userId}`;
  }

  getShoppingCartUrl(): string {
    return `${this.gatewayUrl}/shoppingcart/api/shoppingcart`;
  }

  // Wishlist Service
  getWishlistUrlWithUserId(userId: string): string {
    return `${this.gatewayUrl}/wishlist/api/wishlist/${userId}`;
  }

  getWishlistUrl(): string {
    return `${this.gatewayUrl}/wishlist/api/wishlist`;
  }

  // Logbook Service
  getLogbookUrl(): string {
    return `${this.gatewayUrl}/logbook/api/notification`;
  }

  getGatewayUrl():string {
    return `${this.gatewayUrl}/gateway`;
  }
}
