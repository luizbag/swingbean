package org.swingBean.gui.wrappers;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JLabel;

public class ColorWrapper implements ComponentWrapper {
	
	private JLabel label;
	private MouseListener listener;
	
	public Object getValue() {
		return label.getBackground();
	}

	public void setValue(Object value) {
		label.setBackground((Color)value);
	}

	public void cleanValue() {
		label.setBackground(Color.WHITE);
	}

	public Component getComponent() {
		return label;
	}

	public void setEnable(boolean enable) {
		label.setEnabled(enable);
		if(enable){
			label.addMouseListener(listener);
		} else{
			label.removeMouseListener(listener);
		}
	}

	public void initComponent() throws Exception {
		label = new JLabel(" ");
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		listener = new MouseListener(){

			public void mouseClicked(MouseEvent event) {
				if(event.getClickCount() > 1){
					Color color = JColorChooser.showDialog(label,"Escolha a cor",label.getBackground());
				    if (color != null)
						label.setBackground(color);
				}
			}

			public void mousePressed(MouseEvent arg0) {}

			public void mouseReleased(MouseEvent arg0) {}

			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}
			
		};
		label.addMouseListener(listener);		
	}

}
