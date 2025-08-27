import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { ShoppingCartProduct } from '../../../models/shoppingcart-product';
import { ShoppingcartService } from '../../../services/shoppingcart-service';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-shopping-cart-item',
  standalone: false,
  templateUrl: './shopping-cart-item.html',
  styleUrl: './shopping-cart-item.css'
})
export class ShoppingCartItem {
  @Input() product!: ShoppingCartProduct;
  private shoppingCartService = inject(ShoppingcartService);
  private authService = inject(AuthService);

 @Output() remove = new EventEmitter<number>();

 removeFromCart() {
    this.remove.emit(this.product.product.id);
  }
}

