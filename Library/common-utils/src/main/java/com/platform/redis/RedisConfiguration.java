package com.platform.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.platform.logging.Log;

/**
 * @author Muhil Kennedy
 *
 */
//@Profile(value = { "prod", "dev" })
@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "spring.cache", value = "enabled", havingValue = "true")
public class RedisConfiguration {
	
	@Value("${spring.data.redis.port}")
	private int port;

	// Creating Connection with Redis
	/*@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	// multiple bean can be configured for specific entity types with diff serializer.
	@Bean(name = "genericCacheManager")
	public RedisTemplate<Long, Object> redisTemplate() {
		RedisTemplate<Long, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
		redisTemplate.setDefaultSerializer(serializer);
		redisTemplate.setEnableDefaultSerializer(true);
		return redisTemplate;
	}*/
	
	// default redis config picked up by spring
	@Bean
	public RedisTemplate<Long, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
	    RedisTemplate<Long, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(redisConnectionFactory);
	    return template;
	}
    
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		Log.platform.info("----- Initializing REDIS Cache Manager -----");
		RedisCacheConfiguration config = RedisCacheConfiguration
				.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
				.prefixCacheNameWith(this.getClass().getSimpleName() + ".")
				.entryTtl(Duration.ofHours(1))
				.disableCachingNullValues();
		Log.platform.info("----- Connected to REDIS server at port {} -----" , port);
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
	}
	
//	@Bean("RedisKeyGenerator")
//    public KeyGenerator keyGenerator() {
//        return new RedisKeyGenerator();
//    }

}

