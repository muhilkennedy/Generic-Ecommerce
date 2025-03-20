import { TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';

export class TranslationLoader implements TranslateLoader {
  constructor(private http: HttpClient) {}

  getTranslation(lang: string): Observable<any> {
    //return this.http.get(`assets/i18n/${lang}.json`);
    console.log(`Loading translations for: ${lang}`);
    return this.http.get(`assets/i18n/${lang}.json`).pipe(
      tap(data => console.log(`Loaded translations`)),
      catchError(error => {
        console.error('Error loading translations:', error);
        return of({});
      })
    );
  }
  
}