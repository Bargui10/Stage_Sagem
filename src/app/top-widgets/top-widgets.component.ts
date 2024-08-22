import { Component, OnInit, Inject, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, interval } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faChartLine, faPeopleGroup } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-top-widgets',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './top-widgets.component.html',
  styleUrls: ['./top-widgets.component.scss'],
})
export class TopWidgetsComponent implements OnInit {
  faChartLine = faChartLine;
  faPeopleGroup = faPeopleGroup;

  performance: string = '0.00';
  previousShiftPerformance: string = '0.00';
  previousShiftName: string = 'Unknown';
  error: any;

  private performanceApiUrl = 'http://localhost:8080/api/feeder-counts/performance2';
  private previousShiftApiUrl = 'http://localhost:8080/api/feeder-counts/performance-previous-shift';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadData();

    if (isPlatformBrowser(this.platformId)) {
      interval(60000)
        .pipe(
          switchMap(() => this.loadData())
        )
        .subscribe();
    }
  }

  private loadData(): Observable<void> {
    return this.fetchPerformanceData().pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Error fetching performance data', error);
        this.error = error;
        return of(); 
      })
    );
  }

  private fetchPerformanceData(): Observable<void> {
    return this.http.get<number>(this.performanceApiUrl).pipe(
      switchMap(performance => {
        console.log('Fetched performance:', performance);
        return this.http.get<Record<string, number>>(this.previousShiftApiUrl).pipe(
          catchError(error => {
            this.error = error;
            console.error('Error fetching previous shift data:', error);
            return of({});
          }),
          switchMap(previousShift => {
            console.log('Fetched previous shift data:', previousShift);
            this.updatePerformanceData(performance, previousShift);
            return of();
          })
        );
      }),
      catchError(error => {
        this.error = error;
        console.error('Error fetching performance data:', error);
        return of();
      })
    );
  }

  private updatePerformanceData(performance: number, previousShift: Record<string, number>): void {
    this.performance = performance.toFixed(2);

    const shiftEntries = Object.entries(previousShift);
    if (shiftEntries.length > 0) {
      const [shiftName, shiftPerformance] = shiftEntries[0];
      this.previousShiftName = shiftName;
      this.previousShiftPerformance = shiftPerformance.toFixed(2);
    } else {
      this.previousShiftName = 'Unknown';
      this.previousShiftPerformance = '0.00';
    }

    console.log('Updated performance data:', {
      performance: this.performance,
      previousShiftName: this.previousShiftName,
      previousShiftPerformance: this.previousShiftPerformance,
    });

    this.cdr.detectChanges();
  }
}
