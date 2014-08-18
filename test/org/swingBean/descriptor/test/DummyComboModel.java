package org.swingBean.descriptor.test;

import javax.swing.DefaultComboBoxModel;

public class DummyComboModel extends DefaultComboBoxModel {

	private static final long serialVersionUID = 3617573795159160889L;

	public DummyComboModel() {
		super(new String[]{"Valor 1", "Valor 2"});
	}

}
