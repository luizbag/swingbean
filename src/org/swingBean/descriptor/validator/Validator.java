package org.swingBean.descriptor.validator;

import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.message.SimpleValidationMessage;


public abstract class Validator {
	
	private String property;
	public abstract boolean isValid(Object bean);
	protected abstract String getGeneratedErrorMessage(String label);

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}	
	public ValidationMessage getValidationMessage(String label){
		return new SimpleValidationMessage(getGeneratedErrorMessage(label),Severity.ERROR);
	}

}
