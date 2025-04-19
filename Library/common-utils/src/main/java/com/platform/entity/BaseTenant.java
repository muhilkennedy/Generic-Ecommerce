package com.platform.entity;

/**
 * @author Muhil 
 * Lets have these tenant related overrides to get more
 * information on platform layer for email/notification related activities.
 */
public interface BaseTenant {
	
	String getName();
	
	Long getParent();
	
	String getLogo();

}
