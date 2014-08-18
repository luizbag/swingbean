package org.swingBean.descriptor.composite;

import java.util.List;

import javax.swing.tree.TreeNode;

public interface CompositeBean<Bean>{
	
	public List<CompositeBean<Bean>> getChild();
	
	public int getChildCount();
	
	public Bean getBean();
	
}
