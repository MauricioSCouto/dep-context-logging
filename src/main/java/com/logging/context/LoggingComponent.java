package com.logging.context;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.logging.enumerator.MaskingStrategy;
import com.logging.model.ExternalStepLogModel;
import com.logging.model.InternalStepLogModel;
import com.logging.model.PayloadLogModel;

/**
 * Componente responsável pela inserção de itens a serem logados junto ao
 * contexto de log.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-19
 *
 */
@Component
public class LoggingComponent {
	
	@Value("#{'${app.logging.headers-sensiveis}'.split(',')}")
	private List<String> sensitiveHeaders;

	/**
	 * Adiciona um log interno.
	 * 
	 * @param message ({@link String}) - mensagem do log 
	 * @param level ({@link Level}) - level do log
	 * @param caller ({@link Class}) - classe geradora do log
	 */
	public void addInternalStepLog(String message, Level level, Class<?> caller) {
		PayloadLogModel payloadLogModel = getPayloadLogModel();
		
		payloadLogModel.addInternalStepLog(new InternalStepLogModel(message, level, caller));
	}

	/**
	 * Adiciona os dados de request ao contexto.
	 * 
	 * @param request ({@link ServletRequest}) - dados de request
	 */
	public void addRequest(ServletRequest request) {		
		PayloadLogModel payloadLogModel = getPayloadLogModel();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		this.addRequestHeaders(httpServletRequest, payloadLogModel);
		
		payloadLogModel.addRequestUri(httpServletRequest.getRequestURI());
		payloadLogModel.addRequestMethod(httpServletRequest.getMethod());
	}

	/**
	 * Adiciona os dados de response ao contexto.
	 * 
	 * @param response ({@link ServletResponse}) - dados de response
	 */
	public void addResponse(ServletResponse response) {
		PayloadLogModel payloadLogModel = getPayloadLogModel();
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			
		addResponseHeaders(httpServletResponse, payloadLogModel);
			
		payloadLogModel.addResponseStatus(httpServletResponse.getStatus());
			
		payloadLogModel.finish();		
	}
	
	/**
	 * Adiciona os parâmetros de request da chamada.
	 * 
	 * @param parameters ({@link Object}) - objeto contendo os parâmetros do request.
	 */
	public void addRequestParameters(Object parameters) {
		PayloadLogModel payloadLogModel = getPayloadLogModel();
		payloadLogModel.addRequestParameters(parameters);
	}

	/**
	 * Adiciona o body de request da chamada.
	 * 
	 * @param body ({@link Object}) - objeto contendo o body do request.
	 */
	public void addRequestBody(Object body) {
		PayloadLogModel payloadLogModel = getPayloadLogModel();
		payloadLogModel.addRequestBody(body);
	}
	
	/**
	 * Adiciona o body de response da chamada.
	 * 
	 * @param body ({@link Object}) - objeto contendo o body do response.
	 */
	public void addResponseBody(Object body) {
		PayloadLogModel payloadLogModel = getPayloadLogModel();
		payloadLogModel.addResponseBody(body);
	}
	
	/**
	 * Adiciona o log de uma chamada externa.
	 * 
	 * @param externalStepLogModel ({@link ExternalStepLogModel}) - dados da chamada
	 *                             externa.
	 */
	public void addExternalStepLog(ExternalStepLogModel externalStepLogModel) {
		PayloadLogModel payloadLogModel = getPayloadLogModel();
		payloadLogModel.addExternalStepLog(externalStepLogModel);
	}

	/**
	 * Retorna o {@link PayloadLogModel} com base no contexto MDC da requisição.
	 * 
	 * @return {@code PayloadLogModel} - payload atrelado ao contexto de log.
	 */
	private PayloadLogModel getPayloadLogModel() {
		String contextId = MDC.get(LoggingContextConstants.CONTEXT_ID);
		LoggingContext loggingContext = LoggingContextMap.recuperarContexto(contextId);
		
		PayloadLogModel payloadLogModel = loggingContext.getPayloadLogModel();
		return payloadLogModel;
	}
	
	/**
	 * Adiciona os headers de request com base na lista de campos sensíveis mapeada.
	 * 
	 * @param httpServletRequest ({@link HttpServletRequest}) - dados do request
	 * @param payloadLogModel    ({@link PayloadLogModel}) - modelo de armazenamento
	 *                           dos itens a serem logados
	 */
	private void addRequestHeaders(HttpServletRequest httpServletRequest, PayloadLogModel payloadLogModel) {
		httpServletRequest.getHeaderNames().asIterator().forEachRemaining(key -> {
			String value = httpServletRequest.getHeader(key);
			
			if(sensitiveHeaders.contains(key)) {
				payloadLogModel.addRequestHeader(key, value, MaskingStrategy.FULL);
			}
			else {
				payloadLogModel.addRequestHeader(key, value);
			}			
		});
	}

	/**
	 * Adiciona os headers de response com base na lista de campos sensíveis mapeada.
	 * 
	 * @param httpServletResponse ({@link HttpServletResponse}) - dados do response
	 * @param payloadLogModel    ({@link PayloadLogModel}) - modelo de armazenamento
	 *                           dos itens a serem logados
	 * 
	 */
	private void addResponseHeaders(HttpServletResponse httpServletResponse, PayloadLogModel payloadLogModel) {
		httpServletResponse.getHeaderNames().forEach(key -> {
			String value = httpServletResponse.getHeader(key);
			
			if(sensitiveHeaders.contains(key)) {
				payloadLogModel.addResponseHeader(key, value, MaskingStrategy.FULL);
			}
			else {
				payloadLogModel.addResponseHeader(key, value);
			}			
		});
	}
}
