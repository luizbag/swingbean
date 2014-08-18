package org.swingBean.gui.wrappers;

import javax.swing.ListModel;

public interface ArrayHandler {
	
	public void setValue(Object[] value);
	
	public Object[] getValue();
	
	public void resetComponent();
	
	public void setCompleteData(Object[] completeData);
	
	public void setCompleteData(ListModel model);
	
	public void setEnabled(boolean enable);

}
