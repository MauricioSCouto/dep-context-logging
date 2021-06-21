package com.logging.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Modelo para mapeamento de dados do response.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public class ResponseLogModel extends HttpLogModel {
	
	private Integer statusCode;

	/**
	 * Retorna o status code HTTP do response.
	 * 
	 * @return {@code Integer} - status code do response
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * Adiciona o status code HTTP do response.
	 * 
	 * @param statusCode ({@link Integer}) - status code do response
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
}
