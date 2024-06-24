import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsPaymentComponent } from './details-payment.component';

describe('DetailsPaymentComponent', () => {
  let component: DetailsPaymentComponent;
  let fixture: ComponentFixture<DetailsPaymentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailsPaymentComponent]
    });
    fixture = TestBed.createComponent(DetailsPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
