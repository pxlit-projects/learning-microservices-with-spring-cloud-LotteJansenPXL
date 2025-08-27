import { Product } from "./product";

export class ShoppingCartProduct {
  id?: number;
  productId: number;
  product: Product;

  constructor(productId: number, product: Product, id?: number) {
    this.id = id;
    this.productId = productId;
    this.product = product;
  }
}