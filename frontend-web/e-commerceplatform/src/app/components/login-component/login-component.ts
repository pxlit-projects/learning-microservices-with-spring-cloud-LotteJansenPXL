import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { ShoppingcartService } from '../../services/shoppingcart-service';
import { WishlistService } from '../../services/wishlist-service';

@Component({
  selector: 'app-login-component',
  standalone: false,
  templateUrl: './login-component.html',
  styleUrls: ['./login-component.css']
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';
  errorMessage = '';

  private authService = inject(AuthService);
  private router = inject(Router);
  private shoppingcartService = inject(ShoppingcartService);
  private wishlistService = inject(WishlistService);

  ngOnInit() {
    // Auto-redirect if user is already logged in
    const role = this.authService.getRole();
    if (role) {
      this.navigateByRole(role);
    }
  }

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: (res) => {
        if (res.role === 'ADMIN' || res.role === 'USER') {
          this.authService.setRole(res.role);
          this.navigateByRole(res.role);
        } else {
          this.errorMessage = 'Invalid login';
        }
      },
      error: () => {
        this.errorMessage = `Invalid username or password: ${this.username}`;
      }
    });
  }

  private navigateByRole(role: string) {
    if (role === 'ADMIN') {
      this.router.navigate(['/product-list']);
    } else if (role === 'USER') {
      this.shoppingcartService.createShoppingCart(role).subscribe({
        next: () => {
          this.wishlistService.createWishlist(role).subscribe({
            next: () => {
              this.router.navigate(['/shoppingcart-product-list']);
            },
            error: () => {
              this.errorMessage = 'Failed to create wishlist';
            }
          });
        },
        error: () => {
          this.errorMessage = 'Failed to create shopping cart';
        }
      });
    }
  }
}
