package org.swingBean.actions;

import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.gui.JBeanPanel;

public class FilterAction extends ApplicationAction {

	private JBeanPanel panel;
	private BeanTableModel model;
	
	public FilterAction(JBeanPanel panel, BeanTableModel model) {
		this.panel = panel;
		this.model = model;
	}

	@Override
	public void execute() {
		panel.filterModel(model);
	}

}
