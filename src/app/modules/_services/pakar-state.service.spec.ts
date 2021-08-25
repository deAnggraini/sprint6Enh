import { TestBed } from '@angular/core/testing';

import { PakarStateService } from './pakar-state.service';

describe('PakarStateService', () => {
  let service: PakarStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PakarStateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
