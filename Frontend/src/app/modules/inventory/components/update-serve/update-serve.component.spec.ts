import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateServeComponent } from './update-serve.component';

describe('UpdateServeComponent', () => {
  let component: UpdateServeComponent;
  let fixture: ComponentFixture<UpdateServeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateServeComponent]
    });
    fixture = TestBed.createComponent(UpdateServeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
