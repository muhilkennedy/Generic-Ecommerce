package com.platform.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Muhil
 */
public class Log {
	
	public static Logger logger = LoggerFactory.getLogger(Log.class);
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static final Logger platform = LoggerFactory.getLogger("com.platform");
	public static final Logger i18n = LoggerFactory.getLogger("com.i18n");
	public static final Logger tenant = LoggerFactory.getLogger("com.tenant");
	public static final Logger user = LoggerFactory.getLogger("com.user");
	public static final Logger util = LoggerFactory.getLogger("com.util");

}
