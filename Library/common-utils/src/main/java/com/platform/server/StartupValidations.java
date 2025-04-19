package com.platform.server;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import com.platform.annotations.ClassMetaProperty;
import com.platform.antivirus.ClamAVService;
import com.platform.configurations.ConfigTypes;
import com.platform.entity.BaseEntity;
import com.platform.logging.Log;
import com.platform.push.notification.GooglePushNotificationFactory;
import com.platform.service.BaseService;
import com.platform.service.ConfigTypeService;
import com.platform.storage.GoogleConstants;
import com.platform.storage.gcs.GoogleStorageFactory;
import com.platform.util.PlatformUtil;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 *
 */
@Configuration
public class StartupValidations {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Value("${spring.cache.enabled}")
	private boolean cacheEnabled;
	
	@Value("${antivirus.clamav.enabled}")
	private boolean clamAvEnabled;
	
	@Autowired(required = false)
	private ClamAVService clamAvService;
	
	@Autowired
	@Qualifier("TenantService")
	private BaseService tenantService;
	
	@Autowired
	private ConfigTypeService configService;
	
	@Autowired
	private GoogleStorageFactory gcsFactory;
	
	@Autowired
	private GooglePushNotificationFactory pushNotificationFactory;

	/**
	 * @param event Execute after application startup
	 * @throws PortUnreachableException
	 * @throws ParseException
	 * 
	 * NOTE: 1. Redis Cache reset
	 * 		 2. Test ClamAV connection
	 * 		 3. Load GCS configs
	 * 		 4. Load PushMessage configs
	 */
	@EventListener
	private void onApplicationEvent(ApplicationReadyEvent event) throws PortUnreachableException, ParseException {
		clearInitialCaches();
		pingClamAv();
		loadTenantsGoogleStorageAndPushNotificationConfigurations();
		Log.platform.info("StartupValidations done!");
	}

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

	private void loadTenantsGoogleStorageAndPushNotificationConfigurations() {
		this.tenantService.findAll().stream().map(tenant -> ((BaseEntity) tenant)).forEach(tenant -> {
			try {
				BaseSession.setupSession(tenant, null);
				AtomicReference<String> gcpConfig = new AtomicReference<>();
				AtomicReference<String> gcpBucket = new AtomicReference<>();
				CollectionUtils.emptyIfNull(configService.findConfigsByType(ConfigTypes.STORAGE.name())).stream()
						.forEach(config -> {
							if (config.getName().equals(GoogleConstants.GCPCONFIG)) {
								gcpConfig.set(config.getValue());
							} else if (config.getName().equals(GoogleConstants.GCPBUCKET)) {
								gcpConfig.set(config.getValue());
							}
						});
				if (StringUtils.isNotBlank(gcpConfig.get())) {
					Log.platform.info("Setting up GCS Config for Tenant : {}", tenant.getUniqueName());
					gcsFactory.updateTenantStorageConfig(tenant.getRootid(), gcpConfig.get(), gcpBucket.get());
					Log.platform.debug("Updated GCS config for tenant : {} : Config Bucket : {}",
							BaseSession.getTenantUniqueName(), gcsFactory.bucket());
					pushNotificationFactory.updateTenantConfig(tenant.getUniqueName(), gcpConfig.get());
					Log.platform.debug("Updated Push Message config for tenant : {}",
							BaseSession.getTenantUniqueName());
				}
			} catch (IOException e) {
				Log.platform.error(
						"Failed to initialize Google Storage / Message config for Tenant : {} : Exception : {}",
						tenant.getUniqueName(), e);
			} finally {
				BaseSession.tearDownSession();
			}
		});
	}

}
