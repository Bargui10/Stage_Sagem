import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable, of, interval } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-data-time',
  templateUrl: './data-time.component.html',
  standalone: true,
  imports: [CommonModule],
  styleUrls: ['./data-time.component.css']
})
export class DataTimeComponent implements OnInit {
  currentDateTime: string | undefined;
  private apiUrl = 'http://localhost:8080/api/feeder-counts/current';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    this.refreshData();

    if (isPlatformBrowser(this.platformId)) {
      interval(60000) // Actualiser toutes les 60 secondes
        .pipe(
          switchMap(() => this.getCurrentDateTime())
        )
        .subscribe((dateTime: string) => {
          this.currentDateTime = dateTime;
          console.log('Nouvelle date/heure reçue:', dateTime);
        });
    }
  }

  refreshData(): void {
    this.getCurrentDateTime().subscribe((dateTime: string) => {
      console.log('Données initiales reçues:', dateTime);
      this.currentDateTime = dateTime;
    });
  }

  getCurrentDateTime(): Observable<string> {
    const urlWithTimestamp = `${this.apiUrl}?timestamp=${new Date().getTime()}`;
    console.log('Fetching new data from API:', urlWithTimestamp);
    
    return this.http.get(urlWithTimestamp, { responseType: 'text' }).pipe(
      catchError((error) => {
        console.error('Error fetching date/time', error);
        return of('Error fetching date/time');
      })
    );
  }
}
