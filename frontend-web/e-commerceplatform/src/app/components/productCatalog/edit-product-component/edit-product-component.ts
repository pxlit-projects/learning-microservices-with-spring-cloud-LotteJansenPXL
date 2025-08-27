import { Component, inject } from '@angular/core';
import { Product } from '../../../models/product';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../../services/product-service';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../../models/category';
import { CategoryService } from '../../../services/category-service';
import { MatDialog } from '@angular/material/dialog';
import { AddCategoryComponent } from '../add-category-component/add-category-component';

@Component({
  selector: 'app-edit-product-component',
  standalone: false,
  templateUrl: './edit-product-component.html',
  styleUrl: './edit-product-component.css'
})
export class EditProductComponent {
  private productService = inject(ProductService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private categoryService = inject(CategoryService);
  private dialog = inject(MatDialog);

  product!: Product;
  productForm!: FormGroup;
  categories: Category[] = [];

  constructor() {
    this.categoryService.getAllCategories().subscribe({
      next: (categories) => this.categories = categories,
      error: (err) => console.error('Error loading categories:', err)
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productService.getProductById(id).subscribe({
        next: (product) => {
          this.product = product;
          this.productForm = this.fb.group({
            id: [product.id],
            name: [product.name, Validators.required],
            description: [product.description],
            price: [product.price, [Validators.required, Validators.min(0)]],
            category: [product.category || undefined, Validators.required] // send full category
          });
        },
        error: (err) => console.error('Error loading product:', err)
      });
    }
  }

  onSubmit() {
    if (this.productForm.valid) {
      const updatedProductRequest = {
        id: this.productForm.value.id,
        name: this.productForm.value.name,
        description: this.productForm.value.description,
        price: this.productForm.value.price,
        category: this.productForm.value.category || undefined
      };
      console.log('Updated product request:', updatedProductRequest);
      this.productService.updateProduct(updatedProductRequest).subscribe({
        next: () => this.router.navigate(['/product-list']),
        error: (err) => console.error('Error updating product:', err)
      });
    }
  }

  addCategory() {
    const dialogRef = this.dialog.open(AddCategoryComponent);
    dialogRef.afterClosed().subscribe((newCategory: Category) => {
      if (newCategory) this.categories.push(newCategory);
    });
  }
}
