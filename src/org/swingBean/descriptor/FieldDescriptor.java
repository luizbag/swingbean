package org.swingBean.descriptor;

import java.util.Map;

import org.swingBean.descriptor.validator.Validator;

import com.jgoodies.validation.ValidationResult;

public interface FieldDescriptor {
	
	public Class getBeanClass();
	
	public int getLineLenght();
	
	public String[] getLinePropertys(int lineNumber);
	
	public int getNumberColumns(int row);
	
	public String getParameter(String property, String parameter);
	
	public Map<String,String> getParameters(String property);
	
	public ValidationResult validate(Object bean);
	
	public void addValidator(Validator validator);

}
