import { TestBed } from '@angular/core/testing';

import { InformsService } from './informs.service';

describe('InformsService', () => {
  let service: InformsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InformsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
