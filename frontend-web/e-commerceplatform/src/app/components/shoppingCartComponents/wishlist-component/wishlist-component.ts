import { Component, inject } from '@angular/core';
import { WishlistService } from '../../../services/wishlist-service';
import { AuthService } from '../../../services/auth-service';
import { Product } from '../../../models/product';

@Component({
  selector: 'app-wishlist-component',
  standalone: false,
  templateUrl: './wishlist-component.html',
  styleUrl: './wishlist-component.css'
})
export class WishlistComponent {
private wishlistService = inject(WishlistService);
  private authService = inject(AuthService);
  wishlist: any;
  errorMessage: string = '';
  products: Product[] = [];

  ngOnInit() {
    this.loadWishlist();
  }

  private loadWishlist() {
    console.log('Loading wishlist...');
    const userId = this.authService.getRole();
    if (!userId) {
      this.errorMessage = 'User not authenticated';
      return;
    }
    this.wishlistService.getWishlist(userId).subscribe({
      next: (wishlist) => {
        this.wishlist = wishlist;
        console.log('Wishlist loaded:', this.wishlist);
      },
      error: () => {
        this.errorMessage = 'Failed to load wishlist';
      }
    });
  }

  removeProduct(event: any) {
    const productId = event?.productId ?? event; 
    const userId = this.authService.getRole();
    if (!userId) {
      this.errorMessage = 'User not authenticated';
      return;
    }
    this.wishlistService.removeItemFromWishlist(this.wishlist.id, productId).subscribe({
      next: () => {
        this.loadWishlist();
      },
      error: () => {
        this.errorMessage = 'Failed to remove product from wishlist';
      }
    });
  }
}
