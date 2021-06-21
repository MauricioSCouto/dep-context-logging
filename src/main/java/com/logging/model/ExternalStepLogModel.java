package com.logging.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * Modelo para mapeamento de logs relacionados à chamadas externas.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class ExternalStepLogModel {

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	private LocalDateTime startTime;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	private LocalDateTime endTime;
	
	@JsonProperty("duration (ms)")
	private Long duration;
		
	private String stepId;
	
	private String status;

	/**
	 * Retorna a data de início da chamada do step.
	 * 
	 * @return {@code LocalDateTime} - data de início da chamada
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Adiciona a data de início da chamada do step.
	 * 
	 * @param startTime ({@link LocalDateTime}) - data de início da chamada
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Retorna a data de término da chamada do step.
	 * 
	 * @return {@code LocalDateTime} - data de término da chamada
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * Adiciona a data de término da chamada do step.
	 * 
	 * @param endTime ({@link LocalDateTime}) - data de término da chamada
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * Retorna a duração da chamada do step em milisegundos.
	 * 
	 * @return {@code Long} - duração da chamada
	 */
	public Long getDuration() {
		return duration;
	}

	/**
	 * Adiciona a duração da chamada do step.
	 * 
	 * @param duration ({@link Long}) - duração da chamada
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	/**
	 * Retorna o status de retorno da chamada do step.
	 * 
	 * @return {@code String} - status de retorno da chamada
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Adiciona o status de retorno da chamada do step.
	 * 
	 * @param status ({@link String}) - status de retorno da chamada
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Retorna o id do step chamado.
	 * 
	 * @return {@code String} - id do step chamado
	 */
	public String getStepId() {
		return stepId;
	}

	/**
	 * Adiciona o id do step chamado.
	 * 
	 * @param status ({@link String}) - id do step chamado
	 */
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
}
