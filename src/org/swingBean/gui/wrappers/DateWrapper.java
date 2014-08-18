package org.swingBean.gui.wrappers;

import java.awt.Component;
import java.util.Date;

import javax.swing.JSpinner;

import org.swingBean.descriptor.look.LookProvider;

import com.toedter.calendar.JDateChooser;

public class DateWrapper implements ComponentWrapper {
	
	private JDateChooser dateChooser;
	private String format;

	public Object getValue() {
		return dateChooser.getDate();
	}

	public void setValue(Object value) {
		dateChooser.setDate((Date)value);
	}

	public void cleanValue() {
		dateChooser.setDate(new Date());
	}

	public Component getComponent() {
		return dateChooser;
	}

	public void setEnable(boolean enable) {
		for(Component comp : dateChooser.getComponents())
			comp.setEnabled(enable);	
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void initComponent() throws Exception {
		dateChooser = new JDateChooser();
		if(format != null)
			dateChooser.setDateFormatString(format);
		for(Component comp : dateChooser.getComponents())
			if(comp instanceof JSpinner)
				((JSpinner)comp).setFont(LookProvider.getLook().getFieldsFont());
		cleanValue();
	}

}
