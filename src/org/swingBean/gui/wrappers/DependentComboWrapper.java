package org.swingBean.gui.wrappers;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.swingBean.descriptor.DependentComboModel;
import org.swingBean.descriptor.look.LookProvider;

public class DependentComboWrapper extends ComboWrapper {
	
	private DependentComboModel dependentModel;
	private Object lastValue; 

	public void valueChanged(Object value){
		if(lastValue != null && lastValue.equals(value))
			return;
		comboModel = dependentModel.getComboModel(value);
		if(comboModel == null)
			combo.setModel(new DefaultComboBoxModel());
		else
			combo.setModel(comboModel);
		lastValue = value;
	}

	public void reload() {
		comboModel = dependentModel.getComboModel(lastValue);
		if(comboModel == null)
			combo.setModel(new DefaultComboBoxModel());
		else
			combo.setModel(comboModel);
	}
	
	public void initComponent() throws Exception {
		combo = new JComboBox(new DefaultComboBoxModel(new String[]{}));
		if(comboModelClass != null){
			modelClass = Class.forName(comboModelClass);
			dependentModel = (DependentComboModel) modelClass.newInstance();
		}
		combo.setFont(LookProvider.getLook().getFieldsFont());
		combo.setPreferredSize(new Dimension(-1,2*LookProvider.getLook().getFieldsFont().getSize()));
	}
	

}
