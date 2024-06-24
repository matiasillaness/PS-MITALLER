import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiCarComponent } from './ui-car.component';

describe('UiCarComponent', () => {
  let component: UiCarComponent;
  let fixture: ComponentFixture<UiCarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UiCarComponent]
    });
    fixture = TestBed.createComponent(UiCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
