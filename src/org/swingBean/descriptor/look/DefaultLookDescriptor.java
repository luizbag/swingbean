package org.swingBean.descriptor.look;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class DefaultLookDescriptor implements LookDescriptor {

	public Border getBorder(String title) {
		LineBorder lineborder = new LineBorder(new Color(0,0,0),1,true);
		Border border = BorderFactory.createTitledBorder(lineborder,title,TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,null,new Color(0,70,213));
		return border;
	}

	public JLabel createFormLabel(String text) {
		JLabel label = new JLabel(text);
		label.setName(text+"_label");
		return label;
	}

	public JLabel createFormMandatoryLabel(String text) {
		JLabel label = new JLabel("<html><b>"+text+"</b>");
		label.setName(text+"_label");
		return label;
	}

	public Font getTextFont() {
		return new JLabel().getFont();
	}

	public Color getTextColor() {
		return Color.BLACK;
	}

	public Font getFieldsFont() {
		return new JLabel().getFont();
	}

	public Color getSelectedLineColor() {
		return Color.LIGHT_GRAY;
	}

	public Color getErrorLineColor() {
		return new Color(255,0,0,100);
	}

	public JLabel createFieldLabel(String text) {
		JLabel label = new JLabel(text);
		label.setName(text+"_label");
		return label;
	}
	

}
