import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BackendService } from './backend-service';
import { Log } from '../models/log';

@Injectable({
  providedIn: 'root'
})
export class LogbookService {
  private http = inject(HttpClient);
  private backendService = inject(BackendService);

  getLogs() {
    return this.http.get<Log[]>(this.backendService.getLogbookUrl());
  }
}
