import { TestBed } from '@angular/core/testing';

import { FeederCountsService } from './feeder-counts-service.service';

describe('FeederCountsServiceService', () => {
  let service: FeederCountsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeederCountsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
