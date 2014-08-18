package org.swingBean.example.comboloading;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import org.swingBean.descriptor.DependentComboModel;

public class CorCachorroProvider implements DependentComboModel {

	public ComboBoxModel getComboModel(Object value) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		if("Yorkshire".equals(value)){
			model.addElement("Azul Aço e Caramelo");
			model.addElement("Acinzentado e Caramelo");
		}else if("Beagle".equals(value)){
			model.addElement("Marrom");
		}else if("Basset".equals(value)){
			model.addElement("Cinza");
			model.addElement("Preto");
			model.addElement("Marron");
		}else if("Poodle".equals(value)){
			model.addElement("Cinza");
			model.addElement("Branco");
			model.addElement("Preto");
			model.addElement("Rosa");
		}
		return model;
	}

}
