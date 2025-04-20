package com.platform.recaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.platform.exceptions.ReCaptchaException;
import com.platform.logging.Log;
import com.platform.model.RecaptchaResponse;

/**
 * @author Muhil 
 * Google recaptcha verification service.
 */
@Service
public class ReCaptchaService {

	private static final String KEY_SECRET = "secret";
	private static final String KEY_RESPONSE = "response";

	private final RestTemplate restTemplate;

	@Autowired
	private ReCaptchaConfiguration config;

	public ReCaptchaService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public boolean verify(String response) throws ReCaptchaException {
		if (!config.isEnabled()) {
			Log.platform.warn("Recaptcha Not Enabled! Verification skipped.");
			return true;
		}
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add(KEY_SECRET, config.getSecret());
		param.add(KEY_RESPONSE, response);
		RecaptchaResponse recaptchaResponse = null;
		try {
			recaptchaResponse = this.restTemplate.postForObject(config.getVerifyUrl(), param, RecaptchaResponse.class);
		} catch (RestClientException e) {
			Log.platform.error("Captcha verification exception - {}", e);
			throw new ReCaptchaException(e.getMessage());
		}
		return (recaptchaResponse != null && recaptchaResponse.isSuccess());
	}

}