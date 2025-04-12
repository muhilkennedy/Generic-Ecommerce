import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CookieService } from 'ngx-cookie-service';
import { CommonUtil } from '../../util/CommonUtil.service';
import { Router } from '@angular/router';
import { ToastMessageService } from '../toastmessage/toast-message.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private cookieService: CookieService, private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let newHeaders = req.headers;
    // Append tenant-Id and token to all outgoing requests.
    if (environment.tenantId) {
      newHeaders = newHeaders.append('X-Tenant', environment.tenantId);
      newHeaders = newHeaders.append('Accept-Language', this.cookieService.get(CommonUtil.KEY_LOCALE)? this.cookieService.get(CommonUtil.KEY_LOCALE) : 'en');
      newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.cookieService.get(CommonUtil.KEY_TOKEN)!);
    }
    const authReq = req.clone({ headers: newHeaders });
    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          console.error('Unauthorized request - Redirecting to login');
          // Handle 401 error - Redirect to login page or refresh token
          this.cookieService.deleteAll();
          //return throwError(() => new Error('Unauthorized - 401'));
        }
        // Pass other errors through
        return throwError(() => error);
      })
    );
  }
}
