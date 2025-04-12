package com.aggregator.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.ValidateUserToken;

/**
 * @author Muhil
 */
@RestController
@RequestMapping("audit")
@ValidateUserToken
public class AuditController {

	/*@Autowired
	TenantServiceMSImpl tenantService;

	@GetMapping(value = "/ping")
	public Mono<String> ping() {
		return Mono.just("hell");
	}

	@GetMapping(value = "/ping1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<?> ping1() {
		return Flux.just(tenantService.findAll());
	}*/

}
