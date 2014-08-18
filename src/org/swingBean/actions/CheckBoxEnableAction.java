package org.swingBean.actions;

import org.swingBean.gui.JBeanPanel;

public class CheckBoxEnableAction extends SetEnableFieldsAction {

	public CheckBoxEnableAction(JBeanPanel panel, String field, String... fields) {
		super(panel, field, fields);
	}

	public boolean getEnabled(Object value) {
		return (Boolean)value;
	}

}
