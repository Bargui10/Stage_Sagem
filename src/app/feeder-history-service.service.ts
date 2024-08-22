import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FeederHistory } from '../feeder-history.model';

@Injectable({
  providedIn: 'root'
})
export class FeederHistoryService {

  private baseUrl = '/api/feeder-history'; // L'URL de base de votre API

  constructor(private http: HttpClient) { }

  getAllFeederHistories(): Observable<FeederHistory[]> {
    return this.http.get<FeederHistory[]>(`${this.baseUrl}`);
  }

  createFeederHistory(feederHistory: FeederHistory): Observable<FeederHistory> {
    return this.http.post<FeederHistory>(`${this.baseUrl}`, feederHistory);
  }
}
