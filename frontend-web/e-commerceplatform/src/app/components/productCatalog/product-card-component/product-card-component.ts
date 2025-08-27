import { Component, Input } from '@angular/core';
import { Product } from '../../../models/product';

@Component({
  selector: 'app-product-card-component',
  standalone: false,
  templateUrl: './product-card-component.html',
  styleUrl: './product-card-component.css'
})
export class ProductCardComponent {
  @Input() product!: Product;
}
