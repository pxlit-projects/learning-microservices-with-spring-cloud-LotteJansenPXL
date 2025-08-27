import { Component, inject } from '@angular/core';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product-service';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-product-list-component',
  standalone: false,
  templateUrl: './product-list-component.html',
  styleUrl: './product-list-component.css'
})
export class ProductListComponent {
  private productCatalogService = inject(ProductService);
  private router = inject(Router);
  private authService = inject(AuthService);

  products: Product[] = [];

  constructor() {}

  ngOnInit(): void {
    this.loadProducts();
  }


  loadProducts(): void {
    this.productCatalogService.getAllProducts().subscribe({
      next: (data: Product[]) => {
        this.products = data;
      },
      error: (error) => {
        console.error('Error loading products:', error);
      }
    });
  }

  onAddProduct() {
    this.router.navigate(['/add-product']);
  }

  editProduct(productId: number) {
    this.router.navigate(['/edit-product', productId]);
  }

  onShowLogs() {
    this.router.navigate(['/logbook']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
