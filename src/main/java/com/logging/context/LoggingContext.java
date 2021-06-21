package com.logging.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.logging.component.LoggingObjectMapper;
import com.logging.model.PayloadLogModel;

/**
 * Classe responsável por mapear o contexto de log de uma requisição, centralizando
 * os dados da requisição e a estrutura de logs do contexto.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
public class LoggingContext {
	
	private static final Logger LOGGER = LoggerFactory.getLogger("logger");
	private LoggingObjectMapper loggingObjectMapper;

	private String contextId;
	private PayloadLogModel payloadLogModel;

	/**
	 * Construtor da classe com o mapeamento do id do contexto.
	 * 
	 * @param contextId ({@link String}) - id do contexto
	 */
	public LoggingContext(String contextId) {
		loggingObjectMapper = new LoggingObjectMapper();

		this.contextId = contextId;
		this.payloadLogModel = new PayloadLogModel();
	}
	
	/**
	 * Retorna o id do contexto.
	 * 
	 * @return {@code String} - id do contexto
	 */
	public String getContextId() {
		return contextId;
	}
	
	/**
	 * Retorna o payload com os dados da chamada.
	 * 
	 * @return {@code PayloadLogModel} - estrutura com os logs
	 */
	public PayloadLogModel getPayloadLogModel() {
		return payloadLogModel;
	}
	
	/**
	 * Realiza o log do conteúdo inserido ao payload do contexto.
	 * 
	 * @throws JsonProcessingException lançada em caso de falha na deserialização do
	 *                                 payload.
	 */
	public void logPayloadLogModel() throws JsonProcessingException {
		LOGGER.info(loggingObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payloadLogModel));				
	}
}