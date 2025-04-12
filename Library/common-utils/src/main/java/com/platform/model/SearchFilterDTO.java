package com.platform.model;

/**
 * @author Muhil
 */
public class SearchFilterDTO {

	private String field;
	private Object value; // Search text
	private String matchMode; // Search type (contains, startsWith, notContains)
	private String operator; // Logical operator (and, or)

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		if(value.equals("true") || value.equals("false")) {
			return Boolean.parseBoolean(value.toString());
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMatchMode() {
		return matchMode;
	}

	public void setMatchMode(String matchMode) {
		this.matchMode = matchMode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
