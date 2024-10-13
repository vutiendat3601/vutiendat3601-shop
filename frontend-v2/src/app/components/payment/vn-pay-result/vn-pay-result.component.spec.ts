import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VnPayResultComponent } from './vn-pay-result.component';

describe('VnPayResultComponent', () => {
  let component: VnPayResultComponent;
  let fixture: ComponentFixture<VnPayResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VnPayResultComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VnPayResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
