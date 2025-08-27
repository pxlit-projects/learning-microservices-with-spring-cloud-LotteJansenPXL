import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product-service';
import { Router } from '@angular/router';
import { Category } from '../../../models/category';
import { CategoryService } from '../../../services/category-service';
import { MatDialog } from '@angular/material/dialog';
import { AddCategoryComponent } from '../add-category-component/add-category-component';

@Component({
  selector: 'app-add-product-component',
  standalone: false,
  templateUrl: './add-product-component.html',
  styleUrl: './add-product-component.css'
})
export class AddProductComponent {
  private productService = inject(ProductService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private categoryService = inject(CategoryService);
  private dialog = inject(MatDialog);


  productForm: FormGroup;
  categories: Category[] = [];

  constructor() {
    this.categoryService.getAllCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (err) => console.error('Error loading categories:', err)
    });

    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      price: [0, [Validators.required, Validators.min(0)]],
      category: [undefined, Validators.required]
    });
  }

  onSubmit() {
    if (this.productForm.valid) {
      const newProduct: Product = {
        id: 0, // or undefined depending on your model
        name: this.productForm.value.name,
        description: this.productForm.value.description,
        price: this.productForm.value.price,
        category: this.productForm.value.category // full category object
      };
      console.log('Adding product:', newProduct);
      this.productService.addProduct(newProduct).subscribe({
        next: () => this.router.navigate(['/product-list']),
        error: (err) => console.error('Error adding product:', err)
      });
    }
  }

  addCategory() {
      const dialogRef = this.dialog.open(AddCategoryComponent);
      dialogRef.afterClosed().subscribe((newCategory: Category) => {
  if (newCategory) {
    this.categories.push(newCategory);
  }
});
    }
}
