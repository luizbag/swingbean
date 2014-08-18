package org.swingBean.descriptor.look;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.Border;

public interface LookDescriptor {
	
	public Border getBorder(String title);
	
	public JLabel createFormLabel(String text);
	
	public JLabel createFormMandatoryLabel(String text);
	
	public JLabel createFieldLabel(String text);
	
	public Font getTextFont();
	
	public Color getTextColor();
	
	public Font getFieldsFont();
	
	public Color getSelectedLineColor();
	
	public Color getErrorLineColor();
	
}
