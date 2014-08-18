package org.swingBean.descriptor.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.swingBean.util.BeanUtils;

public class PatternValidator extends Validator {
	
	private Pattern pattern;
	private String formatExample;
	
	public void setFormatExample(String formatExample) {
		this.formatExample = formatExample;
	}

	public void setPattern(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	public boolean isValid(Object bean) {
		String value = (String) BeanUtils.getProperty(bean,getProperty());
		if(value == null || value.equals("")){
			return true;
		}
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	protected String getGeneratedErrorMessage(String label) {
		if(formatExample == null)
			return "O campo "+label+" não está no formato correto";
		else
			return "O campo "+label+" deve seguir o formato " + formatExample;
	}

}
