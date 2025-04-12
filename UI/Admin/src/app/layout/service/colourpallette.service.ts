import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColourPalletteService {

    themeColor: string = 'white';

    setThemeColor(color: string) {
        this.themeColor = color;
    }

    getThemeColor(): string {
      return this.themeColor;
    }

}