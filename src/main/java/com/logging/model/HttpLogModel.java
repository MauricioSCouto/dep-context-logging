package com.logging.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Modelo base para requisições HTTP(S).
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@JsonInclude(Include.NON_EMPTY)
public abstract class HttpLogModel {

	private Map<String, String> headers = new HashMap<>();
		
	private Map<String, Object> body = new HashMap<>();
		
	/**
	 * Retorna o body mapeado.
	 * 
	 * @return {@code Map} - body mapeado
	 */
	public Map<String, Object> getBody() {
		return body;
	}

	/**
	 * Adiciona o body descomposto em um {@link Map}.
	 * 
	 * @param body ({@link Map}) - body mapeado.
	 */
	public void setBody(Map<String, Object> body) {
		this.body = body;
	}

	/**
	 * Retorna os headers mapeados.
	 * 
	 * @return {@code Map} - headers mapeados
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Adiciona um header no modelo.
	 * 
	 * @param key ({@link String}) - nome do header
	 * @param value ({@link String}) - valor do header
	 */
	public void addHeader(String key, String value) {
		String header = this.headers.get(key);
		
		if(Objects.isNull(headers)) {
			header = value;
		}
		else {
			header = String.format("%s,%s", header, value);
		}
		
		headers.put(key, value);
	}
}
