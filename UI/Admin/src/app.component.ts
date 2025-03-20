import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerModule } from 'ngx-spinner';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterModule, NgxSpinnerModule],
    template: `<router-outlet></router-outlet>`
})
export class AppComponent {

  constructor(private translate: TranslateService) {
    
  }

    // constructor(
    //     //private primengConfig: PrimeNGConfig,
    //     private translate: TranslateService
    //   ) {
    //     this.translate.setDefaultLang('en');
    
    //     // Listen to language changes and update PrimeNG locale
    //     // this.translate.use('en').subscribe(() => {
    //     //   this.setPrimeNGLocale();
    //     // });
    //   }
    
      switchLanguage(lang: string) {
        // this.translate.use(lang).subscribe(() => {
        //   this.setPrimeNGLocale();
        // });
      }
    
    //   setPrimeNGLocale() {
    //     this.translate.get('primeng').subscribe((res) => {
    //       this.primengConfig.setTranslation(res);
    //     });
    //   }
      
}
