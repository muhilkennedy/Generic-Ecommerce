package com.tenant.api;

import java.io.IOException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.platform.messages.GenericResponse;
import com.platform.server.BaseSession;
import com.tenant.entity.Tenant;

/**
 * @author Muhil
 */
@RestController
public class TenantController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/ping")
	public GenericResponse<Tenant> pingTenant() throws SchedulerException, IOException {
		//return messageSource.getMessage("tenant.ping", new String[] { BaseSession.getTenantUniqueName() }, BaseSession.getLocale());
	    return new GenericResponse<Tenant>().setData((Tenant)BaseSession.getCurrentTenant());
	}

}
