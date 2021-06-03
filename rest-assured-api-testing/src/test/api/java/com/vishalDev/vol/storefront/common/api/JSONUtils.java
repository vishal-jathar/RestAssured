package com.onmobile.vol.storefront.common.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JSONUtils {

	private static final ObjectMapper JACKSON_OBJECT_MAPPER = createObjectMapper();

	private static ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	public static final String objectToJsonString(Object type) {
		try {
			return JACKSON_OBJECT_MAPPER.writeValueAsString(type);
		} catch (JsonProcessingException jpe) {
			throw new RuntimeException(jpe.getMessage());
		}
	}
}
