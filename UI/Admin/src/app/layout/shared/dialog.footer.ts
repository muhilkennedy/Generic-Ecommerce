import { Component } from '@angular/core';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { ButtonModule } from 'primeng/button';
import { TranslateModule } from '@ngx-translate/core';

@Component({
    selector: 'dialog-footer',
    standalone: true,
    imports: [ButtonModule, TranslateModule],
    template: `
        <div class="flex w-full justify-end mt-4">
            <p-button type="button" [label]="'Close' | translate" icon="pi pi-times" (click)="closeDialog({ buttonType: 'Cancel', summary: 'No Action' })" />
        </div> `
})
export class DialogFooter {

    constructor(public ref: DynamicDialogRef) { }

    closeDialog(data: any) {
        this.ref.close(data);
    }
}