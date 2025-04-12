import { Entity } from "./entity";

export class Role extends Entity {
    rolename!: string;
    selected!: boolean;
    permissions: any[] = [];
}