package org.swingBean.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.swingBean.descriptor.composite.CompositeBean;
import org.swingBean.descriptor.composite.CompositeTree;
import org.swingBean.descriptor.composite.TreeFactory;
import org.swingBean.gui.JBeanPanel;
import org.swingBean.gui.custom.treetable.AbstractTreeTableModel;
import org.swingBean.gui.custom.treetable.TreeTableModel;
import org.swingBean.util.BeanUtils;

public class BeanTreeTableModel<Bean> extends AbstractTreeTableModel implements BeanModel, TableStateHolder<Bean>{
	
	private TableFieldDescriptor descriptor;
	private Class beanClass;
	private CompositeTree<Bean> tree;
	private List<Bean> inserted;
	private List<Bean> deleted;
	private List<Bean> updated;

	public BeanTreeTableModel(CompositeBean<Bean> root, TableFieldDescriptor descriptor, Class beanClass) {
		super(root);
		this.descriptor = descriptor;
		this.beanClass = beanClass;
		resetCounters();
	}
	
	public BeanTreeTableModel(TableFieldDescriptor descriptor, Class<Bean> beanClass, List<Bean> beanList) {
		tree = getTree(descriptor, beanClass, beanList);
		root = tree.getRoot();
		this.descriptor = descriptor;
		this.beanClass = beanClass;
		resetCounters();
	}
	
	private CompositeTree<Bean> getTree(TableFieldDescriptor descriptor, Class<Bean> beanClass, List<Bean> beanList){
		String rootName = descriptor.getParameter(descriptor.getLinePropertys()[0],"rootName");
		TreeFactory<Bean> factory =  new TreeFactory<Bean>();
		if(descriptor.getParameter(descriptor.getLinePropertys()[0],"classifyBy") != null){
			String[] propertys = descriptor.getParameter(descriptor.getLinePropertys()[0],"classifyBy").split(";");
			return factory.constructOrganizationalTree(rootName, beanList, propertys);
		}else{
			String idProperty = descriptor.getParameter(descriptor.getLinePropertys()[0],"idProperty");
			String parentProperty = descriptor.getParameter(descriptor.getLinePropertys()[0],"parentProperty");
			return factory.constructInheritedTree(rootName,idProperty,parentProperty,beanList);
		}
	}

	public int getColumnCount() {
		return descriptor.getLinePropertys().length;
	}

	public String getColumnName(int col) {
		return descriptor.getParameter(getColProperty(col),"label");
	}

	public Object getValueAt(Object node, int col) {
		if(col == 0)
			return node.toString();
		else 
			if (((CompositeBean<Bean>)node).getBean() != null){
				Bean bean = ((CompositeBean<Bean>)node).getBean();
				return BeanUtils.getProperty(bean,getColProperty(col));
			}
		return "";
	}

	public Object getChild(Object node, int index) {
		return ((CompositeBean<Bean>)node).getChild().get(index);
	}

	public int getChildCount(Object node) {
		return ((CompositeBean<Bean>)node).getChildCount();
	}

	public Class getColumnClass(int col) {
		if(col == 0)
			return TreeTableModel.class;
		return BeanUtils.getPropertyClass(beanClass,getColProperty(col));
	}
	
	public String getColProperty(int col){
		return descriptor.getLinePropertys()[col];
	}

	public boolean isLeaf(Object node) {
		return ((CompositeBean<Bean>) node).getChildCount() == 0;
	}

	public int getPropertyCol(String property) {
		for(int i = 0; i<descriptor.getLinePropertys().length; i++)
			if(property.equals(descriptor.getLinePropertys()[i]))
				return i;
		return 0;
	}

	public TableFieldDescriptor getDescriptor() {
		return descriptor;
	}

	public Object getValueAt(int row, int col) {
		return null;
	}

	public boolean isCellEditable(Object node, int col) {
		if(descriptor.getParameter(getColProperty(col),"readOnly") != null && descriptor.getParameter(getColProperty(col),"readOnly").equals("true"))
			return false;
		return true;
	}

	public void setValueAt(Object value, Object node, int col) {
		Bean bean = ((CompositeBean<Bean>)node).getBean();
		if(value != null && value.equals(BeanUtils.getProperty(bean,getColProperty(col))))
			return;
		BeanUtils.setProperty(bean,getColProperty(col),value);
		if(!inserted.contains(bean))
			updated.add(bean);
	}
	
	public void addBean(Bean bean){
		tree.addChild(bean);
		inserted.add(bean);
		fireTreeChanged(this,tree.getLastPath());
	}
	
	public void removeBean(Bean bean){
		if(bean == null)
			return;
		if(inserted.contains(bean)){
			inserted.remove(bean);
		}else{
			if(updated.contains(bean))
				updated.remove(bean);
			deleted.add(bean);
		}
		tree.removeChild(bean);
		fireTreeChanged(this,tree.getLastPath());
	}
	
	public void resetCounters(){
		inserted = new ArrayList<Bean>();
		deleted = new ArrayList<Bean>();
		updated = new ArrayList<Bean>();
	}
	
	public List<Bean> getInserted() {
		return inserted;
	}

	public List<Bean> getDeleted() {
		return deleted;
	}

	public List<Bean> getUpdated() {
		return updated;
	}
	
	public void setBeanPath(JBeanPanel panel, CompositeBean composite){
		Map<String,Object> propertys = tree.getPropertys(composite);
		for(String property: propertys.keySet())
			panel.setPropertyValue(property,propertys.get(property));
	}
	
	public void setBeanPath(Bean bean, CompositeBean composite){
		Map<String,Object> propertys = tree.getPropertys(composite);
		for(String property: propertys.keySet())
			BeanUtils.setProperty(bean,property,propertys.get(property));
	}
	
}
