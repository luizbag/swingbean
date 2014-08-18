package org.swingBean.descriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.swingBean.binding.TableBindingList;
import org.swingBean.util.BeanUtils;
import org.swingBean.util.PropertyComparator;

import com.jgoodies.validation.ValidationResult;

public class BeanTableModel<Bean> extends AbstractTableModel implements BeanModel,TableStateHolder<Bean>{
	
	private static final long serialVersionUID = 1L;
	
	private List<Bean> beanList;
	private List<Bean> completeBeanList;
	private List<Bean> inserted;
	private List<Bean> deleted;
	private List<Bean> updated;
	private Map<String,Boolean> disabled;
	private TableFieldDescriptor descriptor;
	private Class beanClass;
	private Comparator comparator;
	private int lastIndexFound;
	private String lastValueSearched;
	private boolean continuousFiltering;

	public BeanTableModel(Class beanClass) {
		super();
		beanList = new ArrayList<Bean>();
		completeBeanList = new ArrayList<Bean>();
		descriptor = new TableFieldDescriptor(beanClass);
		this.beanClass = beanClass;
		disabled = new HashMap<String,Boolean>();
		resetCounters();
	}
	
	public BeanTableModel(TableFieldDescriptor descriptor) {
		super();
		beanList = new ArrayList<Bean>();
		completeBeanList = new ArrayList<Bean>();
		this.descriptor = descriptor;
		this.beanClass = descriptor.getBeanClass();
		disabled = new HashMap<String,Boolean>();
		resetCounters();
	}

	public int getRowCount() {
		return beanList.size();
	}

	public int getColumnCount() {
		return descriptor.getLinePropertys().length;
	}

	public String getColumnName(int col) {
		return  descriptor.getParameter(getColProperty(col),"label");
	}

	public Class<?> getColumnClass(int col) {
		return BeanUtils.getPropertyClass(beanClass,getColProperty(col));
	}

	public boolean isCellEditable(int row, int col) {
		String id = row+","+col;
		if(disabled.containsKey(id))
			return disabled.get(id);
		if(descriptor.getParameter(getColProperty(col),"readOnly") != null && descriptor.getParameter(getColProperty(col),"readOnly").equals("true"))
			return false;
		return true;
	}

	public Object getValueAt(int row, int col) {
		return getValueAt(row, getColProperty(col));
	}
	
	public Object getValueAt(int row, String property) {
		return BeanUtils.getProperty(beanList.get(row),property);
	}

	public void setValueAt(Object value, int row, int col) {
		
		if(row >= beanList.size())
			return;
		
		if(value != null && value.equals(BeanUtils.getProperty(beanList.get(row),getColProperty(col))))
			return;
		
		BeanUtils.setProperty(beanList.get(row),getColProperty(col),value);
		if(comparator != null)
			Collections.sort(beanList, comparator);
		
		for(String dependentProperty : descriptor.getDependentList()){
			if(descriptor.getDependentProperty(dependentProperty).equals(getColProperty(col))){
				int dependenceCol = getPropertyCol(dependentProperty);
				setValueAt(null ,row, dependenceCol);
			}
		}
		if(!inserted.contains(beanList.get(row)) && !updated.contains(beanList.get(row)))
			updated.add(beanList.get(row));
		
		fireTableDataChanged();
	}
	
	public void setBeanList(List<Bean> list){
		beanList.clear();
		beanList.addAll(list);
		completeBeanList.clear();
		completeBeanList.addAll(list);
		resetCounters();
		
		if(comparator != null)
			Collections.sort(beanList, comparator);
		
		fireTableDataChanged();
	}
	
	public void addBean(Bean bean){
		beanList.add(bean);
		completeBeanList.add(bean);
		inserted.add(bean);
		if(comparator != null){
			Collections.sort(beanList, comparator);
			fireTableDataChanged();
		} else {
			fireTableRowsInserted(beanList.size()-1,beanList.size());
		}
	}
	
	public void addBeanNoRecord(Bean bean){
		beanList.add(bean);
		completeBeanList.add(bean);
		if(comparator != null){
			Collections.sort(beanList, comparator);
			fireTableDataChanged();
		} else {
			fireTableRowsInserted(beanList.size()-1,beanList.size());
		}
	}
	
