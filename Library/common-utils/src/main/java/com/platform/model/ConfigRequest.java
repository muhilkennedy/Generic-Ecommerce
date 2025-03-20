package com.platform.model;

import java.util.Map;

public class ConfigRequest {

	private String type;
	Map<String, String> configType;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getConfigType() {
		return configType;
	}

	public void setConfigType(Map<String, String> configType) {
		this.configType = configType;
	}

}
