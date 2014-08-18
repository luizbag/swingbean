package org.swingBean.descriptor.composite;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreePath;

import org.swingBean.util.BeanUtils;

public class ObjectTree<Bean> implements CompositeTree<Bean> {
	
	private String childrenProperty;
	private GroupBean<Bean> theRoot;

	public ObjectTree(String property, GroupBean<Bean> root) {
		childrenProperty = property;
		theRoot = root;
	}

	public void addChild(Bean bean) {
		addChild(theRoot, bean);
	}
	
	private void addChild(GroupBean<Bean> parent, Bean child) {
		GroupBean<Bean> group = new GroupBean<Bean>(child);
		Object list = BeanUtils.getProperty(child,childrenProperty);
		if(list != null){
			if(list.getClass().isArray()){
				Object[] array = (Object[]) list;
				for(Object bean : array)
					addChild(group, (Bean)bean);
			}
			if(list instanceof Collection){
				Collection collection = (Collection) list;
				for(Object bean : collection)
					addChild(group, (Bean)bean);
			}
		}
		parent.addChild(group);
	}

	public void removeChild(Bean bean) {
		theRoot.getChild().remove(bean);
	}

	public Map<String, Object> getPropertys(CompositeBean composite) {
		Map<String,Object> prop = new HashMap<String,Object>();
		return prop;
	}

	public TreePath getLastPath() {
		return new TreePath(theRoot);
	}

	public CompositeBean<Bean> getRoot() {
		return theRoot;
	}

}
