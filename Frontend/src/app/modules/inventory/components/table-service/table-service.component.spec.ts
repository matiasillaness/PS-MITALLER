import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableServiceComponent } from './table-service.component';

describe('TableServiceComponent', () => {
  let component: TableServiceComponent;
  let fixture: ComponentFixture<TableServiceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableServiceComponent]
    });
    fixture = TestBed.createComponent(TableServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
