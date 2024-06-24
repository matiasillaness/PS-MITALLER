import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalBrandComponent } from './modal-brand.component';

describe('ModalBrandComponent', () => {
  let component: ModalBrandComponent;
  let fixture: ComponentFixture<ModalBrandComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalBrandComponent]
    });
    fixture = TestBed.createComponent(ModalBrandComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
