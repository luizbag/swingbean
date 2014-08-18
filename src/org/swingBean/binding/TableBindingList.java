package org.swingBean.binding;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.swingBean.descriptor.BeanTableModel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TableBindingList<Bean> implements List<Bean>, Serializable {
	
	private BeanTableModel<Bean> model;
	
	public TableBindingList(BeanTableModel<Bean> model) {
		this.model = model;
	}

	public boolean add(Bean o) {
		model.addBean(o);
		return true;
	}

	public void add(int index, Bean element) {
		model.addBeanAt(index,element);
	}

	public boolean addAll(Collection<? extends Bean> c) {
		for(Bean b:c)
			add(b);
		return true;
	}

	public boolean addAll(int index, Collection<? extends Bean> c) {
		for(Bean b:c){
			add(index,b);
			index++;
		}
		return true;
	}

	public void clear() {
		model.setBeanList(new ArrayList<Bean>());		
	}

	public boolean contains(Object o) {
		return model.getFilteredList().contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return model.getFilteredList().containsAll(c);
	}

	public Bean get(int index) {
		return (Bean)BeanTableBinding.createSynchonizedBean(model.getBeanAt(index), index, model);
	}

	public int indexOf(Object o) {
		return model.getFilteredList().indexOf(o);
	}

	public boolean isEmpty() {
		return model.getRowCount() != 0;
	}

	public Iterator<Bean> iterator() {
		throw new NotImplementedException();
	}

	public int lastIndexOf(Object o) {
		return model.getFilteredList().lastIndexOf(o);
	}

	public ListIterator<Bean> listIterator() {
		throw new NotImplementedException();
	}

	public ListIterator<Bean> listIterator(int index) {
		throw new NotImplementedException();
	}

	public boolean remove(Object o) {
		if(o instanceof BeanRetriever)
			o = ((BeanRetriever)o).retrieveBean();
		model.deleteBeanNoRecord((Bean)o);
		return true;
	}

	public Bean remove(int index) {
		Bean bean = model.getBeanAt(index);
		model.deleteBeanAt(index);
		return bean;
	}

	public boolean removeAll(Collection<?> c) {
		throw new NotImplementedException();
	}

	public boolean retainAll(Collection<?> c) {
		throw new NotImplementedException();
	}

	public Bean set(int index, Bean element) {
		Bean returned = remove(index);
		add(index,element);
		return returned;
	}

	public int size() {
		return model.getRowCount();
	}

	public List<Bean> subList(int fromIndex, int toIndex) {
		throw new NotImplementedException();
	}

	public Object[] toArray() {
		throw new NotImplementedException();
	}

	public <T> T[] toArray(T[] a) {
		throw new NotImplementedException();
	}
	
	public Object writeReplace() throws ObjectStreamException {
		return model.getFilteredList();
	}

}
