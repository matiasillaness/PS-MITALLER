import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiBuyComponent } from './ui-buy.component';

describe('UiBuyComponent', () => {
  let component: UiBuyComponent;
  let fixture: ComponentFixture<UiBuyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UiBuyComponent]
    });
    fixture = TestBed.createComponent(UiBuyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
