import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputDropDownComponent } from './input-drop-down.component';

describe('InputDropDownComponent', () => {
  let component: InputDropDownComponent;
  let fixture: ComponentFixture<InputDropDownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputDropDownComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InputDropDownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
