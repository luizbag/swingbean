package org.swingBean.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;  

import org.swingBean.actions.ApplicationAction;

public class JActButton extends JButton implements  ActionListener{
	
	private ApplicationAction action;

	public JActButton(String name, ApplicationAction action) {
		super(name);
		this.action = action;
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		action.executeActionChain();
	}	

	public ApplicationAction getApplicationAction() {
		return action;
	}

	public void setApplicationAction(ApplicationAction action) {
		this.action = action;
	}
	
	
	
	

}
