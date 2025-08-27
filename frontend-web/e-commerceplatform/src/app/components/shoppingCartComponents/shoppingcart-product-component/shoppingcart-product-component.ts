import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Product } from '../../../models/product';
import { ShoppingCartProduct } from '../../../models/shoppingcart-product';
import { ShoppingcartService } from '../../../services/shoppingcart-service';
import { AuthService } from '../../../services/auth-service';
import { WishlistService } from '../../../services/wishlist-service';
import { Shoppingcart } from '../../../models/shoppingcart';
import { Wishlist } from '../../../models/wishlist';

@Component({
  selector: 'app-shoppingcart-product-component',
  standalone: false,
  templateUrl: './shoppingcart-product-component.html',
  styleUrl: './shoppingcart-product-component.css'
})
//this component is for showing the products in the overview, not in the shoppingcart itself
export class ShoppingcartProductComponent {
  @Input() product!: Product;
  @Input() shoppingCart?: Shoppingcart;
  @Input() wishlist?: Wishlist;

  private shoppingCartService = inject(ShoppingcartService);
  private authService = inject(AuthService);
  private wishlistService = inject(WishlistService);

  userId: string | null = null;

  ngOnInit() {
    this.userId = this.authService.getRole();
    if (!this.userId) {
      throw new Error('User not logged in');
    }
  }

  addToCart() {
    if (!this.userId) throw new Error('User not logged in');
    if (!this.shoppingCart || this.shoppingCart.id == null) {
      throw new Error('Shopping cart not loaded');
    }

    const shoppingCartProduct: ShoppingCartProduct = {
      productId: this.product.id,
      product: this.product
    };

    this.shoppingCartService.addItemToCart(this.userId, shoppingCartProduct, this.shoppingCart.id).subscribe({
      next: () => console.log('Product added to cart successfully'),
      error: (err) => console.error('Error adding product to cart:', err)
    });
  }

  addToWishlist() {
    if (!this.userId) throw new Error('User not logged in');
    if (!this.wishlist || this.wishlist.id == null) {
      throw new Error('Wishlist not loaded');
    }
    const shoppingCartProduct: ShoppingCartProduct = {
      productId: this.product.id,
      product: this.product
    };
    this.wishlistService.addItemToWishlist(this.wishlist.id, shoppingCartProduct).subscribe({
      next: () => console.log('Product added to wishlist successfully'),
      error: (err) => console.error('Error adding product to wishlist:', err)
    });
  }
}
