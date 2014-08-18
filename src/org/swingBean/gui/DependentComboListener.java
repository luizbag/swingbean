package org.swingBean.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.swingBean.gui.wrappers.ComboWrapper;
import org.swingBean.gui.wrappers.ComponentWrapper;
import org.swingBean.gui.wrappers.DependentComboWrapper;

public class DependentComboListener implements ActionListener {   
	
	private ComponentWrapper dependable;
	private DependentComboWrapper dependent;
	
	public static void createDependence(ComboWrapper dependable, DependentComboWrapper dependent){
		((JComboBox)dependable.getComponent()).addActionListener(new DependentComboListener(dependable, dependent));
	}

	public DependentComboListener(ComboWrapper dependable, DependentComboWrapper dependent) {
		this.dependable = dependable;
		this.dependent = dependent;
	}

	public void actionPerformed(ActionEvent action) {
		dependent.valueChanged(dependable.getValue());
		dependent.cleanValue();
	}

}
