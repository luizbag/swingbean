package org.swingBean.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TableKeyListener implements KeyListener {
	
	private JBeanTable table;
	
	public TableKeyListener(JBeanTable table) {
		this.table = table;
	}

	public void keyTyped(KeyEvent arg0) {}

	public void keyPressed(KeyEvent arg0) {}

	public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() == 10){
			table.editNextField();
		}

	}

}
