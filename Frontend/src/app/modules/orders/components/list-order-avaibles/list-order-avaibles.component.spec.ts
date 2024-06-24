import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOrderAvaiblesComponent } from './list-order-avaibles.component';

describe('ListOrderAvaiblesComponent', () => {
  let component: ListOrderAvaiblesComponent;
  let fixture: ComponentFixture<ListOrderAvaiblesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListOrderAvaiblesComponent]
    });
    fixture = TestBed.createComponent(ListOrderAvaiblesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
