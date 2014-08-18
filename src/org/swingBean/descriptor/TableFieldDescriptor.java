package org.swingBean.descriptor;

import org.swingBean.util.BeanUtils;

public class TableFieldDescriptor extends GenericFieldDescriptor {   

	public TableFieldDescriptor(Class beanClass) {
		this(beanClass, (BeanUtils.getPropertyList(beanClass)).toArray(new String[BeanUtils.getPropertyList(beanClass).size()]));
	}

	public TableFieldDescriptor(Class beanClass, String[] propertyOrder) {
		super(beanClass,new String[][]{propertyOrder});
	}

	public int getLineLenght() {
		return 1;
	}

	public String[] getLinePropertys(int lineNumber) {
		return super.getLinePropertys(0);
	}
	
	public String[] getLinePropertys() {
		return getLinePropertys(0);
	}

	public void setPropertyOrder(String[][] propertyOrder) {
		super.setPropertyOrder(new String[][]{propertyOrder[0]});
	}
	
	public void setPropertyOrder(String[] propertyOrder) {
		super.setPropertyOrder(new String[][]{propertyOrder});
	}
	
	

}
