package org.swingBean.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.swingBean.actions.ActionMouseAdapter;
import org.swingBean.actions.ApplicationAction;
import org.swingBean.actions.ColumnAction;
import org.swingBean.actions.ColumnOrderListener;
import org.swingBean.actions.ListenerActionExecuter;
import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.exception.ComponentExecutionException;
import org.swingBean.gui.wrappers.WrapperCellEditor;

import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;

public class JBeanTable extends JTable {
	
	private Map<String,WrapperCellEditor> compWrappers;
	private Map<Integer,Class> columnActions;
	private int row = 0;
	private int col = -1;
	private KeyListener listener;
	private boolean validateErrors;
	

	public JBeanTable(BeanTableModel model) {
		super(model);
		compWrappers = new TreeMap<String,WrapperCellEditor>();
		columnActions = new HashMap<Integer,Class>();
		
		for(int i=0;i<model.getColumnCount();i++){
			String property = model.getColProperty(i);
			String size = model.getDescriptor().getParameter(property,"columnSize");
			if(size != null){
				getColumnModel().getColumn(i).setPreferredWidth(Integer.parseInt(size));
			}
		}
	}

	public TableCellEditor getCellEditor(int row, int col) {
		this.row = row;
		this.col = col;
		String id = row+","+col;
		if(!compWrappers.containsKey(id))
			compWrappers.put(id,new WrapperCellEditor((BeanTableModel)getModel(), listener));
		WrapperCellEditor wrapper = compWrappers.get(id);
		return wrapper;
	}

	public TableCellRenderer getCellRenderer(int row, int col) {
		if(getColumnModel().getColumn(col).getCellRenderer() != null)
			return getColumnModel().getColumn(col).getCellRenderer();
		String id = row+","+col;
		if(!compWrappers.containsKey(id)){
			WrapperCellEditor wrapper =new WrapperCellEditor((BeanTableModel)getModel(), listener);
			if(validateErrors){
				ValidationResult result = ((BeanTableModel)getModel()).getValidationResult(row);
				boolean hasError = result.getErrors().size() > 0;
				wrapper.setHasError(hasError);
			}
			compWrappers.put(id,wrapper);
			ActionListener listener = getActionListener(row, col, wrapper);
			if(listener != null){
				wrapper.setListener(listener);
			}
		}
		WrapperCellEditor wrapper = compWrappers.get(id);
		return wrapper;
	}
	
	private ActionListener getActionListener(int row, int col, WrapperCellEditor wrapper){
		ColumnAction action;
		if(!columnActions.containsKey(col)){
			return null;
		}
		try {
			action = (ColumnAction) columnActions.get(col).newInstance();
		} catch (Exception e) {
			throw new ComponentExecutionException("Can't create the action listener",e);
		}
		action.setRow(row);
		action.setModel((BeanTableModel)getModel());
		action.setWrapper(wrapper);
		action.setTable(this);
		return new ListenerActionExecuter(action);
	}
	
	public void associateActionToColumn(Class actionClass, int col){
		columnActions.put(col,actionClass);
	}
	
	public void addDoubleClickAction(ApplicationAction action){
		addMouseListener(new ActionMouseAdapter(action));
	}
	
	public void enableHeaderOrdering(){
		getTableHeader().addMouseListener(new ColumnOrderListener(this));
	}

	public void editNextField(){
		int col = this.col;
		int row = this.row;
		editingStopped(null);
		col++;
		if(col == getModel().getColumnCount()){
			col = 0;
			row++;
			if(row == getModel().getRowCount()){
				row = 0;				
			}
		}
		editCellAt(row,col);
		getSelectionModel().clearSelection();
		getSelectionModel().addSelectionInterval(row,row);
	}
	
	public void enableQuickEditing(){
		listener = new TableKeyListener(this);
		addKeyListener(listener);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		if(validateErrors){
			for(Integer row = e.getFirstRow();row < Math.min(e.getLastRow(),getModel().getRowCount());row++){
				ValidationResult result = ((BeanTableModel)getModel()).getValidationResult(row);
				boolean hasError = result.getErrors().size() > 0;
				for(String str :compWrappers.keySet()){
					if(str.startsWith(row.toString()+",")){
						compWrappers.get(str).setHasError(hasError);
					}
				}
			}
		}
		synchronized (getTreeLock()) {
			super.tableChanged(e);
		}
	}

	public void enableValidateErrors() {
		validateErrors = true;
	}
	
	public Component prepareRenderer(TableCellRenderer renderer, int rowIndex,
			int vColIndex) {
		Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
		if (c instanceof JComponent) {
			JComponent jc = (JComponent) c;
			BeanTableModel model = (BeanTableModel)getModel();
			ValidationResult result = model.getValidationResult(rowIndex);
			if(result.hasErrors()){
				String toolTipText = "";
				for(Object message : result.getMessages()){
					ValidationMessage vm = (ValidationMessage)message;
					toolTipText = toolTipText + vm.formattedText() + "\n";
				}
				jc.setToolTipText(toolTipText);
			}
		}
		return c;
	}
	
	public JToolTip createToolTip() {
        MultiLineToolTip tip = new MultiLineToolTip();
        tip.setComponent(this);
        return tip;
    }
	
	public void resetComboValues(String property){
		int col = ((BeanTableModel)getModel()).getPropertyCol(property);
		Set<String> toRemove =  new HashSet<String>();
		for(String key : compWrappers.keySet()){
			if(key.endsWith(Integer.toString(col))){
				toRemove.add(key);
			}
		}
		for(String key :  toRemove){
			compWrappers.remove(key);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		synchronized (getTreeLock()) {
			super.paintComponent(g);
		}
	}
}
