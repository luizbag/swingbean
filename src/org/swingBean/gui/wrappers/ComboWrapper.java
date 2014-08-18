  package org.swingBean.gui.wrappers;

import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.Method;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.exception.ComponentCreationException;

public class ComboWrapper implements ComponentWrapper,ReloadableWrapper {
	
	protected ComboBoxModel comboModel;
	protected JComboBox combo;
	protected Boolean loading = false;
	protected Object tempValue = null;
	protected Class modelClass;
	protected Method modelMethod;
	protected Object modelProvider;
	
	protected boolean threadLoading = true;
	protected String parameter;
	protected String comboModelClass;
	protected String comboModelMethod;
	protected String comboList;

	private void loadModel() {
		if(threadLoading){
			Thread loadThread = new Thread(){
				public void run(){
					setModel();
				}
			};
			loadThread.start();
		} else {
			setModel();
		}
	}
	
	private void setModel(){
		synchronized(loading){
			loading = true;
		}
		
		if (modelClass != null || modelMethod != null) {
			try {
				if (modelMethod == null)
					comboModel = (ComboBoxModel) modelClass.newInstance();
				else if (parameter == null)
					comboModel = (ComboBoxModel) modelMethod
							.invoke(modelProvider);
				else
					comboModel = (ComboBoxModel) modelMethod.invoke(
							modelProvider, parameter);
			} catch (Exception e) {
				throw new ComponentCreationException("Can't set the model in the combo");
			}
			combo.setModel(comboModel);
		}
		
		synchronized(loading){
			loading = false;
			if(tempValue != null){
				comboModel.setSelectedItem(tempValue);
				tempValue = null;
			}else{
				cleanValue();
			}
		}
	}
	

	public Object getValue() {
		if(comboModel == null)
			return combo.getSelectedItem();
		return comboModel.getSelectedItem();
	}

	public void setValue(Object value) {
		synchronized (loading) {
			if(!loading && comboModel != null){
					comboModel.setSelectedItem(value);
					combo.repaint();
			}else if (comboModel == null && combo != null) {
				DefaultComboBoxModel model = new DefaultComboBoxModel();
				model.addElement(value);
				combo.setModel(model);
				combo.setSelectedItem(value);
			} else{
				tempValue = value;
			}
		}
	}

	public void cleanValue() {
		if(comboModel != null)
			comboModel.setSelectedItem(null);
	}

	public Component getComponent() {
		return combo;
	}

	public void reload() {
		if(modelClass != null || modelProvider != null){
			tempValue = getValue();
			combo.setModel(new DefaultComboBoxModel(new String[]{}));
			loadModel();
		}
	}

	public void setEnable(boolean enable) {
		combo.setEnabled(enable);		
	}

	public void initComponent() throws Exception {
		combo = new JComboBox(new DefaultComboBoxModel(new String[]{}));
		if(comboList != null){
			comboModel = new DefaultComboBoxModel(comboList.split(";"));
			combo.setModel(comboModel);
			cleanValue();
		}
		if(comboModelClass != null){
			modelClass = Class.forName(comboModelClass);
		}
		if(comboModelMethod != null){
			if(parameter == null)
				modelMethod = modelClass.getMethod(comboModelMethod);
			else
				modelMethod = modelClass.getMethod(comboModelMethod,String.class);
			modelProvider = modelClass.getMethod("getInstance").invoke(null);
		}
		combo.setFont(LookProvider.getLook().getFieldsFont());
		combo.setPreferredSize(new Dimension(-1,2*LookProvider.getLook().getFieldsFont().getSize()));
		loadModel();
	}

	public void setThreadLoading(boolean threadLoading){
		this.threadLoading = threadLoading;
	}

	public void setComboList(String comboList) {
		this.comboList = comboList;
	}

	public void setComboModelClass(String comboModelClass) {
		this.comboModelClass = comboModelClass;
	}

	public void setComboModelMethod(String comboModelMethod) {
		this.comboModelMethod = comboModelMethod;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
}
