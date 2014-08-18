package org.swingBean.visualTest;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import org.swingBean.descriptor.look.DefaultLookDescriptor;

public class ExampleLook extends DefaultLookDescriptor {

	public JLabel createFormLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(getTextFont());
		label.setForeground(getTextColor());
		return label;
	}

	public JLabel createFormMandatoryLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Tahoma",Font.BOLD,10));
		label.setForeground(getTextColor());
		return label;
	}
	
	public Font getTextFont() {
		return new Font("Tahoma",Font.PLAIN,10);
	}
	
	public Color getTextColor() {
		return Color.BLUE;
	}

	public Font getFieldsFont() {
		return new Font("Verdana",Font.PLAIN,10);
	}

}
