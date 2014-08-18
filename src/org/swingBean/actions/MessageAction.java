package org.swingBean.actions;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageAction extends ApplicationAction {
	
	private String message;
	private Frame frame;

	public MessageAction(String message) {
		this.message = message;
		this.frame = JOptionPane.getRootFrame();
	}
	
	public MessageAction(String message, JFrame frame) {
		this.message = message;
		this.frame = frame;
	}

	public void execute() {
		JOptionPane.showMessageDialog(frame,message);
	}

}
