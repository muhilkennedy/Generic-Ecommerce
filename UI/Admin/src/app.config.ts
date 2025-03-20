import { HttpClient, provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom, inject, provideAppInitializer } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter, withEnabledBlockingInitialNavigation, withInMemoryScrolling } from '@angular/router';
import Aura from '@primeng/themes/aura';
import { PRIME_NG_CONFIG, providePrimeNG } from 'primeng/config';
import { appRoutes } from './app.routes';
import { TenantinitalizerService } from './app/service/appintializer/tenantinitalizer.service';
import { httpInterceptorProvider } from './app.module';
import { provideTranslateService, TranslateLoader } from '@ngx-translate/core';
import { TranslationLoader } from './app/service/translator/TranslationLoader.service';
import { CookieService } from 'ngx-cookie-service';
import { NgxSpinnerService, provideSpinnerConfig } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';

export const appConfig: ApplicationConfig = {
    providers: [
        provideRouter(appRoutes, withInMemoryScrolling({ anchorScrolling: 'enabled', scrollPositionRestoration: 'enabled' }), withEnabledBlockingInitialNavigation()),
        provideHttpClient(withInterceptorsFromDi()),
        provideAnimationsAsync(),
        providePrimeNG({ theme: { preset: Aura, options: { darkModeSelector: '.app-dark' } } }),
        provideAppInitializer(() => {
            return (inject(TenantinitalizerService)).initializeApp();
        }),
        importProvidersFrom(HttpClient),
        httpInterceptorProvider,
        provideTranslateService({
            loader: {
              provide: TranslateLoader,
              useFactory: (http: HttpClient) => new TranslationLoader(http),
              deps: [HttpClient],
            },
            defaultLanguage: 'en' // Default language
        }),
        CookieService,
        provideSpinnerConfig({ type: 'ball-clip-rotate-multiple' }),
        NgxSpinnerService,
        MessageService,
        DialogService
    ]
};
