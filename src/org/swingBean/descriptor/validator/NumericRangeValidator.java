package org.swingBean.descriptor.validator;

import org.swingBean.util.BeanUtils;


public class NumericRangeValidator extends Validator {
	
	private double maxValue;
	private double minValue;
	private boolean validateMax = false;
	private boolean validateMin = false;
	
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
		validateMax = true;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
		validateMin = true;
	}

	public boolean isValid(Object bean) {
		double value = ((Number)BeanUtils.getProperty(bean,getProperty())).doubleValue();
		if((validateMax && maxValue<value) || (validateMin && minValue>value))
			return false;
		return true;
	}

	protected String getGeneratedErrorMessage(String label) {
		if(validateMax)
			if(validateMin)
				return "O campo "+label+" deve estar entre os valores "+minValue+" e "+maxValue;
			else
				return "O campo "+label+" deve possuir valor menor que "+maxValue;
		else
			return "O campo "+label+" deve possuir valor maior que "+minValue;
	}


}
