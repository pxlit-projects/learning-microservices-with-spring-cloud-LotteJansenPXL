import { Component, inject, OnInit } from '@angular/core';
import { ProductService } from '../../../services/product-service';
import { Router } from '@angular/router';
import { Product } from '../../../models/product';
import { AuthService } from '../../../services/auth-service';
import { ShoppingcartService } from '../../../services/shoppingcart-service';
import { Shoppingcart } from '../../../models/shoppingcart';
import { WishlistService } from '../../../services/wishlist-service';
import { Wishlist } from '../../../models/wishlist';

@Component({
  selector: 'app-shoppingcart-product-list-component',
  standalone: false,
  templateUrl: './shoppingcart-product-list-component.html',
  styleUrls: ['./shoppingcart-product-list-component.css']
})
export class ShoppingcartProductListComponent implements OnInit {
  private productCatalogService = inject(ProductService);
  private shoppingCartService = inject(ShoppingcartService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private wishlistService = inject(WishlistService);

  products: Product[] = [];
  filteredProducts: Product[] = [];
  categories: string[] = [];
  selectedCategory: string = '';
  maxPrice: number | null = null;
  searchText: string = '';
  shoppingCart?: Shoppingcart;
  wishlist?: Wishlist;
  userId: string | null = null;

  ngOnInit(): void {
    this.userId = this.authService.getRole();
    if (!this.userId) {
      console.error('User not logged in');
      return;
    }
    this.loadProducts();
    this.loadShoppingCart(this.userId);
    this.loadWishlist(this.userId);
  }

  loadProducts(): void {
    this.productCatalogService.getAllProducts().subscribe({
      next: (data: Product[]) => {
        this.categories = Array.from(
          new Set(
            data.map(p => p.category?.name ?? '').filter(cat => !!cat)
          )
        );
        this.products = data;
        this.applyFilters();
      },
      error: (error: any) => console.error('Error loading products:', error)
    });
  }

  loadShoppingCart(userId: string): void {
    this.shoppingCartService.getShoppingCart(userId).subscribe({
      next: (cart: Shoppingcart) => {
        this.shoppingCart = cart;
      },
      error: (error) => console.error('Error loading shopping cart:', error)
    });
  }

  loadWishlist(userId: string): void {
    this.wishlistService.getWishlist(userId).subscribe({
      next: (wishlist: Wishlist) => {
        this.wishlist = wishlist;
      },
      error: (error: any) => console.error('Error loading wishlist:', error)
    });
  }

  openCart(): void {
    this.router.navigate(['/shoppingcart']);
  }

  openWishlist(): void {
    this.router.navigate(['/wishlist']);
  }

  applyFilters(): void {
    const searchLower = this.searchText.toLowerCase();

    this.filteredProducts = this.products.filter(product => {
      const productCategory = product.category?.name ?? '';
      const matchesCategory = this.selectedCategory ? productCategory === this.selectedCategory : true;
      const matchesPrice = this.maxPrice ? product.price <= this.maxPrice : true;
      const matchesSearch = this.searchText ? product.name.toLowerCase().includes(searchLower) : true;

      return matchesCategory && matchesPrice && matchesSearch;
    });
  }

  resetFilters(): void {
    this.selectedCategory = '';
    this.maxPrice = null;
    this.searchText = '';
    this.applyFilters();
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
