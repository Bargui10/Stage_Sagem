import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, interval } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { CommonModule, isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-Top3',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './Top3.component.html',
  styleUrls: ['./Top3.component.css']
})
export class Top3Component implements OnInit {
  topPerformances: { expectedPn: string, rejectionRate: number }[] = [];
  topPerformancesByValue: { expectedPn: string, price: number }[] = [];
  error: any;

  private topPerformancesApiUrl = 'http://localhost:8080/api/feeder-history/top-3-rejection';
  private topPerformancesByValueApiUrl = 'http://localhost:8080/api/feeder-history/top3-rejected-by-price';

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: Object) { }

  ngOnInit(): void {
    this.refreshData();

    if (isPlatformBrowser(this.platformId)) {
      interval(60000) 
        .pipe(
          switchMap(() => this.fetchTopPerformances())
        )
        .subscribe({
          next: (data) => {
            this.topPerformances = data;
            console.log('Updated top performances:', this.topPerformances);
          },
          error: (error) => {
            console.error('Error fetching top performances:', error);
          }
        });

      interval(60000) 
        .pipe(
          switchMap(() => this.fetchTopPerformancesByValue())
        )
        .subscribe({
          next: (data) => {
            this.topPerformancesByValue = data;
            console.log('Updated top performances by value:', this.topPerformancesByValue);
          },
          error: (error) => {
            console.error('Error fetching top performances by value:', error);
          }
        });
    }
  }

  refreshData(): void {
    this.fetchTopPerformances().subscribe({
      next: (data) => {
        this.topPerformances = data;
        console.log('Initial top performances:', this.topPerformances);
      },
      error: (error) => {
        console.error('Error fetching initial top performances:', error);
      }
    });

    this.fetchTopPerformancesByValue().subscribe({
      next: (data) => {
        this.topPerformancesByValue = data;
        console.log('Initial top performances by value:', this.topPerformancesByValue);
      },
      error: (error) => {
        console.error('Error fetching initial top performances by value:', error);
      }
    });
  }

  fetchTopPerformances(): Observable<{ expectedPn: string, rejectionRate: number }[]> {
    const urlWithTimestamp = `${this.topPerformancesApiUrl}?timestamp=${new Date().getTime()}`;
    console.log('Fetching new data from API:', urlWithTimestamp);

    return this.http.get<{ expectedPn: string, rejectionRate: number }[]>(urlWithTimestamp).pipe(
      catchError((error) => {
        this.error = error;
        console.error('Error fetching top performances:', error);
        return of([]);
      })
    );
  }

  fetchTopPerformancesByValue(): Observable<{ expectedPn: string, price: number }[]> {
    const urlWithTimestamp = `${this.topPerformancesByValueApiUrl}?timestamp=${new Date().getTime()}`;
    console.log('Fetching new data from API:', urlWithTimestamp);

    return this.http.get<{ expectedPn: string, price: number }[]>(urlWithTimestamp).pipe(
      catchError((error) => {
        this.error = error;
        console.error('Error fetching top performances by value:', error);
        return of([]);
      })
    );
  }
}
