package org.swingBean.gui;

import org.swingBean.descriptor.BeanTableModel;

public class FormTablePipe<Bean> {   
	
	JBeanPanel<Bean> panel;
	BeanTableModel<Bean> tableModel;
	
	public FormTablePipe(JBeanPanel<Bean> panel, BeanTableModel<Bean> model) {
		this.panel = panel;
		tableModel = model;
	}
	
	public void addTableFromForm(Bean bean){
		panel.populateBean(bean);
		tableModel.addBean(bean);
		panel.cleanForm();
	}
	
	public void addFormFromTable(int tableRow){
		panel.setBean(tableModel.getBeanAt(tableRow));
	}
}
