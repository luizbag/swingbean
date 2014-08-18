package org.swingBean.descriptor.composite;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreePath;

import org.swingBean.util.BeanUtils;

public class OrganizedTree<Bean> implements CompositeTree<Bean>  {
	
	private String[] organizedPropertys;
	private TreePath lastPath;
	private GroupBean<Bean> theRoot;
	
	public OrganizedTree(String[] propertys, GroupBean<Bean> root) {
		organizedPropertys = propertys;
		theRoot = root;
	}

	public void addChild(Bean bean) {
		lastPath = new TreePath(theRoot);
		String[] property = new String[organizedPropertys.length];
		for(int i=0;i<organizedPropertys.length;i++)
			property[i] = BeanUtils.getProperty(bean,organizedPropertys[i]).toString();
		GroupBean<Bean> currentGroup = theRoot;
		for(int i=0;i<property.length;i++){
			if(currentGroup.getChild(property[i]) == null){
				GroupBean<Bean> newGroup = new GroupBean<Bean>(property[i],organizedPropertys[i]);
				currentGroup.addChild(newGroup);
				currentGroup = newGroup;
			}else{
				currentGroup =(GroupBean<Bean>)currentGroup.getChild(property[i]);
				lastPath = lastPath.pathByAddingChild(currentGroup);
			}
		}
		currentGroup.addChild(new SingleBean<Bean>(bean));
	}

	public void removeChild(Bean bean) {
		lastPath = new TreePath(theRoot);
		String[] property = new String[organizedPropertys.length];
		for(int i=0;i<organizedPropertys.length;i++)
			property[i] = BeanUtils.getProperty(bean,organizedPropertys[i]).toString();
		GroupBean<Bean> currentGroup = theRoot;
		for(String prop : property){
			currentGroup =(GroupBean<Bean>)currentGroup.getChild(prop);
		}
		currentGroup.getChild().remove(new SingleBean(bean));
		while(currentGroup.getChild().size() == 0){
			GroupBean<Bean> group = currentGroup.getParent();
			group.getChild().remove(currentGroup);
			currentGroup = group;
		}
		
		currentGroup = theRoot;
		for(String prop : property){
			currentGroup =(GroupBean<Bean>)currentGroup.getChild(prop);
			if(currentGroup != null)
				lastPath = lastPath.pathByAddingChild(currentGroup);
			else
				break;
		}
	}

	public Map<String, Object> getPropertys(CompositeBean composite) {
		Map<String,Object> prop = new HashMap<String,Object>();
		if(composite instanceof SingleBean)
			for(String property :organizedPropertys)
				prop.put(property, BeanUtils.getProperty(composite.getBean(),property));
		else{
			GroupBean current = (GroupBean)composite;
			while(current != null && current.getGroupProp() != null){
				prop.put(current.getGroupProp(),current.getGroupName());
				current = current.getParent();
			}
		}
		return prop;
	}

	public TreePath getLastPath() {
		return lastPath;
	}
	
	public CompositeBean<Bean> getRoot() {
		return theRoot;
	}

}
