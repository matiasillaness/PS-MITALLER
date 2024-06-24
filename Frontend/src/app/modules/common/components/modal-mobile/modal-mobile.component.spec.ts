import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalMobileComponent } from './modal-mobile.component';

describe('ModalMobileComponent', () => {
  let component: ModalMobileComponent;
  let fixture: ComponentFixture<ModalMobileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalMobileComponent]
    });
    fixture = TestBed.createComponent(ModalMobileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
