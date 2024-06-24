import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiPanelAdminComponent } from './ui-panel-admin.component';

describe('UiPanelAdminComponent', () => {
  let component: UiPanelAdminComponent;
  let fixture: ComponentFixture<UiPanelAdminComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UiPanelAdminComponent]
    });
    fixture = TestBed.createComponent(UiPanelAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
