package com.logging.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.logging.context.LoggingComponent;
import com.logging.context.LoggingContext;
import com.logging.context.LoggingContextFactory;
import com.logging.context.LoggingContextMap;

/**
 * Filter responsável por filtrar requisições HTTP com o intuito de mapeamento
 * de dados para log.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class LoggingFilter implements Filter {

	@Autowired
	private LoggingComponent loggingComponent;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final LoggingContext loggingContext = LoggingContextFactory.build(request);

		loggingComponent.addRequest(request);

		chain.doFilter(request, response);

		loggingComponent.addResponse(response);
		loggingContext.logPayloadLogModel();

		LoggingContextMap.removerContexto(loggingContext.getContextId());
	}
}