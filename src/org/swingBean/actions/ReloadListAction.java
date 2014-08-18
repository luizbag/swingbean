package org.swingBean.actions;

import org.swingBean.gui.JBeanPanel;

public class ReloadListAction extends ApplicationAction {

	private JBeanPanel panel;
	private String property;
	
	public ReloadListAction(JBeanPanel panel, String property) {
		super();
		this.panel = panel;
		this.property = property;
	}

	public void execute() {
		panel.reloadList(property);
	}

}
