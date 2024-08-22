import { TestBed } from '@angular/core/testing';

import { FeederHistoryService } from './feeder-history-service.service';

describe('FeederHistoryServiceService', () => {
  let service: FeederHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeederHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
