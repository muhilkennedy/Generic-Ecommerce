package com.platform.i18n.configuration;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.platform.logging.Log;
import com.platform.server.BaseSession;


/**
 * @author Muhil
 *
 */
@Component
public class LocaleInterceptor implements WebRequestInterceptor {

	@Override
	public void preHandle(WebRequest request) throws Exception {
		String lang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
		if (StringUtils.isNotBlank(lang) && BaseSession.getLocale() == null) {
			try {
				BaseSession.setLocale(lang);
			}
			catch(RuntimeException ex) { // Not a good idea, deal later to resolve this
				Log.i18n.error("Error setting up locale", ex);
				BaseSession.setLocale(Locale.ENGLISH);
			}
		}
		else {
			Log.i18n.debug("No Locale information found in request");
		}
		// Incase of no locale we rollback to en locale.
		if (BaseSession.getLocale() == null) {
			Log.i18n.debug("setting up default locale");
			BaseSession.setLocale(Locale.ENGLISH);
		}
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		//No-OP
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		Log.i18n.debug("Base Session cleaned : {}", request.toString());
		BaseSession.tearDownSession();
	}

}
