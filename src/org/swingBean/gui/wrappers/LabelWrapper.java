package org.swingBean.gui.wrappers;

import java.awt.Component;

import javax.swing.JLabel;

import org.swingBean.descriptor.look.LookProvider;

public class LabelWrapper implements ComponentWrapper {

	private JLabel label;

	public Object getValue() {
		return label.getText();
	}

	public void setValue(Object value) {
		label.setText(value.toString());
	}

	public void cleanValue() {
		label.setText("");
	}

	public Component getComponent() {
		return label;
	}

	public void setEnable(boolean enable) {
		label.setEnabled(enable);
	}

	public void initComponent() throws Exception {
		label = LookProvider.getLook().createFieldLabel("");
	}

}
