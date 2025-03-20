import { TestBed } from '@angular/core/testing';

import { TenantinitalizerService } from './tenantinitalizer.service';

describe('TenantinitalizerService', () => {
  let service: TenantinitalizerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TenantinitalizerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
