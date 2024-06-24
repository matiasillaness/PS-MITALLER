import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterPartialComponent } from './footer-partial.component';

describe('FooterPartialComponent', () => {
  let component: FooterPartialComponent;
  let fixture: ComponentFixture<FooterPartialComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FooterPartialComponent]
    });
    fixture = TestBed.createComponent(FooterPartialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
