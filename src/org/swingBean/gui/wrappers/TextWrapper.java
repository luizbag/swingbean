package org.swingBean.gui.wrappers;

import java.awt.Component;
import java.text.ParseException;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.*;

import org.swingBean.descriptor.TypeConstants;
import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.gui.FixedSizeFilter;
import org.swingBean.gui.RegexInputVerifier;

public class TextWrapper implements ComponentWrapper {
	
	private JTextComponent textField;
	private String mask;
	private String type = TypeConstants.TEXT;
	private String pattern;
	private boolean uppercase = false;
	private int size = Integer.MAX_VALUE;
	private int rows = 5;
	
	public TextWrapper() {
	}

	public Object getValue() {
		return textField.getText().trim();
	}

	public void setValue(Object value) {
		textField.setText(value.toString());
	}

	public void cleanValue() {
		textField.setText("");
	}

	public Component getComponent() {
		if(textField instanceof JTextArea){
			((JTextArea)textField).setLineWrap(true); 
			return new JScrollPane(textField);
		}			
		return textField;
	}

	public void setEnable(boolean enable) {
		textField.setEnabled(enable);
	}

	public void initComponent() throws Exception {
		if(type.equals(TypeConstants.LARGE_TEXT)){
			textField = new JTextArea();
			((JTextArea)textField).setRows(rows);
		}
		else if(type.equals(TypeConstants.PASSWORD))
			textField = new JPasswordField();
		else{
			if(mask == null)
				textField = new JFormattedTextField();
			else
				textField = new JFormattedTextField(new MaskFormatter(mask));
		}
		((AbstractDocument)textField.getDocument()).setDocumentFilter(new FixedSizeFilter(size, uppercase));
		
		if(pattern != null){
			Pattern compiledPattern = Pattern.compile(pattern);
			textField.setInputVerifier(new RegexInputVerifier(compiledPattern));
		}
		
		textField.setFont(LookProvider.getLook().getFieldsFont());
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUppercase(boolean uppercase) {
		this.uppercase = uppercase;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}	
	
}
