import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { Chart, ChartModule } from 'angular-highcharts';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Observable, of, interval } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-PerfByShift',
  standalone: true,
  imports: [ChartModule, CommonModule],
  templateUrl: './PerfByShift.component.html',
  styleUrls: ['./PerfByShift.component.css']
})
export class PerfByShiftComponent implements OnInit {

  chart: Chart | undefined;
  private apiUrl = 'http://localhost:8080/api/feeder-counts/performance-current-shift-hourly';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
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
    return this.http.get<{ [key: string]: number }>(this.apiUrl)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          console.error('Error fetching performance data', error);
          return of({});
        }),
        switchMap(data => {
          console.log('Raw data:', data);

          const currentShift = this.getCurrentShift();
          const [startHour, endHour] = this.getShiftHours(currentShift);
          console.log('Current Shift:', currentShift);
          console.log('Shift Interval:', startHour, endHour);

          const categories = this.generateHourLabels(startHour, endHour);
          console.log('Categories:', categories);

          const seriesData = this.getHourlyData(data, categories);
          console.log('Series data:', seriesData);

          this.chart = new Chart({
            chart: {
              type: 'column',
              height: 380
            },
            title: {
              text: `Performance Rate by ${currentShift}`
            },
            xAxis: {
              categories: categories,
              title: {
                text: 'Time of the Day'
              },
              tickInterval: 1,
              labels: {
                formatter: function (): string {
                  return this.value as string;
                }
              }
            },
            yAxis: {
              title: {
                text: 'Percentage (%)',
              },
              labels: {
                format: '{value:.2f}'
              },
              min: 0.0,
              max: 0.50,
              tickInterval: 0.01
            },
            series: [
              {
                type: 'column',
                name: currentShift,
                data: seriesData,
                color: this.getShiftColor(currentShift),
                dataLabels: {
                  enabled: true,
                  format: '{y:.2f}'
                }
              }
            ],
            credits: {
              enabled: false
            }
          });

          return of();
        })
      );
  }

  private getCurrentShift(): string {
    const now = new Date();
    const hour = now.getHours();

    if (hour >= 6 && hour < 14) {
      return 'Shift 1';
    } else if (hour >= 14 && hour < 22) {
      return 'Shift 2';
    } else {
      return 'Shift 3';
    }
  }

  private getShiftHours(shift: string): [number, number] {
    switch (shift) {
      case 'Shift 1':
        return [6, 14];
      case 'Shift 2':
        return [14, 22];
      case 'Shift 3':
        return [22, 6];
      default:
        return [0, 24];
    }
  }

  private generateHourLabels(startHour: number, endHour: number): string[] {
    const labels = [];
    
    if (startHour > endHour) {
      for (let i = startHour; i < 24; i++) {
        labels.push(`${i.toString().padStart(2, '0')}:00`);
      }
      for (let i = 0; i <= endHour; i++) {
        labels.push(`${i.toString().padStart(2, '0')}:00`);
      }
    } else {
      for (let i = startHour; i <= endHour; i++) {
        labels.push(`${i.toString().padStart(2, '0')}:00`);
      }
    }
    
    return labels;
  }

  private getHourlyData(data: { [key: string]: number }, categories: string[]): number[] {
    return categories.map(hour => data[hour] || 0);
  }

  private getShiftColor(shift: string): string {
    switch (shift) {
      case 'Shift 1':
        return '#003366'; 
      case 'Shift 2':
        return '#92b2d8'; 
      case 'Shift 3':
        return '#B0E0E6';
      default:
        return '#000000'; 
    }
  }
}
