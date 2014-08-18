package org.swingBean.gui.wrappers;

import java.awt.Component;

public interface ComponentWrapper {

	public Object getValue();
	
	public void setValue(Object value);
	
	public void cleanValue();
	
	public Component getComponent();
	
	public void setEnable(boolean enable);
	
	public void initComponent() throws Exception;

}
