package com.platform.util;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.platform.messages.GenericResponse;
import com.platform.server.BaseSession;

import reactor.core.publisher.Mono;

/**
 * @author Muhil Util methods to make Http call using io reactive
 */
@Component
@ConditionalOnProperty(prefix = "platform.api", value = "reactive", havingValue = "true")
public class HttpUtilReactive {

	private static WebClient webClient;

	@Autowired
	public void setWebClient(WebClient webClient) {
		HttpUtilReactive.webClient = webClient;
	}

	public static Mono<GenericResponse> getMonoGenericResponse(String httpUrl, Class<?> responseDataCls) {
		return getMonoGenericResponse(httpUrl, null, responseDataCls);
	}

	/**
	 * @param httpUrl
	 * @param responseCls (return type of api response)
	 * @return Mono<cls>
	 */
	public static Mono<?> getMono(String httpUrl, Class<?> responseCls) {
		return getMono(httpUrl, null, responseCls);
	}

	public static Mono<GenericResponse> getMonoGenericResponse(String httpUrl, MultiValueMap<String, String> headers,
			Class<?> dataClass) {
		headers = (headers != null) ? headers : new LinkedMultiValueMap<>();
		headers.computeIfAbsent(HttpHeaders.AUTHORIZATION,
				key -> Collections.singletonList(PlatformUtil.TOKEN_BEARER + BaseSession.getJwttoken()));
		headers.computeIfAbsent(PlatformUtil.TENANT_HEADER,
				key -> Collections.singletonList(BaseSession.getTenantUniqueName()));
		MultiValueMap<String, String> finalHeaders = headers;
		ParameterizedTypeReference<GenericResponse> typeRef = TypeReferenceUtil
				.createParameterizedTypeReference(GenericResponse.class, dataClass);
		return Mono.fromCallable(() -> httpUrl).flatMap(
				url -> webClient.get().uri(url).headers(h -> h.addAll(finalHeaders)).retrieve().bodyToMono(typeRef))
				// .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
				.onErrorStop();
	}

	/**
	 * @param httpUrl
	 * @param headers
	 * @param responseCls (return type of api response)
	 * @return Mono<cls>
	 */
	public static Mono<?> getMono(String httpUrl, MultiValueMap<String, String> headers, Class<?> responseCls) {
		headers = (headers != null) ? headers : new LinkedMultiValueMap<>();
		headers.computeIfAbsent(HttpHeaders.AUTHORIZATION,
				key -> Collections.singletonList(PlatformUtil.TOKEN_BEARER + BaseSession.getJwttoken()));
		headers.computeIfAbsent(PlatformUtil.TENANT_HEADER,
				key -> Collections.singletonList(BaseSession.getTenantUniqueName()));
		MultiValueMap<String, String> finalHeaders = headers;
		return Mono.fromCallable(() -> httpUrl).flatMap(
				url -> webClient.get().uri(url).headers(h -> h.addAll(finalHeaders)).retrieve().bodyToMono(responseCls))
				// .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
				.onErrorStop();
	}

}
