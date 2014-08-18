package org.swingBean.visualTest;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import org.swingBean.descriptor.DependentComboModel;

public class SexoToEstadoCivil implements DependentComboModel {

	public ComboBoxModel getComboModel(Object value) {
		if(value == null)
			return null;
		if(((String)value).equals("Masculino"))
			return new DefaultComboBoxModel(new String[]{"Casado","Solteiro","Divorciado","Viuvo"});
		else
			return new DefaultComboBoxModel(new String[]{"Casada","Solteira","Divorciada","Viuva"});
	}

}
