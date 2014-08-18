package org.swingBean.descriptor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;

import org.swingBean.descriptor.composite.*;

public class BeanTreeModel<Bean> implements TreeModel {
	
	private TableFieldDescriptor descriptor;
	private Class beanClass;
	private CompositeTree<Bean> tree;
	private Object root;
	private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
	private List<Bean> beanList;
	
	
	public BeanTreeModel(TableFieldDescriptor descriptor, Class<Bean> beanClass, List<Bean> beanList) {
		tree = getTree(descriptor, beanClass, beanList);
		root = tree.getRoot();
		this.descriptor = descriptor;
		this.beanClass = beanClass;
		this.beanList = beanList;
	}
	
	private CompositeTree<Bean> getTree(TableFieldDescriptor descriptor, Class<Bean> beanClass, List<Bean> beanList){
		String rootName = descriptor.getParameter(descriptor.getLinePropertys()[0],"rootName");
		TreeFactory<Bean> factory =  new TreeFactory<Bean>();
		if(descriptor.getParameter(descriptor.getLinePropertys()[0],"classifyBy") != null){
			String[] propertys = descriptor.getParameter(descriptor.getLinePropertys()[0],"classifyBy").split(";");
			return factory.constructOrganizationalTree(rootName, beanList, propertys);
		}else if(descriptor.getParameter(descriptor.getLinePropertys()[0],"idProperty") != null){
			String idProperty = descriptor.getParameter(descriptor.getLinePropertys()[0],"idProperty");
			String parentProperty = descriptor.getParameter(descriptor.getLinePropertys()[0],"parentProperty");
			return factory.constructInheritedTree(rootName,idProperty,parentProperty,beanList);
		}else if(descriptor.getParameter(descriptor.getLinePropertys()[0],"childrenProperty") != null){
			String childrenProperty = descriptor.getParameter(descriptor.getLinePropertys()[0],"childrenProperty");
			return factory.constructObjectTree(rootName,beanList, childrenProperty);
		}
		return null;
	}

	public Object getRoot() {
		return root;
	}

	public Object getChild(Object composite, int index) {
		return ((CompositeBean)composite).getChild().get(index);
	}

	public int getChildCount(Object composite) {
		return ((CompositeBean)composite).getChildCount();
	}

	public boolean isLeaf(Object composite) {
		if(getChildCount(composite) == 0)
			return true;
		return false;
	}

	public void valueForPathChanged(TreePath path, Object obj) {}

	public int getIndexOfChild(Object parent, Object child) {
		return ((CompositeBean)parent).getChild().indexOf(child);
	}

	public void addTreeModelListener(TreeModelListener listener) {
		listeners.add(listener);
	}

	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.remove(listener);
	}
	
	public Bean getBeanAt(TreePath path){
		return ((CompositeBean<Bean>)path.getLastPathComponent()).getBean();
	}
	
	public void addBean(Bean bean){
		tree.addChild(bean);
		for(TreeModelListener listener: listeners)
			listener.treeStructureChanged(new TreeModelEvent(this,tree.getLastPath()));
	}
	
	public void refreshTree(){
		tree = getTree(descriptor, beanClass, beanList);
		root = tree.getRoot();
		for(TreeModelListener listener: listeners)
			listener.treeStructureChanged(new TreeModelEvent(this,new TreePath(root),null,null));
	}

}
