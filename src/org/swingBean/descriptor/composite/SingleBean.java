package org.swingBean.descriptor.composite;

import java.util.*;

import javax.swing.tree.TreeNode;


public class SingleBean<Bean> implements CompositeBean<Bean> {
	
	private Bean bean;

	public SingleBean(Bean bean) {
		this.bean = bean;
	}

	public List<CompositeBean<Bean>> getChild() {
		return new ArrayList<CompositeBean<Bean>>();
	}

	public int getChildCount() {
		return 0;
	}

	public Bean getBean() {
		return bean;
	}

	public String toString() {
		return bean.toString();
	}

	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof SingleBean))
			return false;
		return bean.equals(((SingleBean<Bean>)obj).getBean());
	}

}
