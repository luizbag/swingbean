package org.swingBean.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CompressedList implements List,Serializable {
	
	private List list;
	
	private CompressedList(List list){
		this.list = list;
	}
	
	public static List compressList(List list){
		return new CompressedList(list);
	}

	public void add(int arg0, Object arg1) {
		list.add(arg0, arg1);
	}

	public boolean add(Object arg0) {
		return list.add(arg0);
	}

	public boolean addAll(Collection arg0) {
		return list.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection arg1) {
		return list.addAll(arg0, arg1);
	}

	public void clear() {
		list.clear();
	}

	public boolean contains(Object arg0) {
		return list.contains(arg0);
	}

	public boolean containsAll(Collection arg0) {
		return list.containsAll(arg0);
	}

	public boolean equals(Object arg0) {
		return list.equals(arg0);
	}

	public Object get(int arg0) {
		return list.get(arg0);
	}

	public int hashCode() {
		return list.hashCode();
	}

	public int indexOf(Object arg0) {
		return list.indexOf(arg0);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Iterator iterator() {
		return list.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return list.lastIndexOf(arg0);
	}

	public ListIterator listIterator() {
		return list.listIterator();
	}

	public ListIterator listIterator(int arg0) {
		return list.listIterator(arg0);
	}

	public Object remove(int arg0) {
		return list.remove(arg0);
	}

	public boolean remove(Object arg0) {
		return list.remove(arg0);
	}

	public boolean removeAll(Collection arg0) {
		return list.removeAll(arg0);
	}

	public boolean retainAll(Collection arg0) {
		return list.retainAll(arg0);
	}

	public Object set(int arg0, Object arg1) {
		return list.set(arg0, arg1);
	}

	public int size() {
		return list.size();
	}

	public List subList(int arg0, int arg1) {
		return list.subList(arg0, arg1);
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public Object[] toArray(Object[] arg0) {
		return list.toArray(arg0);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		ZipOutputStream zipOut = new ZipOutputStream(out);
		ZipEntry zipEntry = new ZipEntry("obj");
		zipEntry.setMethod(ZipOutputStream.DEFLATED);
		zipOut.setLevel(9);
		zipOut.putNextEntry(zipEntry);
		ObjectOutputStream dataOut = new ObjectOutputStream(zipOut);
		int size = list.size();
		dataOut.writeInt(size);
		for(int i=0;i<size;i++){
			dataOut.writeObject(list.get(i));
		}
		zipOut.closeEntry();
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		ZipInputStream zipIn = new ZipInputStream(in);
		zipIn.getNextEntry();
		ObjectInputStream dataIn = new ObjectInputStream(zipIn);
		int size = dataIn.readInt();
		list = new ArrayList(size);
		for(int i=0;i<size;i++){
			list.add(dataIn.readObject());
		}
	}
}
