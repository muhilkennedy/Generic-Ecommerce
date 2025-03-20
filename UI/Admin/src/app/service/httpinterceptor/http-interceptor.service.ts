import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CookieService } from 'ngx-cookie-service';
import { CommonUtil } from '../../util/CommonUtil.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private cookieService: CookieService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let newHeaders = req.headers;
    // Append tenant-Id and token to all outgoing requests.
    if (environment.tenantId) {
      newHeaders = newHeaders.append('X-Tenant', environment.tenantId);
      newHeaders = newHeaders.append('Accept-Language', this.cookieService.get(CommonUtil.KEY_LOCALE)? this.cookieService.get(CommonUtil.KEY_LOCALE) : 'en');
      newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.cookieService.get(CommonUtil.KEY_TOKEN)!);
    }
    const authReq = req.clone({ headers: newHeaders });
    return next.handle(authReq);
  }
}
