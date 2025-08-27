import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BackendService } from './backend-service';
import { tap } from 'rxjs/operators'; // <-- Add this import
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private role: string | null = localStorage.getItem('role'); // ✅ load role on service init
  private http = inject(HttpClient);
  private backendService = inject(BackendService);

  constructor() {}

  login(username: string, password: string): Observable<{ role: string }> {
    // 🔹 Mocked login
    if (username === 'admin' && password === 'admin') {
      this.setRole('ADMIN');
      return of({ role: 'ADMIN' });
    } else if (username === 'user' && password === 'user') {
      this.setRole('USER');
      return of({ role: 'USER' });
    } else {
      return of({ role: 'INVALID' });
    }
  }

  getData(): Observable<string> {
    const role = this.getRole();
    const headers = new HttpHeaders().set('X-ROLE', role || '');
    const url = this.backendService.getGatewayUrl();

    return this.http.get(`${url}/auth/data`, { headers, responseType: 'text' });
  }

  logout(): void {
    this.role = null;
    localStorage.removeItem('role');
  }

  setRole(role: string) {
    this.role = role;
    localStorage.setItem('role', role); // ✅ persist role
  }

  getRole(): string | null {
    return this.role;
  }

  isLoggedIn(): boolean {
    return this.getRole() !== null;
  }
}