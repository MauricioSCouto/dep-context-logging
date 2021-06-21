package com.logging.context;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.MDC;

/**
 * Centraliza todos os contextos de log criados.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-19
 *
 */
public final class LoggingContextMap {

	private static final Map<String, LoggingContext> CONTEXTS = new HashMap<>();
	
	/**
	 * Construtor privado para evitar instanciação.
	 */
	private LoggingContextMap() {}
	
	/**
	 * Adiciona um novo contexto de log.
	 * 
	 * @param contextId ({@link String}) - id do contexto
	 * @param loggingContext ({@link LoggingContext}) - contexto de log
	 */
	public static void adicionarContexto(String contextId, LoggingContext loggingContext) {
		CONTEXTS.put(contextId, loggingContext);
		MDC.put(LoggingContextConstants.CONTEXT_ID, loggingContext.getContextId());
	}
	
	/**
	 * Retorna contexto de log associado ao id indicado.
	 * 
	 * @param contextId ({@link String}) - id do contexto
	 * @return {@code LoggingContext} - caso o id exista, retorna o contexto
	 *         associado ao id. Caso contrário, retorna <b>null</b>.
	 */
	public static LoggingContext recuperarContexto(String contextId) {
		return CONTEXTS.get(contextId);
	}

	/**
	 * Remove o contexto de log associado ao id indicado.
	 * 
	 * @param contextId ({@link String}) - id do contexto
	 */
	public static void removerContexto(String contextId) {
		CONTEXTS.remove(contextId);
		MDC.remove(LoggingContextConstants.CONTEXT_ID);
	}	
}
