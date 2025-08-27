import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { BackendService } from './backend-service';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private http = inject(HttpClient);
  private backendService = inject(BackendService);

  getAllProducts(): Observable<any> {
    const url = this.backendService.getProductUrl();
    return this.http.get(url);
  }

  getProductById(productId: string): Observable<Product> {
    const url = `${this.backendService.getProductUrl()}/${productId}`;
    return this.http.get<Product>(url);
  }

  addProduct(product: Product): Observable<any> {
    const url = this.backendService.getProductUrl();
    return this.http.post(url, product);
  }

  updateProduct(product: Product): Observable<any> {
    console.log('Updating product:', product);
    const url = `${this.backendService.getProductUrl()}/${product.id}`;
    return this.http.put(url, product);
  }

  getProductsByCategory(categoryId: string): Observable<any> {
    const url = `${this.backendService.getCategoryUrl()}/${categoryId}`;
    return this.http.get(url);
  }

  // addCategoryToProduct(productId: string, categoryId: string): Observable<any> {
  //   const url = `${this.backendService.getProductUrl()}/${productId}/category/${categoryId}`;
  //   return this.http.put(url, {});
  // }
}
