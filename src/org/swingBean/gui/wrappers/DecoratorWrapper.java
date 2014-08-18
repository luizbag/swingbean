package org.swingBean.gui.wrappers;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class DecoratorWrapper implements ComponentWrapper,ReloadableWrapper {
	
	private ComponentWrapper wrapper;
	private JComponent component;
	private JPanel panel;
	private String dlu;
	
	public DecoratorWrapper(ComponentWrapper wrapper, JComponent component) {
		this.wrapper = wrapper;
		this.component = component;
	}
	
	public DecoratorWrapper(ComponentWrapper wrapper, JComponent component, String dlu) {
		this.wrapper = wrapper;
		this.component = component;
		this.dlu = dlu;
	}


	public Object getValue() {
		return wrapper.getValue();
	}

	public void setValue(Object value) {
		wrapper.setValue(value);
	}

	public void cleanValue() {
		wrapper.cleanValue();
	}

	public Component getComponent() {
		if(panel == null)
			createPanel();
	    return panel;
	}
	
	private void createPanel() {
		panel = new JPanel();
		int height = Math.max(component.getPreferredSize().height,wrapper.getComponent().getPreferredSize().height);
		int componentWidth = component.getPreferredSize().width;
		FormLayout formlayout = null;
		if (dlu != null) {
			int dluInteger = Integer.parseInt(dlu);
		    int wrapperWidth = (dluInteger - (int)(componentWidth / 1.9) - 2); 
		    formlayout = new FormLayout("FILL:" + wrapperWidth + "DLU:NONE,FILL:2DLU:NONE,FILL:" + componentWidth + "PX:NONE",
			"CENTER:"+height+"PX:NONE");
		} else {
			formlayout = new FormLayout("FILL:DEFAULT:GROW(1.0),FILL:2DLU:NONE,FILL:" + componentWidth + "PX:NONE","CENTER:"+height+"PX:NONE");
		}
		CellConstraints cc = new CellConstraints();
		panel.setLayout(formlayout);
		panel.add(wrapper.getComponent(),cc.xy(1,1));
		panel.add(component,cc.xy(3,1));
	}


	public void reload(){
		if(wrapper instanceof ReloadableWrapper){
			((ReloadableWrapper)wrapper).reload();
		}
	}

	public void setEnable(boolean enable) {
		wrapper.setEnable(enable);
		component.setEnabled(enable);
	}

	public void initComponent() throws Exception {
		createPanel();
	}

}
