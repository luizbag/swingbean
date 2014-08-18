package org.swingBean.descriptor.test;

import javax.swing.ComboBoxModel;

import org.swingBean.descriptor.DependentComboModel;

public class DummyDependentModel implements DependentComboModel {

	public ComboBoxModel getComboModel(Object value) {
		return new DummyComboModel();
	}

}
