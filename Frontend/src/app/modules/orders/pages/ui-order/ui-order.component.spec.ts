import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiOrderComponent } from './ui-order.component';

describe('UiOrderComponent', () => {
  let component: UiOrderComponent;
  let fixture: ComponentFixture<UiOrderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UiOrderComponent]
    });
    fixture = TestBed.createComponent(UiOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
