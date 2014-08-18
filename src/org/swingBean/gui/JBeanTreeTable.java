package org.swingBean.gui;

import java.util.Map;
import java.util.TreeMap;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.swingBean.descriptor.BeanModel;
import org.swingBean.descriptor.BeanTreeTableModel;
import org.swingBean.descriptor.composite.CompositeBean;
import org.swingBean.gui.custom.treetable.JTreeTable;
import org.swingBean.gui.custom.treetable.TreeTableModel;
import org.swingBean.gui.custom.treetable.TreeTableModelAdapter;
import org.swingBean.gui.wrappers.WrapperCellEditor;

public class JBeanTreeTable extends JTreeTable {
	
	private Map<String,WrapperCellEditor> compWrappers;
	private TreeTableModel treeTableModel;

	public JBeanTreeTable(BeanTreeTableModel treeTableModel) {
		super(treeTableModel);
		compWrappers = new TreeMap<String,WrapperCellEditor>();
		this.treeTableModel = treeTableModel;
		
		for(int i=0;i<treeTableModel.getColumnCount();i++){
			String property = treeTableModel.getColProperty(i);
			String size = treeTableModel.getDescriptor().getParameter(property,"columnSize");
			if(size != null){
				getColumnModel().getColumn(i).setPreferredWidth(Integer.parseInt(size));
			}
		}
		
	}

	public TableCellEditor getCellEditor(int row, int col) {
		if(col == 0)
			return super.getCellEditor(row,col);
		String id = row+","+col;
		if(!compWrappers.containsKey(id))
			compWrappers.put(id,new WrapperCellEditor((BeanModel)treeTableModel));
		WrapperCellEditor wrapper = compWrappers.get(id);
		return wrapper;
	}

	public TableCellRenderer getCellRenderer(int row, int col) {
		if(col == 0)
			return super.getCellRenderer(row,col);
		if(getColumnModel().getColumn(col).getCellRenderer() != null)
			return getColumnModel().getColumn(col).getCellRenderer();
		String id = row+","+col;
		if(!compWrappers.containsKey(id))
			compWrappers.put(id,new WrapperCellEditor((BeanModel)treeTableModel));
		WrapperCellEditor wrapper = compWrappers.get(id);
		return wrapper;
	}
	
	public Object getSelectedBean(){
		return ((TreeTableModelAdapter)getModel()).getBeanAtRow(getSelectedRow());
	}
	
	public CompositeBean getSelectedComposite(){
		return ((TreeTableModelAdapter)getModel()).getCompositeAtRow(getSelectedRow());
	}

}
