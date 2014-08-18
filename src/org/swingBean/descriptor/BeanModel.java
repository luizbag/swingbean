package org.swingBean.descriptor;

public interface BeanModel {   
	
	public String getColProperty(int col);
	
	public int getPropertyCol(String property);
	
	public TableFieldDescriptor getDescriptor();
	
	public Object getValueAt(int row, int col);

}
