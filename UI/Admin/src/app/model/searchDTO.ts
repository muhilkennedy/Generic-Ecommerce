import { Entity } from "./entity";

export class SearchDTO {
    field!: string;
    matchMode!: string;
    operator!: string;
    value!: any;

    constructor(field: string, matchMode: string, operator: string, value: any) {
        this.field = field;
        this.matchMode = matchMode;
        this.operator = operator;
        this.value = value;
    }
}