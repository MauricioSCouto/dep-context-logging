package com.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.logging.enumerator.MaskingStrategy;

/**
 * Anotação para mapeamento da campos sensíveis que devem ser mascarados na
 * geração de logs.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SensitiveData {
	
	/**
	 * Estratégia para mascamento do campo.
	 * 
	 * @return {@code MaskedStrategy} - retorna o {@link MaskingStrategy} com a
	 *         estratégia para mascaramento do campo. Caso não definida a
	 *         estratégia, o padrão é {@link MaskingStrategy#FULL}.
	 */
	MaskingStrategy strategy() default MaskingStrategy.FULL;
}
