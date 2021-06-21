package com.logging.context;

import java.util.UUID;

import javax.servlet.ServletRequest;

/**
 * Factory responsável pela criação de {@link LoggingContext}.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-14
 */
public class LoggingContextFactory {

	/**
	 * Constrói um {@link LoggingContext} com base nos parâmetros indicados.
	 * 
	 * @param contextId ({@link String}) - id do contexto

	 * @return {@code LoggingContext} - contexto de logs da requisição.
	 */
	public static LoggingContext build(ServletRequest request) {
		String contextId = UUID.randomUUID().toString();
		
		LoggingContext loggingContext = new LoggingContext(contextId);		
		LoggingContextMap.adicionarContexto(contextId, loggingContext);
		
		return loggingContext;
	}
}
