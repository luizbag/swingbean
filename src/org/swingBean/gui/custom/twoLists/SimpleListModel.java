package org.swingBean.gui.custom.twoLists;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractListModel;

public class SimpleListModel extends AbstractListModel {
	
	private ArrayList list = new ArrayList();
	
	public Object getElementAt(int index) {
		synchronized (list) {
			if(list.size() > index)
				return list.get(index);
			return null;
		}
	}

	public int getSize() {
		synchronized (list) {
			return list.size();
		}
	}
	
	public void addElement(Object element) {
		synchronized (list) {
			list.add(element);
			if(element instanceof Comparable)
				Collections.sort(list);
			fireContentsChanged(this,list.size(),list.size());
		}
	}
	
	public void removeElement(Object element){
		synchronized (list) {
			int index = list.indexOf(element);
			list.remove(element);
			fireIntervalRemoved(this,index,index);
		}
	}
	
	public void clear(){
		synchronized (list) {
			int size = list.size();
			list.clear();
			if(size > 0)
				fireIntervalRemoved(this,0,size-1);
		}
	}

	public Object[] toArray() {
		synchronized (list) {
			return list.toArray();
		}
	}

}
