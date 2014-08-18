package org.swingBean.gui.custom.twoLists;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.TransferHandler;
import javax.swing.event.MouseInputAdapter;

import org.swingBean.descriptor.look.LookProvider;


public class MutableList extends JList {
	private SimpleListModel m_model;
	private DnDStarter starter;

	public MutableList() {
		m_model = new SimpleListModel();
		setModel(m_model);
		installDnD();
		setFont(LookProvider.getLook().getFieldsFont());
	}

	public MutableList(Object[] arr) {
		m_model = new SimpleListModel();
		setObjectArray(arr);
		setModel(m_model);
		installDnD();
		setFont(LookProvider.getLook().getFieldsFont());
	}
	
	public void setObjectArray(Object[] arr){
		synchronized (getTreeLock()) {
			m_model.clear();
			if(arr != null)
				for (int k = 0; k < arr.length; k++)
					m_model.addElement(arr[k]);
		}
	}

	public void addElement(Object obj) {
		synchronized (getTreeLock()) {
			m_model.addElement(obj);
		}
	}

	public void removeElement(Object obj) {
		synchronized (getTreeLock()) {
			m_model.removeElement(obj);
		}
	}

	public Object[] getData() {
		return m_model.toArray();
	}

	protected void installDnD() {
		setDragEnabled(true);
		setTransferHandler(new ListTransferHandler());
		starter = new DnDStarter();
		addMouseListener(starter);
		addMouseMotionListener(starter);
	}
	
	protected void uninstallDnD() {
		setDragEnabled(false);
		setTransferHandler(null);
		removeMouseListener(starter);
		removeMouseMotionListener(starter);
	}
	
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if(enabled){
			installDnD();
		}else{
			uninstallDnD();
		}
	}

	class DnDStarter extends MouseInputAdapter {
		public void mousePressed(MouseEvent e) {
			TransferHandler th = MutableList.this.getTransferHandler();
			th.exportAsDrag(MutableList.this, e, TransferHandler.MOVE);
		}
	}
	
}
