package org.swingBean.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import org.swingBean.actions.ApplicationAction;

public class JActMenuItem extends JMenuItem implements ActionListener {

	private ApplicationAction action;
	
	public JActMenuItem(Icon icon, ApplicationAction action) {
		super(icon);
		this.action = action;
		addActionListener(this);
	}

	public JActMenuItem(String text, Icon icon, ApplicationAction action) {
		super(text, icon);
		this.action = action;
		addActionListener(this);
	}

	public JActMenuItem(String text, ApplicationAction action) {
		super(text);
		this.action = action;
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		action.executeActionChain();
	}	

	public ApplicationAction getAplicationAction() {
		return action;
	}

}
