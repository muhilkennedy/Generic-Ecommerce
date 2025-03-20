package com.platform.storage.gcs;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.platform.server.BaseSession;

import jakarta.annotation.PostConstruct;

@Component
@ConditionalOnProperty(prefix = "app.gcs", value = "enabled", havingValue = "true")
public class GoogleStorageFactory {
	
	@Autowired
	private GCSConfiguration defaultConfig;

	private Credentials credentials;
	private Storage storage;
	private String bucketName;

	private Map<Long, StorageConfig> tenantStorages = new HashMap<Long, StorageConfig>();

	@PostConstruct
	public void init() throws FileNotFoundException, IOException {
		byte[] bytes = Base64.getDecoder().decode(defaultConfig.getConfig());
		this.credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(bytes));
		this.storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("default").build()
				.getService();
		this.bucketName = defaultConfig.getBucket();

	}

	public void updateTenantStorageConfig(Long tenantId, String gcpConfig, String gcpBucket) throws IOException {
		synchronized (GoogleStorageService.class) {
			this.tenantStorages.put(tenantId, addTenantStorageConfig(tenantId, gcpConfig, gcpBucket));
		}
	}

	private StorageConfig addTenantStorageConfig(Long tenantId, String gcpConfig, String gcpBucket) throws IOException {
		byte[] bytes = Base64.getDecoder().decode(gcpConfig);
		Credentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(bytes));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(tenantId.toString())
				.build().getService();
		StorageConfig storageConfig = new StorageConfig();
		storageConfig.setStorage(storage);
		storageConfig.setDefaultBucket(gcpBucket);
		tenantStorages.put(BaseSession.getTenantId(), storageConfig);
		return storageConfig;
	}

	public Storage storage() {
		if (!tenantStorages.isEmpty()) {
			return tenantStorages.get(BaseSession.getTenantId()).getStorage();
		}
		return storage;
	}

	public String bucket() {
		if (!tenantStorages.isEmpty()) {
			return tenantStorages.get(BaseSession.getTenantId()).getDefaultBucket();
		}
		return bucketName;
	}

}

class StorageConfig {

	private Storage storage;
	private String defaultBucket;

	public StorageConfig() {

	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public String getDefaultBucket() {
		return defaultBucket;
	}

	public void setDefaultBucket(String defaultBucket) {
		this.defaultBucket = defaultBucket;
	}

}
