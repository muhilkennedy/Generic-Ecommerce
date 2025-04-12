package com.platform.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Muhil
 */
@Configuration
@ConditionalOnProperty(prefix = "platform.api", value = "reactive", havingValue = "true")
public class WebClientConfig {
    
	@Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
	
}
