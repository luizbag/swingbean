package org.swingBean.visualTest;

import org.swingBean.descriptor.validator.Validator;

public class CPFValidator extends Validator {

	@Override
	public boolean isValid(Object bean) {
		// Algoritmo de valida��o do CPF
		return true;
	}

	@Override
	protected String getGeneratedErrorMessage(String label) {
		return "O campo "+label+" n�o possui um valor de CPF v�lido.";
	}

}
