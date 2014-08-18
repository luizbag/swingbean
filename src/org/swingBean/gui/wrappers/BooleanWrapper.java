package org.swingBean.gui.wrappers;

import java.awt.Component;

import javax.swing.JCheckBox;

import org.swingBean.descriptor.look.LookProvider;

public class BooleanWrapper implements ComponentWrapper {
	
	private JCheckBox checkBox;
	private String label;

	public Object getValue() {
		return checkBox.isSelected();
	}

	public void setValue(Object value) {
		checkBox.setSelected((Boolean)value);
	}

	public void cleanValue() {
		checkBox.setSelected(false);
	}

	public Component getComponent() {
		return checkBox;
	}

	public void setEnable(boolean enable) {
		checkBox.setEnabled(enable);
	}

	public void initComponent() throws Exception {
		checkBox = new JCheckBox();
		checkBox.setText(label);
		checkBox.setFont(LookProvider.getLook().getTextFont());
		checkBox.setForeground(LookProvider.getLook().getTextColor());	
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
