package org.swingBean.descriptor.validator;

import org.swingBean.util.BeanUtils;

public class StringSizeValidator extends Validator {
	
	private int minSize = 0;
	private boolean validateMaxSize = false;
	private int maxSize = 0;

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
		validateMaxSize = true;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	public boolean isValid(Object bean) {
		String value = (String) BeanUtils.getProperty(bean,getProperty());
		if(value == null)
			return false;
		else
			value.trim();
		if((validateMaxSize && value.length()>maxSize) || value.length()<minSize)
			return false;
		return true;
	}

	public String getGeneratedErrorMessage(String label) {
		if(validateMaxSize)
			return "O tamanho do campo "+label+" deve estar entre "+minSize+" e "+maxSize;
		else
			return "O tamanho do campo "+label+" deve ser maior que "+minSize;
	}

}
