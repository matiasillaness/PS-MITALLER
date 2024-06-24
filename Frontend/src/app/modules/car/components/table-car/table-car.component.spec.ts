import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableCarComponent } from './table-car.component';

describe('TableCarComponent', () => {
  let component: TableCarComponent;
  let fixture: ComponentFixture<TableCarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableCarComponent]
    });
    fixture = TestBed.createComponent(TableCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
