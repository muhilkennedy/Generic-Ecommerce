package com.platform.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.configurations.ConfigTypes;
import com.platform.entity.ConfigType;
import com.platform.exceptions.ConfigTypeException;
import com.platform.messages.GenericResponse;
import com.platform.model.ConfigRequest;
import com.platform.serviceImpl.ConfigTypesServiceImpl;
import com.platform.user.permissions.Permissions;

import io.jsonwebtoken.lang.Arrays;

/**
 * @author Muhil
 */
@RestController
@RequestMapping("admin/config")
@ValidateUserToken
public class ConfigTypesController {

	@Autowired
	private ConfigTypesServiceImpl configService;

	@GetMapping
	@UserPermission(values = { Permissions.SUPER_USER })
	public GenericResponse<ConfigTypes> getConfigs() {
		return new GenericResponse<ConfigTypes>(Arrays.asList(ConfigTypes.values()));
	}

	@PostMapping
	@UserPermission(values = { Permissions.SUPER_USER })
	public GenericResponse<ConfigType> createConfig(@RequestBody ConfigRequest config) throws ConfigTypeException {
		return new GenericResponse<ConfigType>(configService.createConfigType(config));
	}

}
