package org.swingBean.descriptor.composite;

import java.util.Map;

import javax.swing.tree.TreePath;

public interface CompositeTree<Bean> {

	public abstract void addChild(Bean bean);

	public abstract void removeChild(Bean bean);

	public abstract Map<String, Object> getPropertys(CompositeBean composite);

	public abstract TreePath getLastPath();
	
	public abstract CompositeBean<Bean> getRoot();

}