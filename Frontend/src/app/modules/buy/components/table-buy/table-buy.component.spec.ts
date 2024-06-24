import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableBuyComponent } from './table-buy.component';

describe('TableBuyComponent', () => {
  let component: TableBuyComponent;
  let fixture: ComponentFixture<TableBuyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableBuyComponent]
    });
    fixture = TestBed.createComponent(TableBuyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
