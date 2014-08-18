package org.swingBean.gui.custom.checkboxlist;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class CheckListener implements MouseListener, KeyListener {
	
	protected CheckBoxList m_list;

	public CheckListener(CheckBoxList list) {
		m_list = list;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getX() < 20)
			doCheck();
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ')
			doCheck();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	protected void doCheck() {
		int index = m_list.getSelectedIndex();
		if (index < 0)
			return;
		RowData data = (RowData) m_list.getModel().getElementAt(index);
		data.invertSelected();
		m_list.repaint();
	}
}