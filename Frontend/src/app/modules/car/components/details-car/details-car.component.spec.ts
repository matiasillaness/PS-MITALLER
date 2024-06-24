import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsCarComponent } from './details-car.component';

describe('DetailsCarComponent', () => {
  let component: DetailsCarComponent;
  let fixture: ComponentFixture<DetailsCarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailsCarComponent]
    });
    fixture = TestBed.createComponent(DetailsCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
