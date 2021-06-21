package com.logging.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Modelo para mapeamento de dados do request.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class RequestLogModel extends HttpLogModel {
	private String uri;
	private String method;	
	private Map<String, Object> parameters = new HashMap<>();
	
	/**
	 * Retorna os parâmetros do request.
	 * 
	 * @return {@code Map} - parâmetros do request
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * Adiciona os parâmetros do request.
	 * 
	 * @param parameters ({@link Map}) - parâmetros do request
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Retorna a uri do request.
	 * 
	 * @return {@code String} - uri do request
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Adiciona a uri do request.
	 * 
	 * @param uri ({@link String}) - uri do request
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * Retorna o método HTTP do request.
	 * 
	 * @return {@code String} - método do request
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Adiciona o método do request.
	 * 
	 * @param method ({@link String}) - método do request
	 */
	public void setMethod(String method) {
		this.method = method;
	}
}
