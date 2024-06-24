import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiServiceComponent } from './ui-service.component';

describe('UiServiceComponent', () => {
  let component: UiServiceComponent;
  let fixture: ComponentFixture<UiServiceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UiServiceComponent]
    });
    fixture = TestBed.createComponent(UiServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
