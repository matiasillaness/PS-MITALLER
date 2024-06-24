import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiSupplierComponent } from './ui-supplier.component';

describe('UiSupplierComponent', () => {
  let component: UiSupplierComponent;
  let fixture: ComponentFixture<UiSupplierComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UiSupplierComponent]
    });
    fixture = TestBed.createComponent(UiSupplierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
