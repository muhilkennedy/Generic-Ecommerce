package com.platform.push.notification;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.platform.logging.Log;
import com.platform.server.BaseSession;
import com.platform.storage.gcs.GCSConfiguration;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 * Firebase Push Message config uses same config as GCS.
 */
@Component
@ConditionalOnProperty(prefix = "app.gcs", value = "enabled", havingValue = "true")
public class GooglePushNotificationFactory {

	@Autowired
	private GCSConfiguration defaultGCSConfig;
	private Map<String, FirebaseApp> tenantConfigs = new HashMap<String, FirebaseApp>();

	@PostConstruct
	public void init() throws FileNotFoundException, IOException {
		byte[] bytes = Base64.getDecoder().decode(defaultGCSConfig.getConfig());
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(bytes))).build();
				//.setDatabaseUrl("url").build();
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
		Log.platform.info("---- Default Firebase Push Cloud Messaging initialized ----");
	}

	public FirebaseMessaging message() {
		if (!this.tenantConfigs.isEmpty() && this.tenantConfigs.get(BaseSession.getTenantUniqueName()) != null) {
			return FirebaseMessaging.getInstance(this.tenantConfigs.get(BaseSession.getTenantUniqueName()));
		}
		// return default instance
		return FirebaseMessaging.getInstance();
	}

	public void updateTenantConfig(String tenantUniqueName, String gcpConfig) throws IOException {
		synchronized (GooglePushNotificationFactory.class) {
			this.tenantConfigs.put(tenantUniqueName, initFireBaseAppForTenant(tenantUniqueName, gcpConfig));
		}
	}

	private FirebaseApp initFireBaseAppForTenant(String tenantUniqueName, String gcpConfig) throws IOException {
		byte[] bytes = Base64.getDecoder().decode(gcpConfig);
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(bytes))).build();
		return FirebaseApp.initializeApp(options, tenantUniqueName);
	}

}
