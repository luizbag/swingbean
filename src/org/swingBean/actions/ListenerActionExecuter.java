package org.swingBean.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ListenerActionExecuter implements ActionListener, DocumentListener {
	
	private ApplicationAction action;

	public ListenerActionExecuter(ApplicationAction action) {
		this.action = action;
	}

	public void actionPerformed(ActionEvent arg0) {
		action.executeActionChain();
	}

	public void insertUpdate(DocumentEvent e) {
		action.executeActionChain();		
	}
	public void removeUpdate(DocumentEvent e) {
		action.executeActionChain();		
	}
	public void changedUpdate(DocumentEvent e) {
		action.executeActionChain();		
	}

}
