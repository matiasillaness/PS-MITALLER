import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalUserClientComponent } from './modal-user-client.component';

describe('ModalUserClientComponent', () => {
  let component: ModalUserClientComponent;
  let fixture: ComponentFixture<ModalUserClientComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalUserClientComponent]
    });
    fixture = TestBed.createComponent(ModalUserClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
