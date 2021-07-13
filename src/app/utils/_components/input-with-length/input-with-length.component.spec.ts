import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputWithLengthComponent } from './input-with-length.component';

describe('InputWithLengthComponent', () => {
  let component: InputWithLengthComponent;
  let fixture: ComponentFixture<InputWithLengthComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputWithLengthComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InputWithLengthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
