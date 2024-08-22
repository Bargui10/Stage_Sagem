import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FeederCountsService {

  private baseUrl = '/api/feeder-counts'; 

  constructor(private http: HttpClient) { }

  getPerformanceRate(): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/performance-rate`);
  }

  getPerformance2(): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/performance2`);
  }

  getTotalErrors(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/total-errors`);
  }

  getPickUpMiss(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/PickUpMiss`);
  }

  getRecognition(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/Recognition`);
  }
}
