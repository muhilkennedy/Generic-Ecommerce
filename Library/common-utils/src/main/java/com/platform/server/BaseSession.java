package com.platform.server;

import java.util.Locale;

import org.apache.commons.lang3.LocaleUtils;

import com.platform.entity.BaseEntity;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil 
 * Basesession should be set for each http request/thread.
 */
public class BaseSession {

	private static final ThreadLocal<BaseEntity> tenant = new ThreadLocal<>();// identifies active tenant
	private static final ThreadLocal<BaseEntity> currentTenant = new ThreadLocal<>();// identifies tenant session owner
	private static final ThreadLocal<BaseEntity> user = new ThreadLocal<>();
	private static final ThreadLocal<Locale> locale = new ThreadLocal<>();

	public static void setTenant(BaseEntity tnt) {
		tenant.set(tnt);
	}

	public static BaseEntity getTenant() {
		return tenant.get();
	}

	public static void setCurrentTenant(BaseEntity tnt) {
		setTenant(tnt);
		currentTenant.set(tnt);
	}

	public static BaseEntity getCurrentTenant() {
		return currentTenant.get();
	}

	public static Long getTenantId() {
		return tenant.get().getRootid();
	}
	
	public static Long getUserId() {
		return user.get().getRootid();
	}


	public static String getTenantUniqueName() {
		return tenant.get() != null ? tenant.get().getUniqueName() : PlatformUtil.EMPTY_STRING;
	}

	public static void setUser(BaseEntity usr) {
		user.set(usr);
	}

	public static BaseEntity getUser() {
		return user.get();
	}

	public static void setLocale(Locale loc) {
		locale.set(loc);
	}

	public static void setLocale(String localeCode) {
		locale.set(LocaleUtils.toLocale(localeCode));
	}

	public static Locale getLocale() {
		return locale.get();
	}

	public static void tearDownSession() {
		tenant.remove();
		user.remove();
		locale.remove();
		currentTenant.remove();
	}

	public static void setupSession(Long tenantId, Long userId) {
		BaseEntity ent = new BaseEntity();
		ent.setRootid(tenantId);
		setCurrentTenant(ent);
		ent = new BaseEntity();
		ent.setRootid(userId);
		setUser(ent);
	}

}
