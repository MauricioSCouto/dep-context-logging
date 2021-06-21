package com.logging.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.logging.component.LoggingObjectMapper;
import com.logging.enumerator.MaskingStrategy;
import com.logging.utils.MaskingUtils;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
@JsonPropertyOrder({
	"startTime",
	"endTime",
	"duration",
	"threadName",
	"request",
	"response",
	"internalSteps",
	"externalSteps"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class PayloadLogModel {
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime startTime;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime endTime;
	
	@JsonProperty("duration (ms)")
	private long duration;

	private String threadName;
	
	@JsonProperty("request")
	private RequestLogModel request;
	
	@JsonProperty("response")
	private ResponseLogModel response;
	
	@JsonProperty("externalSteps")
	private List<ExternalStepLogModel> externalStepLogModelList;
	
	@JsonProperty("internalSteps")
	private List<InternalStepLogModel> internalStepLogModelList;
		
	/**
	 * Construtor responsável pela inicialização dos componentes do modelo.
	 * @param objectMapper 
	 */
	public PayloadLogModel() {
		startTime = LocalDateTime.now();
		threadName = Thread.currentThread().getName();
		internalStepLogModelList = new ArrayList<>();
		request = new RequestLogModel();
		response = new ResponseLogModel();
		externalStepLogModelList = new ArrayList<>();
	}
	
	/**
	 * Método responsável por finalizar a adição de dados base do modelo.
	 */
	public void finish() {
		endTime = LocalDateTime.now();
		duration = Duration.between(startTime, endTime).toMillis();
	}
	
	/**
	 * Retorna a data de início da chamada.
	 * 
	 * @return {@code LocalDateTime} - data de início da chamada
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Retorna a data de término da chamada.
	 * 
	 * @return {@code LocalDateTime} - data de término da chamada
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * Retorna a duração da chamada em milisegundos.
	 * 
	 * @return {@code Long} - duração da chamada
	 */
	public Long getDuration() {
		return duration;
	}

	/**
	 * Retorna a thread de execução da chamada.
	 * 
	 * @return {@code String} - thread de execução da chamada
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * Retorna os dados de request da chamada.
	 * 
	 * @return {@code RequestLogModel} - dados de request da chamada
	 */
	public RequestLogModel getRequest() {
		return request;
	}

	/**
	 * Retorna os dados de response da chamada.
	 * 
	 * @return {@code ResponseLogModel} - dados de response da chamada
	 */
	public ResponseLogModel getResponse() {
		return response;
	}

	/**
	 * Retorna os logs internos da chamada.
	 * 
	 * @return {@code List} - lista de logs internos da chamada
	 */
	public List<InternalStepLogModel> getInternalSteps() {
		return internalStepLogModelList;
	}
	
	/**
	 * Retorna os logs das chamadas externas.
	 * 
	 * @return {@code List} - lista de logs das chamadas externas.
	 */
	public List<ExternalStepLogModel> getExternalSteps() {
		return externalStepLogModelList;
	}

	/**
	 * Adiciona um log interno.
	 * 
	 * @param internalStepLogModel ({@link InternalStepLogModel}) - dados do log interno. 
	 */
	public void addInternalStepLog(InternalStepLogModel internalStepLogModel) {
		this.internalStepLogModelList.add(internalStepLogModel);
	}
	
	/**
	 * Adiciona um request header.
	 * 
	 * @param key ({@link String}) - nome do header
	 * @param value ({@link String}) - valor do header
	 */
	public void addRequestHeader(String key, String value) {
		request.addHeader(key, value);	
	}

	/**
	 * Mascára o valor do header e adiciona a lista de headers de request.
	 * 
	 * @param key ({@link String}) - nome do header
	 * @param value ({@link String}) - valor do header
	 * @param strategy ({@link MaskingStrategy}) - estratégia de mascaramento
	 */
	public void addRequestHeader(String key, String value, MaskingStrategy strategy) {
		String maskedValue = MaskingUtils.maskValue(value, strategy);
		addRequestHeader(key, maskedValue);
	}

	/**
	 * Adiciona a uri do request.
	 * 
	 * @param uri ({@link String}) - uri do request
	 */
	public void addRequestUri(String uri) {
		request.setUri(uri);
	}
	
	/**
	 * Retorna o método HTTP do request.
	 * 
	 * @return {@code String} - método do request
	 */
	public void addRequestMethod(String method) {
		request.setMethod(method);
	}
	
	/**
	 * Adiciona os parâmetros de request da chamada.
	 * 
	 * @param parameters ({@link Object}) - objeto contendo os parâmetros do request.
	 */
	@SuppressWarnings("unchecked")
	public void addRequestParameters(Object parameters) {
		Map<String, Object> parametersMap = new LoggingObjectMapper().convertValue(parameters, Map.class);
		request.setParameters(parametersMap);
	}
	
	/**
	 * Adiciona o body de request da chamada.
	 * 
	 * @param body ({@link Object}) - objeto contendo o body do request.
	 */
	@SuppressWarnings("unchecked")
	public void addRequestBody(Object body) {
		Map<String, Object> bodyMap = new LoggingObjectMapper().convertValue(body, Map.class);		
		request.setBody(bodyMap);
	}

	/**
	 * Adiciona o body de response da chamada.
	 * 
	 * @param body ({@link Object}) - objeto contendo o body do response.
	 */
	@SuppressWarnings("unchecked")
	public void addResponseBody(Object body) {
		Map<String, Object> bodyMap = new LoggingObjectMapper().convertValue(body, Map.class);		
		response.setBody(bodyMap);
	}
	
	/**
	 * Adiciona um response header.
	 * 
	 * @param key ({@link String}) - nome do header
	 * @param value ({@link String}) - valor do header
	 */
	public void addResponseHeader(String key, String value) {
		response.addHeader(key, value);	
	}

	/**
	 * Mascára o valor do header e adiciona a lista de headers de response.
	 * 
	 * @param key ({@link String}) - nome do header
	 * @param value ({@link String}) - valor do header
	 * @param strategy ({@link MaskingStrategy}) - estratégia de mascaramento
	 */
	public void addResponseHeader(String key, String value, MaskingStrategy strategy) {
		String maskedValue = MaskingUtils.maskValue(value, strategy);
		addResponseHeader(key, maskedValue);
	}

	/**
	 * Adiciona o status code HTTP do response.
	 * 
	 * @param statusCode ({@link Integer}) - status code do response
	 */
	public void addResponseStatus(Integer statusCode) {
		response.setStatusCode(statusCode);
	}
	
	/**
	 * Adiciona o log de uma chamada externa.
	 * 
	 * @param externalStepLogModel ({@link ExternalStepLogModel}) - dados da chamada
	 *                             externa.
	 */
	public void addExternalStepLog(ExternalStepLogModel externalStepLogModel) {
		this.externalStepLogModelList.add(externalStepLogModel);
	}	
}
