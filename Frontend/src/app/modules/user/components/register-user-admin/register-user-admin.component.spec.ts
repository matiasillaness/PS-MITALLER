import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterUserAdminComponent } from './register-user-admin.component';

describe('RegisterUserAdminComponent', () => {
  let component: RegisterUserAdminComponent;
  let fixture: ComponentFixture<RegisterUserAdminComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterUserAdminComponent]
    });
    fixture = TestBed.createComponent(RegisterUserAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
