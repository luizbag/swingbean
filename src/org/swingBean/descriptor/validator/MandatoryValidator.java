package org.swingBean.descriptor.validator;

import org.swingBean.util.BeanUtils;

public class MandatoryValidator extends Validator{

	public boolean isValid(Object bean) {
		Object value = BeanUtils.getProperty(bean,getProperty());
		if(value == null || (value instanceof String && ((String)value).trim().equals("")))
			return false;
		return true;
	}

	protected String getGeneratedErrorMessage(String label) {
		return "O campo "+label+" é obrigatório.";
	}

}
