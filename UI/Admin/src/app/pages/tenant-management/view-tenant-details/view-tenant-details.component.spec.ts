import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewTenantDetailsComponent } from './view-tenant-details.component';

describe('ViewTenantDetailsComponent', () => {
  let component: ViewTenantDetailsComponent;
  let fixture: ComponentFixture<ViewTenantDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewTenantDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewTenantDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
