import { Entity } from "./entity";

export class Tenant extends Entity {
    name!: string;
    parent!: number;
    timezone!: string;
    locale!: string;
    tenantDetail!: TenantDetails;
}

export class TenantDetails extends Entity {
    tagline!: string;
    contact!: string;
    emailid!: string;
    street!: string;
    city!: string;
    pincode!: string;
    businessemail!: string;
}