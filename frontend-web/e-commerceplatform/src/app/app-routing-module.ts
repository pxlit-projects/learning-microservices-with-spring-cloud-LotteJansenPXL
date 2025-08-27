import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login-component/login-component';
import { ProductListComponent } from './components/productCatalog/product-list-component/product-list-component';
import { AddProductComponent } from './components/productCatalog/add-product-component/add-product-component';
import { EditProductComponent } from './components/productCatalog/edit-product-component/edit-product-component';
import { ShoppingcartProductListComponent } from './components/shoppingCartComponents/shoppingcart-product-list-component/shoppingcart-product-list-component';
import { ShoppingcartComponent } from './components/shoppingCartComponents/shoppingcart-component/shoppingcart-component';
import { LogbookComponent } from './components/productCatalog/logbook-component/logbook-component';
import { WishlistComponent } from './components/shoppingCartComponents/wishlist-component/wishlist-component';

const routes: Routes = [
  { path: '', component: LoginComponent }, // default route
  { path: 'login', component: LoginComponent },
  { path: 'product-list', component: ProductListComponent },
  { path: 'add-product', component: AddProductComponent },
  { path: 'edit-product/:id', component: EditProductComponent },
  { path: 'shoppingcart-product-list', component: ShoppingcartProductListComponent },
  { path: 'shoppingcart', component: ShoppingcartComponent },
  { path: 'logbook', component: LogbookComponent },
  { path: 'wishlist', component: WishlistComponent },
  // ...other routes...
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
