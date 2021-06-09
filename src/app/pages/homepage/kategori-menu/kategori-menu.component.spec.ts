import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KategoriMenuComponent } from './kategori-menu.component';

describe('KategoriMenuComponent', () => {
  let component: KategoriMenuComponent;
  let fixture: ComponentFixture<KategoriMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KategoriMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(KategoriMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
