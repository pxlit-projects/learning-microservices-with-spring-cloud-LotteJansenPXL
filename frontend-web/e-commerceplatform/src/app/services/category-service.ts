import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BackendService } from './backend-service';
import { Observable } from 'rxjs';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private http = inject(HttpClient);
  private backendService = inject(BackendService);

  getAllCategories(): Observable<Category[]> {
    const url = this.backendService.getCategoryUrl();
    return this.http.get<Category[]>(url);
  }

  getCategoryById(categoryId: string): Observable<Category> {
    const url = `${this.backendService.getCategoryUrl()}/${categoryId}`;
    return this.http.get<Category>(url);
  }

  addCategory(category: any): Observable<Category> {
    const url = this.backendService.getCategoryUrl();
    return this.http.post<Category>(url, category);
  }

  updateCategory(category: any): Observable<Category> {
    const url = `${this.backendService.getCategoryUrl()}/${category.id}`;
    return this.http.put<Category>(url, category);
  }

}
