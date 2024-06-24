import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterPaymentComponent } from './register-payment.component';

describe('RegisterPaymentComponent', () => {
  let component: RegisterPaymentComponent;
  let fixture: ComponentFixture<RegisterPaymentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterPaymentComponent]
    });
    fixture = TestBed.createComponent(RegisterPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
