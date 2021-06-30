import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PakarSearchComponent } from './pakar-search.component';

describe('PakarSearchComponent', () => {
  let component: PakarSearchComponent;
  let fixture: ComponentFixture<PakarSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PakarSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PakarSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
