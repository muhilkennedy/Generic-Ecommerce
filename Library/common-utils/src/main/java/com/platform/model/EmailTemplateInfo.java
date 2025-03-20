package com.platform.model;

import java.util.List;

/**
 * @author Muhil
 */
public class EmailTemplateInfo {

	private String title;
	private List<String> placeHolders;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getPlaceHolders() {
		return placeHolders;
	}

	public void setPlaceHolders(List<String> placeHolders) {
		this.placeHolders = placeHolders;
	}

}
