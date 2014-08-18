package org.swingBean.gui.custom.twoLists;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;  

import org.swingBean.exception.ComponentExecutionException;


public class ListTransferHandler extends TransferHandler {
	public boolean importData(JComponent c, Transferable t) {
		if (!(c instanceof MutableList))
			return false;
		MutableList list = (MutableList) c;
		try {
			Object obj = t.getTransferData(ArrayTransfer.FLAVOUR);
			if (!(obj instanceof ArrayTransfer))
				return false;
			ArrayTransfer at = (ArrayTransfer) obj;
			// block transfer to self!
			if (c.equals(at.getSource()))
				return false;
			Object[] arr = at.getData();
			for (int k = 0; k < arr.length; k++)
				list.addElement(arr[k]);
		} catch (Exception ex) {
			throw new ComponentExecutionException("Error importing data",ex);
		}
		return true;
	}

	public boolean canImport(JComponent c, DataFlavor[] transferFlavors) {
		if (!(c instanceof MutableList))
			return false;
		for (int k = 0; k < transferFlavors.length; k++)
			if (transferFlavors[k].equals(ArrayTransfer.FLAVOUR))
				return true;
		return false;
	}

	public int getSourceActions(JComponent c) {
		if (!(c instanceof MutableList))
			return NONE;
		return COPY_OR_MOVE;
	}

	protected Transferable createTransferable(JComponent c) {
		if (!(c instanceof MutableList))
			return null;
		Object[] arr = ((JList) c).getSelectedValues();
		return new ArrayTransfer(c, arr);
	}

	protected void exportDone(JComponent source, Transferable t, int action) {
		if (!(source instanceof MutableList))
			return;
		MutableList list = (MutableList) source;
		if (!(action == COPY_OR_MOVE || action == MOVE))
			return;
		try {
			Object obj = t.getTransferData(ArrayTransfer.FLAVOUR);
			if (!(obj instanceof ArrayTransfer))
				return;
			ArrayTransfer at = (ArrayTransfer) obj;
			if (!source.equals(at.getSource()))
				return;
			Object[] arr = at.getData();
			for (int k = 0; k < arr.length; k++)
				list.removeElement(arr[k]);
		} catch (Exception ex) {
			throw new ComponentExecutionException("Error exporting data",ex);
		}
	}
}
