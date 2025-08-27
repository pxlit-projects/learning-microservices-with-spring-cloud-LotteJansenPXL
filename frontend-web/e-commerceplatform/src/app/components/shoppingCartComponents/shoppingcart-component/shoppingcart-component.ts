import { Component, inject } from '@angular/core';
import { ShoppingcartService } from '../../../services/shoppingcart-service';
import { AuthService } from '../../../services/auth-service';
import { Product } from '../../../models/product';

@Component({
  selector: 'app-shoppingcart-component',
  standalone: false,
  templateUrl: './shoppingcart-component.html',
  styleUrl: './shoppingcart-component.css'
})
export class ShoppingcartComponent {
  private shoppingCartService = inject(ShoppingcartService);
  private authService = inject(AuthService);
  shoppingCart: any;
  errorMessage: string = '';
  products: Product[] = [];

  ngOnInit() {
    this.loadShoppingCart();
  }

  private loadShoppingCart() {
    console.log('Loading shopping cart...');
    const userId = this.authService.getRole();
    if (!userId) {
      this.errorMessage = 'User not authenticated';
      return;
    }
    this.shoppingCartService.getShoppingCart(userId).subscribe({
      next: (cart) => {
        this.shoppingCart = cart;
      },
      error: () => {
        this.errorMessage = 'Failed to load shopping cart';
      }
    });
  }

  removeProduct(event: any) {
    const productId = event?.productId ?? event; // fallback if event is just the ID
    const userId = this.authService.getRole();
    if (!userId) {
      this.errorMessage = 'User not authenticated';
      return;
    }
    this.shoppingCartService.removeItemFromCart(userId, productId).subscribe({
      next: () => {
        this.loadShoppingCart(); 
      },
      error: () => {
        this.errorMessage = 'Failed to remove product from cart';
      }
    });
  }
}
