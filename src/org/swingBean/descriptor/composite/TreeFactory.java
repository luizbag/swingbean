package org.swingBean.descriptor.composite;

import java.util.List;

import org.swingBean.util.BeanUtils;

public class TreeFactory<Bean> {
	
	public CompositeTree<Bean> constructOrganizationalTree(String rootName,List<Bean> list, String... organized){
		
		GroupBean<Bean> root = new GroupBean<Bean>(rootName);
		OrganizedTree<Bean> tree = new OrganizedTree<Bean>(organized,root);
		
		for(Bean bean : list){
			tree.addChild(bean);
		}
		
		return tree;
	}
	
	public CompositeTree<Bean> constructObjectTree(String rootName,List<Bean> list, String childrenProperty){
		
		GroupBean<Bean> root = new GroupBean<Bean>(rootName);
		ObjectTree<Bean> tree = new ObjectTree<Bean>(childrenProperty,root);
		
		for(Bean bean : list){
			tree.addChild(bean);
		}
		
		return tree;
	}

	public CompositeTree<Bean> constructInheritedTree(String rootName, String idProperty, String parentProperty, List<Bean> list) {
		
		GroupBean<Bean> root = new GroupBean<Bean>(rootName);
		for(Bean bean : list){
			Object parentId = BeanUtils.getProperty(bean,parentProperty);
			if(parentId == null){
				GroupBean<Bean> group = new GroupBean<Bean>(bean);
				root.addChild(group);
				Object selfId = BeanUtils.getProperty(bean,idProperty);
				addChilds(group, selfId, list, parentProperty, idProperty);
			}
		}
		InheritedTree<Bean> tree = new InheritedTree<Bean>(idProperty, parentProperty, root);
		
		return tree;
	}

	private void addChilds(GroupBean<Bean> group, Object nodeId, List<Bean> list, String parentProperty, String idProperty) {
		for(Bean bean : list){
			Object parentId = BeanUtils.getProperty(bean,parentProperty);
			if(nodeId.equals(parentId)){
				Object beanId = BeanUtils.getProperty(bean,idProperty);
				GroupBean<Bean> childGroup = new GroupBean<Bean>(bean);
				group.addChild(childGroup);
				addChilds(childGroup, beanId, list, parentProperty, idProperty);
			}
		}
	}
	


}
