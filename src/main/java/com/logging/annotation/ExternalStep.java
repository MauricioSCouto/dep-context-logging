package com.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para mapeamento de chamadas externas realizadas pela aplicação.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExternalStep {
	
	/**
	 * Identificação do step externo sendo invocado.
	 * 
	 * @return {@code String} - id do step.
	 */
	String stepId();
}
