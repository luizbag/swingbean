package org.swingBean.descriptor.validator;

import org.swingBean.util.BeanUtils;

public class SelectedRangeValidator extends Validator {

	private int maxSelected;
	private int minSelected;
	private boolean validateMax = false;
	private boolean validateMin = false;
	
	public void setMaxValue(int maxSelected) {
		this.maxSelected = maxSelected;
		validateMax = true;
	}

	public void setMinValue(int minSelected) {
		this.minSelected = minSelected;
		validateMin = true;
	}

	public boolean isValid(Object bean) {
		int numberSelected = ((Object[])BeanUtils.getProperty(bean,getProperty())).length;
		if((validateMax && maxSelected<numberSelected) || (validateMin && minSelected>numberSelected))
			return false;
		return true;
	}

	protected String getGeneratedErrorMessage(String label) {
		if(validateMax)
			if(validateMin)
				if(minSelected == maxSelected)
					return label+": exatamente "+minSelected+" selecionado(s)";
				else
					return label+": m�nimo "+minSelected+" e m�ximo "+maxSelected +" selecionado(s)";
			else
				return label+": m�ximo "+maxSelected+" selecionado(s)";
		else
			return label+": m�nimo "+minSelected+" selecionado(s)";
	}

}
