import { Entity } from "./entity";

export class FileStore extends Entity {
    acl!: boolean;
    clientfile!: boolean;
    storeId!: number;
    mediaurl!: string;
    fileExtention!: string;
    fileName!: string;
    size!: number;
}