package com.logging.utils;

import org.apache.commons.lang3.RegExUtils;

import com.logging.enumerator.MaskingStrategy;

/**
 * Classe contendo operações estáticas relacionadas ao mascaramento de dados.
 * 
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
public class MaskingUtils {

	/**
	 * Realiza o mascaramento do valor indicado com base na estratégia indicada.
	 * 
	 * @param value    ({@link String})- valor a ser mascarado.
	 * @param strategy ({@link MaskingStrategy})- estratégia de mascaramento.
	 * @return {@code String} - valor mascarado com <b>*</b>.
	 */
	public static String maskValue(String value, MaskingStrategy strategy) {
		String maskedValue;
		
		switch (strategy) {
		case FULL:
			maskedValue = RegExUtils.replaceAll(value, ".", "*");
			break;

		default:
			maskedValue = value;
			break;
		}
		
		return maskedValue;
	}
}
