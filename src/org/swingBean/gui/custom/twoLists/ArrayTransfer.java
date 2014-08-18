package org.swingBean.gui.custom.twoLists;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;

public class ArrayTransfer implements Transferable {
	public static DataFlavor FLAVOUR;
	static {
		try {
			FLAVOUR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected JComponent m_source;

	protected Object[] m_arr;

	public ArrayTransfer(JComponent source, Object[] arr) {
		m_source = source;
		m_arr = arr;
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (!isDataFlavorSupported(flavor))
			throw new UnsupportedFlavorException(flavor);
		return this;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return FLAVOUR.equals(flavor);
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { FLAVOUR };
	}

	public JComponent getSource() {
		return m_source;
	}

	public Object[] getData() {
		return m_arr;
	}
}