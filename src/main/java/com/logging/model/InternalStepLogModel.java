package com.logging.model;

import org.slf4j.event.Level;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Modelo para mapeamento de logs internos da aplicação.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
public class InternalStepLogModel {
	private String message;
	private Level level;
	private String caller;

	/**
	 * Construtor do modelo.
	 * 
	 * @param message ({@link String}) - mensagem do log 
	 * @param level ({@link Level}) - level do log
	 * @param caller ({@link Class}) - classe geradora do log
	 */
	public InternalStepLogModel(String message, Level level, Class<?> caller) {
		this.message = message;
		this.level = level;
		this.caller = caller.getCanonicalName();
	}

	/**
	 * Retorna a mensagem do log.
	 * 
	 * @return {@code String} - mensagem do log.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Retorno o level do log.
	 * 
	 * @return {@code Level} - retorna o level do log.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Retorna a classe que gerou o log.
	 * 
	 * @return {@code Class} - retorna a classe que gerou o log.
	 */
	public String getCaller() {
		return caller;
	}
}
