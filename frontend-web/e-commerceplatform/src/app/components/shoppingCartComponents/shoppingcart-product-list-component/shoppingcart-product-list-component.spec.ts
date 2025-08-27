import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShoppingcartProductListComponent } from './shoppingcart-product-list-component';

describe('ShoppingcartProductListComponent', () => {
  let component: ShoppingcartProductListComponent;
  let fixture: ComponentFixture<ShoppingcartProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShoppingcartProductListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShoppingcartProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
