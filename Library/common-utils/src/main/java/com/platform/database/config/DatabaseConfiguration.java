package com.platform.database.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Muhil
 * configs to be read from implementing application.
 */
@Configuration
public class DatabaseConfiguration {

	@Value("${platform.database.url}")
	private String url;

	@Value("${platform.database.username}")
	private String username;

	@Value("${platform.database.password}")
	private String password;

	@Value("${platform.database.driver-class-name}")
	private String driverClassName;

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create().url(url).username(username).password(password)
				.driverClassName(driverClassName).build();
	}
	
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
