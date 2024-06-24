import { TestBed } from '@angular/core/testing';

import { BrandCarService } from './brand-car.service';

describe('BrandCarService', () => {
  let service: BrandCarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BrandCarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
