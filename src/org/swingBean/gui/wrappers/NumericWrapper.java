package org.swingBean.gui.wrappers;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;

import org.swingBean.descriptor.TypeConstants;
import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.gui.EmptyNumberFormatterNoLetter;

import com.jgoodies.validation.formatter.EmptyNumberFormatter;

public class NumericWrapper implements ComponentWrapper {
	
	private JFormattedTextField numericText;
	private String type;
	private Double minValue;
	private Double maxValue;
	private String numberFormat;

	public Object getValue() {
		if((numericText).getText().equals(""))
			return minValue != null ? minValue : 0;
		try {
			if(type.equals(TypeConstants.INTEGER))
				return ((Number)numericText.getFormatter().stringToValue(numericText.getText())).intValue();
			if(type.equals(TypeConstants.LONG))
				return ((Number)numericText.getFormatter().stringToValue(numericText.getText())).longValue();
			if(type.equals(TypeConstants.FLOAT))
				return ((Number)numericText.getFormatter().stringToValue(numericText.getText())).floatValue();
			if(type.equals(TypeConstants.DOUBLE))
				return ((Number)numericText.getFormatter().stringToValue(numericText.getText())).doubleValue();
		} catch (Exception e) {
			return minValue;
		}
		return minValue;
	}
	
	public String getStringValue(){
		return numericText.getText();
	}

	public void setValue(Object value) {
		try {
			numericText.setText(numericText.getFormatter().valueToString(value));
		} catch (ParseException e) {
			cleanValue();
		}
	}

	public void cleanValue() {
		try {
			numericText.setText(numericText.getFormatter().valueToString(minValue != null ? minValue : 0));
		} catch (ParseException e) {
			numericText.setText("0");
		}
	}

	public Component getComponent() {
		return numericText;
	}
	
	public void setEnable(boolean enable) {
		numericText.setEnabled(enable);
	}

	public void initComponent() throws Exception {
		EmptyNumberFormatter formatter = null;
		NumberFormat format = null; 
		
		if(numberFormat != null)
			format = new DecimalFormat(numberFormat);
		else if(type.equals(TypeConstants.INTEGER) || type.equals(TypeConstants.LONG))
			format = NumberFormat.getIntegerInstance();
		else
			format = NumberFormat.getInstance();
		
		formatter = new EmptyNumberFormatterNoLetter(format);
		if(minValue != null)
			formatter.setMinimum(minValue);
		if(maxValue != null)
			formatter.setMaximum(maxValue);			
		numericText = new JFormattedTextField(formatter);
		setValue(0);
		numericText.setFont(LookProvider.getLook().getFieldsFont());
		
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setNumberFormat(String format){
		this.numberFormat = format;
	}

}
