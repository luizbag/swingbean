package org.swingBean.actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.TableColumnModel;

import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.gui.JBeanTable;

public class ColumnOrderListener extends MouseAdapter {
	
	private JBeanTable table;
	
	public ColumnOrderListener(JBeanTable table) {
		this.table = table;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		TableColumnModel model = table.getColumnModel();
		int index = model.getColumnIndexAtX(e.getX());
		
		if(e.getClickCount() == 2){
			BeanTableModel tableModel = ((BeanTableModel)table.getModel());
			String property = tableModel.getColProperty(index);
			tableModel.orderByProperty(property);
		}
	}

}
