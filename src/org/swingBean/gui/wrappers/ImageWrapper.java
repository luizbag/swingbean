package org.swingBean.gui.wrappers;

import java.awt.Component;

import org.swingBean.gui.custom.imagefield.ImageField;

public class ImageWrapper implements ComponentWrapper {
	
	private ImageField field;
	private int showHeight = 160;
	private int showWidth = 120;
	private int saveHeight = 640;
	private int saveWidth = 480;

	public Object getValue() {
		return field.getValue();
	}

	public void setValue(Object value) {
		if(value != null && ((byte[])value).length != 0)
			field.setValue((byte[])value);
	}

	public void cleanValue() {
		field.reset();
	}

	public Component getComponent() {
		return field;
	}

	public void setEnable(boolean enable) {
		field.setEnabled(enable);
	}
	
	public void setSaveResolution(String saveResolution){
		String[] resolution = saveResolution.split("x");
		saveHeight = Integer.parseInt(resolution[0]);
		saveWidth = Integer.parseInt(resolution[1]);
	}
	
	public void setShowResolution(String showResolution){
		String[] resolution = showResolution.split("x");
		showHeight = Integer.parseInt(resolution[0]);
		showWidth = Integer.parseInt(resolution[1]);
	}

	public void initComponent() throws Exception {
		field = new ImageField(showHeight, showWidth, saveHeight, saveWidth);
	}

}
