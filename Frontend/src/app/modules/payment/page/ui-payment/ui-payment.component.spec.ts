import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiPaymentComponent } from './ui-payment.component';

describe('UiPaymentComponent', () => {
  let component: UiPaymentComponent;
  let fixture: ComponentFixture<UiPaymentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UiPaymentComponent]
    });
    fixture = TestBed.createComponent(UiPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
