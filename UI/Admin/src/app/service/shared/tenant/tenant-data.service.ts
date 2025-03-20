import { Injectable } from '@angular/core';
import { Tenant } from '../../../model/tenant';

@Injectable({
  providedIn: 'root'
})
export class TenantDataService {

    tenant!: Tenant;
  
    constructor() { }
  
    setCurrentTenant(tnt: Tenant){
      this.tenant = tnt;
    }
  
    getCurrentTenant(){
      return this.tenant;
    }
    
}
