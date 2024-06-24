import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccessDenieComponent } from './access-denie.component';

describe('AccessDenieComponent', () => {
  let component: AccessDenieComponent;
  let fixture: ComponentFixture<AccessDenieComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccessDenieComponent]
    });
    fixture = TestBed.createComponent(AccessDenieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
