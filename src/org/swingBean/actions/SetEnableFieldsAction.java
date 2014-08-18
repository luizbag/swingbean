package org.swingBean.actions;

import org.swingBean.gui.JBeanPanel;

public abstract class SetEnableFieldsAction extends ApplicationAction {

	protected JBeanPanel panel;
	protected String[] fields;
	protected String actionField;
	
	public SetEnableFieldsAction(JBeanPanel panel, String field, String... fields) {
		actionField = field;
		this.fields = fields;
		this.panel = panel;
		execute();
	}

	public void execute() {
		boolean enabled = getEnabled(panel.getPropertyValue(actionField));
		for(String field : fields)
			panel.setEnable(field,enabled);
	}
	
	public abstract boolean getEnabled(Object value);

}
