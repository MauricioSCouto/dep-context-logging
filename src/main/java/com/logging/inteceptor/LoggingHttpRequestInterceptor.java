package com.logging.inteceptor;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.logging.context.LoggingComponent;
import com.logging.model.ExternalStepLogModel;

/**
 * Interceptor de chamadas HTTP para registro de logs de chamadas externas.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@Component
public class LoggingHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	@Autowired
	private LoggingComponent loggingComponent;
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		LocalDateTime startTime = LocalDateTime.now();
		
		ClientHttpResponse clientHttpResponse = execution.execute(request, body);
		
		LocalDateTime endTime = LocalDateTime.now();
		long duration = Duration.between(startTime, endTime).toMillis();
			
		ExternalStepLogModel externalStepLogModel = new ExternalStepLogModel();
		externalStepLogModel.setStartTime(startTime);
		externalStepLogModel.setEndTime(endTime);
		externalStepLogModel.setDuration(duration);
		externalStepLogModel.setStepId(request.getURI().toString());
		externalStepLogModel.setStatus(clientHttpResponse.getStatusText());
		
		loggingComponent.addExternalStepLog(externalStepLogModel);
		
		return clientHttpResponse;
	}
}
