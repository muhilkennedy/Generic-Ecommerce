package com.tenant.service.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Muhil
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.platform.*", "com.tenant.*", "com.user.*", "com.service.*" })
@EntityScan(basePackages = { "com.platform.*", "com.tenant.*", "com.user.*", "com.service.*" })
@EnableJpaRepositories(basePackages = { "com.platform.*", "com.tenant.*", "com.user.*", "com.service.*" })
@EnableAutoConfiguration
public class TenantManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantManagerApplication.class, args);
	}

}
