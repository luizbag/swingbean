package org.swingBean.example.comboloading;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class CidadesProvider {
	
	public static CidadesProvider getInstance(){
		return new CidadesProvider();
	}
	
	public ComboBoxModel getCidades(){
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement("São Paulo");
		model.addElement("Rio de Janeiro");
		model.addElement("Belo Horizonte");
		model.addElement("Outros");
		return model;
	}

}
