import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsBuyComponent } from './details-buy.component';

describe('DetailsBuyComponent', () => {
  let component: DetailsBuyComponent;
  let fixture: ComponentFixture<DetailsBuyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailsBuyComponent]
    });
    fixture = TestBed.createComponent(DetailsBuyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
