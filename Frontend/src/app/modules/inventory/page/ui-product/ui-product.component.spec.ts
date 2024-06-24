import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiProductComponent } from './ui-product.component';

describe('UiProductComponent', () => {
  let component: UiProductComponent;
  let fixture: ComponentFixture<UiProductComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UiProductComponent]
    });
    fixture = TestBed.createComponent(UiProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
