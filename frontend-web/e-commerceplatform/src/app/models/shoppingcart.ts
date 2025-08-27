import { Product } from "./product";
import { ShoppingCartProduct } from "./shoppingcart-product";

export class Shoppingcart {
    id: number;
    userId: string;
    totalPrice: number;
    checkedOut: boolean;
    products: ShoppingCartProduct[];

    constructor(id: number, userId: string, products: ShoppingCartProduct[], totalPrice: number, checkedOut: boolean) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.totalPrice = totalPrice;
        this.checkedOut = checkedOut;
    }
}
