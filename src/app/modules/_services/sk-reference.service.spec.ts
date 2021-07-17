import { TestBed } from '@angular/core/testing';

import { SkReferenceService } from './sk-reference.service';

describe('SkReferenceService', () => {
  let service: SkReferenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SkReferenceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
