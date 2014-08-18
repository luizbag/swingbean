package org.swingBean.gui.custom.checkboxlist;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.gui.wrappers.ArrayHandler;

public class CheckBoxList extends JList implements ArrayHandler{

	private CheckListener lst; 
	
	public CheckBoxList() {
		super();
		setFont(LookProvider.getLook().getFieldsFont());
		CheckListCellRenderer renderer = new CheckListCellRenderer();
		setCellRenderer(renderer);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lst = new CheckListener(this);
		addMouseListener(lst);
		addKeyListener(lst);
	}

	public void setCompleteData(Object[] list){
		DefaultListModel model = new DefaultListModel();
		for(Object item : list)
			model.addElement(new RowData(item));
		setModel(model);
	}
	
	public void setCompleteData(ListModel model) {
		Object[] completeData = new Object[model.getSize()];
		for(int i=0;i<completeData.length;i++)
			completeData[i] = model.getElementAt(i);
		setCompleteData(completeData);
	}
	
	public void setValue(Object[] value){
		for(int i=0;i<getModel().getSize();i++){
			RowData obj = (RowData)getModel().getElementAt(i);
			for(Object compare :  value)
				if(compare.equals(obj.getData())){
					obj.setSelected(true);
					repaint();
					break;
				}
		}
	}
	
	public Object[] getValue(){
		
		List list = new ArrayList();
		for (int k = 0; k < getModel().getSize(); k++) {
			RowData row = (RowData)getModel().getElementAt(k);
			if (row.isSelected())
				list.add(row.getData());
		}
		
		Object[] returnArray = new Object[list.size()];
		for(int i=0;i<list.size();i++)
			returnArray[i] = list.get(i);
		
		return returnArray;
	}

	public void resetComponent() {
		for(int i=0;i<getModel().getSize();i++){
			RowData obj = (RowData)getModel().getElementAt(i);
			obj.setSelected(false);
			repaint();
		}
	}
	
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if(enabled){
			addMouseListener(lst);
			addKeyListener(lst);
		}else{
			removeMouseListener(lst);
			removeKeyListener(lst);
		}
	}
	
	
}

