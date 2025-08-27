import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from '../../../services/category-service';
import { Category } from '../../../models/category';

@Component({
  selector: 'app-add-category-component',
  standalone: false,
  templateUrl: './add-category-component.html',
  styleUrl: './add-category-component.css'
})
export class AddCategoryComponent {
  private dialogRef = inject(MatDialogRef<AddCategoryComponent>);
  private fb = inject(FormBuilder);
  private categoryService = inject(CategoryService);

  form: FormGroup;

  constructor() {
    this.form = this.fb.group({
      name: ['', Validators.required]
    });
  }

  onSave() {
    if (this.form.valid) {
      const categoryName = this.form.value.name;
      this.categoryService.addCategory(new Category(categoryName)).subscribe({
        next: () => this.dialogRef.close(categoryName),
        error: (err) => console.error('Error adding category:', err)
      });
    }
  }

  onCancel() {
    this.dialogRef.close();
  }
}
