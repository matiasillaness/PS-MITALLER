import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterBrandCarComponent } from './register-brand-car.component';

describe('RegisterBrandCarComponent', () => {
  let component: RegisterBrandCarComponent;
  let fixture: ComponentFixture<RegisterBrandCarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterBrandCarComponent]
    });
    fixture = TestBed.createComponent(RegisterBrandCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
