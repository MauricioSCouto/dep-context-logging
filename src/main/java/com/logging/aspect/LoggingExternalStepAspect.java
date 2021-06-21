package com.logging.aspect;

import java.time.Duration;
import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logging.annotation.ExternalStep;
import com.logging.component.LoggingComponent;
import com.logging.model.ExternalStepLogModel;

/**
 * Aspecto para log de chamadas externas não associadas a requisições HTTP.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@Component
@Aspect
public class LoggingExternalStepAspect {

	@Autowired
	private LoggingComponent loggingComponent;
	
	/**
	 * Advice para orientação a aspectos em métodos anotados com
	 * {@link ExternalStep}.
	 * 
	 * @param proceedingJoinPoint ({@link ProceedingJoinPoint}) - joint point para
	 *                            direcionamento de chamadas.
	 * @throws Throwable lançada em caso de falha no mapeando AOP.
	 */
	@Around("@annotation (com.logging.annotation.ExternalStep)")
	public void processarRouteStep(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		ExternalStep routeStepAnnotation = methodSignature.getMethod().getAnnotation(ExternalStep.class);	
		
		LocalDateTime startTime = LocalDateTime.now();
		
		proceedingJoinPoint.proceed();
		
		LocalDateTime endTime = LocalDateTime.now();
		long duration = Duration.between(startTime, endTime).toMillis();
			
		ExternalStepLogModel externalStepLogModel = new ExternalStepLogModel();
		externalStepLogModel.setStartTime(startTime);
		externalStepLogModel.setEndTime(endTime);
		externalStepLogModel.setDuration(duration);
		externalStepLogModel.setStepId(routeStepAnnotation.stepId());
		externalStepLogModel.setStatus("Executed");
				
		loggingComponent.addExternalStepLog(externalStepLogModel);
	}	
}
