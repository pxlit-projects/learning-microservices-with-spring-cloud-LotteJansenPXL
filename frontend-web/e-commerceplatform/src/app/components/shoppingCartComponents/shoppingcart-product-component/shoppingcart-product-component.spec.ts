import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShoppingcartProductComponent } from './shoppingcart-product-component';

describe('ShoppingcartProductComponent', () => {
  let component: ShoppingcartProductComponent;
  let fixture: ComponentFixture<ShoppingcartProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShoppingcartProductComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShoppingcartProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
