package org.swingBean.descriptor.composite;

import java.util.ArrayList;
import java.util.List;


public class GroupBean<Bean> implements CompositeBean<Bean> {
	
	private String groupName;
	private String groupProp;
	private GroupBean parentComposite;
	private List<CompositeBean<Bean>> list;
	private Bean bean;

	public String getGroupName() {
		return groupName;
	}

	public GroupBean(String name) {
		groupName = name;
		list = new ArrayList<CompositeBean<Bean>>();
	}
	
	public GroupBean(String name, String prop) {
		groupName = name;
		groupProp = prop;
		list = new ArrayList<CompositeBean<Bean>>();
	}
	
	public GroupBean(Bean bean) {
		this.bean = bean;
		list = new ArrayList<CompositeBean<Bean>>();
	}

	public List<CompositeBean<Bean>> getChild() {
		return list;
	}

	public int getChildCount() {
		return list.size();
	}

	public Bean getBean() {
		return bean;
	}
	
	public void addChild(CompositeBean<Bean> composite){
		list.add(composite);
		if(composite instanceof GroupBean)
			((GroupBean)composite).setParent(this);
	}

	public String toString() {
		if(groupName == null)
			return bean.toString();
		return groupName;
	}

	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof GroupBean))
			return false;
		if(groupName == null)
			return bean.equals(((GroupBean)obj).getBean());
		else
			return groupName.equals(obj.toString());
	}
	
	public CompositeBean<Bean> getChild(String name){
		for(CompositeBean<Bean> composite : list)
			if(composite instanceof GroupBean && composite.toString().equals(name))
				return composite;
		return null;
	}
	
	public CompositeBean<Bean> getChild(Bean bean){
		for(CompositeBean<Bean> composite : list)
			if(composite.getBean().equals(bean))
				return composite;
		return null;
	}

	public String getGroupProp() {
		return groupProp;
	}

	public GroupBean getParent() {
		return parentComposite;
	}

	public void setParent(GroupBean parentComposite) {
		this.parentComposite = parentComposite;
	}
	
}
