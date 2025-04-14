package com.aggregator.unifiedservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.platform.util.PlatformUtil;

/**
 * @author Muhil
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.platform.*", "com.reactive.platform.*", "com.aggregator.*" })
@EntityScan(basePackages = { "com.platform.*", "com.reactive.platform.*", "com.aggregator.*" })
@EnableJpaRepositories(basePackages = { "com.platform.*", "com.reactive.platform.*", "com.aggregator.*" })
//@EnableR2dbcRepositories
@EnableAutoConfiguration
public class UnifiedDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnifiedDataServiceApplication.class, args);
		PlatformUtil.printStartupLog();
	}

}
