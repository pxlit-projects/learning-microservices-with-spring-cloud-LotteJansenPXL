import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { LogbookService } from '../../../services/logbook-service';
import { Log } from '../../../models/log';

@Component({
  selector: 'app-logbook-component',
  standalone: false,
  templateUrl: './logbook-component.html',
  styleUrl: './logbook-component.css'
})
export class LogbookComponent {
  private router = inject(Router);
  private logbookService = inject(LogbookService);

  logs: Log[] = [];

  ngOnInit(): void {
    this.loadLogs();
  }

  loadLogs(): void {
    this.logbookService.getLogs().subscribe({
      next: (data: Log[]) => {
        this.logs = data;
      },
      error: (error) => {
        console.error('Error loading logs:', error);
      }
    });
  }

}