	public void addBeanAt(int index, Bean bean){
		beanList.add(index, bean);
		completeBeanList.add(index, bean);
		inserted.add(bean);
		if(comparator != null){
			Collections.sort(beanList, comparator);
			fireTableDataChanged();
		} else {
			fireTableRowsInserted(beanList.size()-1,beanList.size());
		}
	}

	public void updateBeanAt(int index, Bean bean){
		for(int i=0;i< completeBeanList.size(); i++){
			Bean listBean = completeBeanList.get(i);
			if(listBean.equals(bean)){
				beanList.remove(listBean);
				completeBeanList.remove(listBean);
				if(inserted.contains(listBean)){
					inserted.remove(listBean);
					inserted.add(bean);
				}else{
					updated.remove(listBean);
					updated.add(bean);
				}
				break;
			}
		}
		beanList.add(index, bean);
		completeBeanList.add(index, bean);
		if(comparator != null){
			Collections.sort(beanList, comparator);
		}
		fireTableDataChanged();
	}

	
	public void updateBean(Bean bean){
		for(int i=0;i< completeBeanList.size(); i++){
			Bean listBean = completeBeanList.get(i);
			if(listBean.equals(bean)){
				beanList.remove(listBean);
				completeBeanList.remove(listBean);
				if(inserted.contains(listBean)){
					inserted.remove(listBean);
					inserted.add(bean);
				}else{
					updated.remove(listBean);
					updated.add(bean);
				}
				break;
			}
		}
		beanList.add(bean);
		completeBeanList.add(bean);
		if(comparator != null){
			Collections.sort(beanList, comparator);
		}
		fireTableDataChanged();
	}
	
	public void updateBeanNoRecord(Bean bean){
		int i;
		for(i=0;i< completeBeanList.size(); i++){
			Bean listBean = completeBeanList.get(i);
			if(listBean.equals(bean)){
				beanList.remove(listBean);
				completeBeanList.remove(listBean);
				if(inserted.contains(listBean)){
					inserted.remove(listBean);
				}else{
					updated.remove(listBean);
				}
				break;
			}
		}
		beanList.add(i, bean);
		completeBeanList.add(bean);
		if(comparator != null){
			Collections.sort(beanList, comparator);
		}
		fireTableDataChanged();
	}
	
	public Bean getBeanAt(int index){
		return beanList.get(index);
	}
	
	public String getColProperty(int col){
		return descriptor.getLinePropertys()[col];
	}
	
	public int getPropertyCol(String property){
		for(int i = 0; i<descriptor.getLinePropertys().length; i++)
			if(property.equals(descriptor.getLinePropertys()[i]))
				return i;
		return -1;
	}

