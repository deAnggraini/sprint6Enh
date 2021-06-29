import { TestBed } from '@angular/core/testing';

import { StrukturService } from './struktur.service';

describe('StrukturService', () => {
  let service: StrukturService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StrukturService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
