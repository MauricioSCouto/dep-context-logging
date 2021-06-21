package com.logging.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.logging.inteceptor.LoggingHttpRequestInterceptor;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-20
 *
 */
@Configuration
@ComponentScan("com.logging")
public class LoggingConfiguration {

	@Bean(name="loggingRestTemplate")
	public RestTemplate getRestTemplate(LoggingHttpRequestInterceptor loggingHttpRequestInterceptor) {
		RestTemplate restTemplate = new RestTemplate();
		
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		
		if(interceptors.isEmpty()) {
			interceptors = new ArrayList<>();
		}
		
		interceptors.add(loggingHttpRequestInterceptor);
		restTemplate.setInterceptors(interceptors);
		
		return restTemplate;
	}
}
