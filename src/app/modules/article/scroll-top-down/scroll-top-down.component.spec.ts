import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScrollTopDownComponent } from './scroll-top-down.component';

describe('ScrollTopDownComponent', () => {
  let component: ScrollTopDownComponent;
  let fixture: ComponentFixture<ScrollTopDownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScrollTopDownComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScrollTopDownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
