package com.user.convertors;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.logging.Log;
import com.platform.util.PlatformUtil;
import com.user.model.UserPreferences;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Muhil
 *
 */
@Converter(autoApply = true)
public class UserPreferenceConvertor implements AttributeConverter<UserPreferences, String> {

	@Override
	public String convertToDatabaseColumn(UserPreferences attribute) {
		try {
			if (attribute == null) {
				return PlatformUtil.EMPTY_STRING;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			Log.user.error("Error converting to database column - {}", ex);
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	@Override
	public UserPreferences convertToEntityAttribute(String dbData) {
		try {
			if (StringUtils.isAllEmpty(dbData)) {
				return new UserPreferences();
			}
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(dbData, UserPreferences.class);
		} catch (JsonProcessingException ex) {
			Log.user.error("Error converting to entity attribute - {}", ex);
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

}
