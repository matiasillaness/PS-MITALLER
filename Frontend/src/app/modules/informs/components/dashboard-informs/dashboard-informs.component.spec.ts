import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardInformsComponent } from './dashboard-informs.component';

describe('DashboardInformsComponent', () => {
  let component: DashboardInformsComponent;
  let fixture: ComponentFixture<DashboardInformsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardInformsComponent]
    });
    fixture = TestBed.createComponent(DashboardInformsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
