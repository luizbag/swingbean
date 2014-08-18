package org.swingBean.actions;

import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.gui.JBeanTable;
import org.swingBean.gui.wrappers.WrapperCellEditor;

public abstract class ColumnAction extends ApplicationAction{

	private int row;
	private BeanTableModel model;
	private WrapperCellEditor wrapper;
	private JBeanTable table;

	public JBeanTable getTable() {
		return table;
	}

	public void setTable(JBeanTable table) {
		this.table = table;
	}

	public BeanTableModel getModel() {
		return model;
	}

	public void setModel(BeanTableModel model) {
		this.model = model;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public WrapperCellEditor getWrapper() {
		return wrapper;
	}

	public void setWrapper(WrapperCellEditor wrapper) {
		this.wrapper = wrapper;
	}
	
	
}
