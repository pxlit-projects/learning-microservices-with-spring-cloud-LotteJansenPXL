import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { ProductListComponent } from './components/productCatalog/product-list-component/product-list-component';
import { provideHttpClient } from '@angular/common/http';
import { ProductCardComponent } from './components/productCatalog/product-card-component/product-card-component';
import { LoginComponent } from './components/login-component/login-component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddProductComponent } from './components/productCatalog/add-product-component/add-product-component';
import { EditProductComponent } from './components/productCatalog/edit-product-component/edit-product-component';
import { AddCategoryComponent } from './components/productCatalog/add-category-component/add-category-component';
import { ShoppingcartProductListComponent } from './components/shoppingCartComponents/shoppingcart-product-list-component/shoppingcart-product-list-component';
import { ShoppingcartProductComponent } from './components/shoppingCartComponents/shoppingcart-product-component/shoppingcart-product-component';
import { ShoppingcartComponent } from './components/shoppingCartComponents/shoppingcart-component/shoppingcart-component';
import { ShoppingCartItem } from './components/shoppingCartComponents/shopping-cart-item/shopping-cart-item';
import { LogbookComponent } from './components/productCatalog/logbook-component/logbook-component';
import { WishlistComponent } from './components/shoppingCartComponents/wishlist-component/wishlist-component';
@NgModule({
  declarations: [
    App,
    ProductListComponent,
    ProductCardComponent,
    LoginComponent,
    AddProductComponent,
    EditProductComponent,
    AddCategoryComponent,
    ShoppingcartProductListComponent,
    ShoppingcartProductComponent,
    ShoppingcartComponent,
    ShoppingCartItem,
    LogbookComponent,
    WishlistComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient()
  ],
  bootstrap: [App]
})
export class AppModule { }
