package com.platform.server;

import java.net.PortUnreachableException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import com.platform.annotations.ClassMetaProperty;
import com.platform.antivirus.ClamAVService;
import com.platform.logging.Log;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 *
 */
@Configuration
public class StartupValidations {

	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Value("${spring.cache.enabled}")
	private boolean cacheEnabled;
	
	@Value("${antivirus.clamav.enabled}")
	private boolean clamAvEnabled;
	
	@Autowired(required = false)
	private ClamAVService clamAvService;

	/**
	 * Execute methods on post construct
	 */
	@PostConstruct
	private void postStartupProcessing() {
		validateClassCode();
	}

	/**
	 * Identify if same class code for entity tables.
	 */
	private void validateClassCode() {
		Reflections reflection = new Reflections("com.mken.");
		Set<String> uniqueSet = new HashSet<String>();
		Set<Class<?>> classes = reflection.getTypesAnnotatedWith(ClassMetaProperty.class);
		long count = classes.stream().map(cls -> (ClassMetaProperty) cls.getAnnotation(ClassMetaProperty.class))
				.filter(cls -> !uniqueSet.add(cls.code())).count();
		Assert.isTrue(!(count > 0), "Duplicate Class Codes Detected");
		Log.platform.debug("Class codes validated");
	}

	/**
	 * @param event Execute after application startup
	 * @throws PortUnreachableException
	 * @throws ParseException
	 */
	@EventListener
	private void onApplicationEvent(ApplicationReadyEvent event) throws PortUnreachableException, ParseException {
		clearInitialCaches();
		pingClamAv();
		Log.platform.info("StartupValidations done!");
	}

	private void clearInitialCaches() {
		if(cacheEnabled) {
			try {
				/*cacheManager.getCacheNames().parallelStream().filter(name -> cacheManager.getCache(name) != null)
						.peek(cache -> Log.platform.warn("Clearing cache {} ", cache)).forEach(cache -> {
							cacheManager.getCache(cache).clear();
						});*/
				redisTemplate.delete(redisTemplate.keys("*"));
			} catch (IllegalArgumentException e) {
				Log.platform.error("Exception while cleaning cache {}", e);
			}
		}
	}
	
	private void pingClamAv() {
		if(clamAvEnabled) {
			Log.platform.info("ClamAV Antivirus scan is enabled");
			clamAvService.ping();
		}
	}

}
