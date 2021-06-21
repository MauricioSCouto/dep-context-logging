package com.logging.configuration;

import java.io.IOException;

import org.apache.commons.lang3.RegExUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.logging.annotation.SensitiveData;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-20
 *
 */
public class LoggingMaskingIntrospector extends NopAnnotationIntrospector {

	private static final long serialVersionUID = 6091127918438285465L;

	private static class LoggingSerializer extends StdSerializer<Object> {
		private static final long serialVersionUID = -2559935541194203360L;

		public LoggingSerializer() {
			super(Object.class);
		}
		
		@Override
		public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
			String valueString = String.valueOf(value);
			gen.writeString(RegExUtils.replaceAll(valueString, ".", "*"));
		}
		
	}
	
	@Override
    public Object findSerializer(Annotated am) {
		SensitiveData sensitiveData = am.getAnnotation(SensitiveData.class);
		
		if(sensitiveData != null) {
			return LoggingSerializer.class;
		}
		
		JsonSerialize jsonSerialize = am.getAnnotation(JsonSerialize.class);
		
		if(jsonSerialize != null && jsonSerialize.using() != null) {
			return jsonSerialize.using();
		}
		
		return null;
	}
	
}
