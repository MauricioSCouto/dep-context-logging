package com.logging.component;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.logging.configuration.LoggingMaskingIntrospector;

/**
 * Mapper configurado para logging e serialização de itens mascarados.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-21
 *
 */
public final class LoggingObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -6999905462776507438L;

	public LoggingObjectMapper() {
		setAnnotationIntrospector(new LoggingMaskingIntrospector());
		registerModule(new JavaTimeModule());
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"));
	}
	
}
