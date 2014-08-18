package org.swingBean.descriptor.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.tree.TreePath;

import org.swingBean.util.BeanUtils;

public class InheritedTree<Bean> implements CompositeTree<Bean>  {
	
	private String parentProperty;
	private String idProperty;
	private TreePath lastPath;
	private GroupBean<Bean> theRoot;
	
	public InheritedTree(String idProperty, String parentProperty, GroupBean<Bean> root) {
		this.idProperty = idProperty;
		this.parentProperty = parentProperty;
		theRoot = root;
	}

	public void addChild(Bean bean) {
		Object parentId = BeanUtils.getProperty(bean,parentProperty);
		List<CompositeBean<Bean>> compositeList = new ArrayList<CompositeBean<Bean>>();
		GroupBean<Bean> group;;
		compositeList.add(theRoot);
		if(parentId == null || parentId.equals("")){
			theRoot.addChild(new GroupBean<Bean>(bean));
			lastPath = new TreePath(compositeList.toArray());
			return;
		}
		Stack<Integer> indexes = new Stack<Integer>();
		indexes.push(0);
		while(compositeList.size()>0){
			group = (GroupBean<Bean>)compositeList.get(compositeList.size()-1);
			if(group.getBean() != null && parentId.equals(BeanUtils.getProperty(group.getBean(),idProperty))){
				group.addChild(new GroupBean<Bean>(bean));
				break;
			}else{
				int currentIndex = indexes.peek();
				if(currentIndex < group.getChildCount()){
					indexes.push(0);
					compositeList.add(group.getChild().get(currentIndex));
				}else{
					indexes.pop();
					compositeList.remove(compositeList.size()-1);
					indexes.push(indexes.pop()+1);
				}
			}
		}
		lastPath = new TreePath(compositeList.toArray());
	}

	public void removeChild(Bean bean) {
		Object parentId = BeanUtils.getProperty(bean,parentProperty);
		List<CompositeBean<Bean>> compositeList = new ArrayList<CompositeBean<Bean>>();
		GroupBean<Bean> group;;
		compositeList.add(theRoot);
		Stack<Integer> indexes = new Stack<Integer>();
		indexes.push(0);
		while(compositeList.size()>0){
			group = (GroupBean<Bean>)compositeList.get(compositeList.size()-1);
			if(group.getBean() != null && parentId.equals(BeanUtils.getProperty(group.getBean(),idProperty))){
				group.getChild().remove(new GroupBean(bean));
				break;
			}else{
				int currentIndex = indexes.peek();
				if(currentIndex < group.getChildCount()){
					indexes.push(0);
					compositeList.add(group.getChild().get(currentIndex));
				}else{
					indexes.pop();
					compositeList.remove(compositeList.size()-1);
					indexes.push(indexes.pop()+1);
				}
			}
		}
		lastPath = new TreePath(compositeList.toArray());
	}

	public Map<String, Object> getPropertys(CompositeBean composite) {
		Map<String,Object> prop = new HashMap<String,Object>();
		if(composite.getBean() != null)
			prop.put(parentProperty,BeanUtils.getProperty(composite.getBean(),idProperty));
		return prop;
	}

	public TreePath getLastPath() {
		return lastPath;
	}

	public CompositeBean<Bean> getRoot() {
		return theRoot;
	}

}