	public TableFieldDescriptor getDescriptor() {
		return descriptor;
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

	public void deleteBeanAt(int i) {
		if(inserted.contains(beanList.get(i))){
			inserted.remove(beanList.get(i));
		}else{
			if(updated.contains(beanList.get(i)))
				updated.remove(beanList.get(i));
			deleted.add(beanList.get(i));
		}
		completeBeanList.remove(beanList.get(i));
		beanList.remove(i);
		fireTableRowsDeleted(i,i);
	}
	
	public void deleteBeanNoRecord(Bean bean) {
		if(updated.contains(bean))
			updated.remove(bean);
		deleted.remove(bean);
		completeBeanList.remove(bean);
		beanList.remove(bean);
		fireTableDataChanged();
	}
	
	public void resetCounters(){
		inserted = new ArrayList<Bean>();
		deleted = new ArrayList<Bean>();
		updated = new ArrayList<Bean>();
	}
	
	public ValidationResult getValidationResult(int row){
		Bean bean = getBeanAt(row);
		return descriptor.validate(bean);
	}
	
	public List<Bean> getCompleteList(){
		return completeBeanList;
	}
	
	public List<Bean> getFilteredList(){
		return beanList;
	}
	
	public void setEnabledCell(int row, int col, boolean enabled){
		String id = row+","+col;
		disabled.put(id,enabled);
	}
	
	public void orderByProperty(String property){
		comparator = new PropertyComparator(property);
		Collections.sort(beanList, comparator);
		fireTableDataChanged();
	}
	
	public void filterStartedBy(String property, String value){
		List<Bean> filterBaseList = new ArrayList<Bean> ();
		if(continuousFiltering)
			filterBaseList.addAll(beanList);
		else
			filterBaseList.addAll(completeBeanList);
		
		value = value.toUpperCase();
		beanList.clear();
		for(Bean bean : filterBaseList){
			Object prop = BeanUtils.getProperty(bean,property);
			if(prop != null && prop.toString().toUpperCase().startsWith(value)){
				beanList.add(bean);
			}
		}
		if(comparator != null)
			Collections.sort(beanList, comparator);
		fireTableDataChanged();
	}
	
	public void filterContains(String property, String value){
		List<Bean> filterBaseList = new ArrayList<Bean> ();
		if(continuousFiltering)
			filterBaseList.addAll(beanList);
		else
			filterBaseList.addAll(completeBeanList);
		
		value = value.toUpperCase();
		beanList.clear();
		for(Bean bean : filterBaseList){
			Object prop = BeanUtils.getProperty(bean,property);
			if(prop != null && prop.toString().toUpperCase().indexOf(value) >= 0){
				beanList.add(bean);
			}
		}
		if(comparator != null)
			Collections.sort(beanList, comparator);
		fireTableDataChanged();
	}
	
	public void filterBetween(String property, Comparable begin, Comparable end){
		List<Bean> filterBaseList = new ArrayList<Bean> ();
		if(continuousFiltering)
			filterBaseList.addAll(beanList);
		else
			filterBaseList.addAll(completeBeanList);
		
		beanList.clear();
		for(Bean bean : filterBaseList){
			Object prop = BeanUtils.getProperty(bean,property);
			boolean beginCodition = (begin == null) || (prop != null  && begin.compareTo(prop)<=0);
			boolean endCondition = (end == null) || (prop != null  && end.compareTo(prop)>=0);
			if(beginCodition && endCondition){
				beanList.add(bean);
			}
		}
		if(comparator != null)
			Collections.sort(beanList, comparator);
		fireTableDataChanged();
		
	}
	
	public void filterEquals(String property, Object toCompare){
		List<Bean> filterBaseList = new ArrayList<Bean> ();
		if(continuousFiltering)
			filterBaseList.addAll(beanList);
		else
			filterBaseList.addAll(completeBeanList);
		
		beanList.clear();
		
		for(Bean bean : filterBaseList){
			Object prop = BeanUtils.getProperty(bean,property);
			if(prop != null && toCompare.equals(prop)){
				beanList.add(bean);
			}
		}
		if(comparator != null)
			Collections.sort(beanList, comparator);
		fireTableDataChanged();
	}
	
	public void cleanFilter(){
		beanList.clear();
		beanList.addAll(completeBeanList);
		if(comparator != null)
			Collections.sort(beanList, comparator);
		fireTableDataChanged();
	}
	
	public int getIndexStartedBy(String property, String value){
		value = value.toUpperCase();
		
		if(!value.equals(lastValueSearched)){
			lastIndexFound = 0;
			lastValueSearched = value;
		}
		
		int index = 0;
		int firstIndex = -1;
		
		for(Bean bean : beanList){
			Object prop = BeanUtils.getProperty(bean,property);
			if(prop != null && prop.toString().toUpperCase().startsWith(value)){
				if(firstIndex == -1)
					firstIndex = index;
				if(index > lastIndexFound){
					lastIndexFound = index;
					return index;
				}else{
					index++;
				}
			}else{
				index++;
			}
		}
		lastIndexFound = firstIndex;
		return firstIndex;
	}
	
	public int getIndexContains(String property, String value){
		value = value.toUpperCase();
		
		if(!value.equals(lastValueSearched)){
			lastIndexFound = 0;
			lastValueSearched = value;
		}
		
		int index = 0;
		int firstIndex = -1;
		
		for(Bean bean : beanList){
			Object prop = BeanUtils.getProperty(bean,property);
			if(prop != null && prop.toString().toUpperCase().indexOf(value) >= 0){
				if(firstIndex == -1)
					firstIndex = index;
				if(index > lastIndexFound){
					lastIndexFound = index;
					return index;
				}else{
					index++;
				}
			}else{
				index++;
			}
		}
		lastIndexFound = firstIndex;
		return firstIndex;
	}

	public boolean isContinuousFiltering() {
		return continuousFiltering;
	}

	public void setContinuousFiltering(boolean continuousFiltering) {
		this.continuousFiltering = continuousFiltering;
	}
	
	public List<Bean> getSynchronizedList(){
		return new TableBindingList<Bean>(this);
	}

}
