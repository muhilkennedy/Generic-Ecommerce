import { Component } from '@angular/core';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ColourPalletteService } from '../../layout/service/colourpallette.service';

@Component({
    selector: 'loader',
    standalone: true,
    imports: [ NgxSpinnerModule ],
    template: `
        <ngx-spinner [color]="themeService.getThemeColor()" type='ball-clip-rotate-multiple'/>
    `
})
export class SpinnerComponent {

    constructor(private spinner: NgxSpinnerService, public themeService: ColourPalletteService) {}

    show(){
        this.spinner.show();
    }

    hide(){
        this.spinner.hide();
    }

}
