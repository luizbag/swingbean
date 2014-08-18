package org.swingBean.gui.wrappers;

import java.awt.Component;
import java.lang.reflect.Method;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import org.swingBean.descriptor.TypeConstants;
import org.swingBean.exception.ComponentExecutionException;
import org.swingBean.gui.custom.checkboxlist.CheckBoxList;
import org.swingBean.gui.custom.twoLists.TwoListsPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ArrayWrapper implements ComponentWrapper,ReloadableWrapper {
	
	private ArrayHandler arrayHandler;
	private Class modelClass;
	private Boolean loading = false;
	private Object[] tempValue = null;
	protected Method modelMethod;
	protected Object instance;
	
	protected String type = TypeConstants.MULTIPLE_LIST;
	protected boolean threadLoading = true;
	protected String parameter;
	protected String listModelClass;
	protected String listModelMethod;
	protected String list;
	protected String label;

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
		ListModel listModel = null;
		synchronized(loading){
			loading = true;
		}
		try {
			if(modelClass != null && modelMethod == null)
				listModel = (ListModel)modelClass.newInstance();
			else if(modelMethod != null)
				if(parameter == null)
					listModel = (ListModel)modelMethod.invoke(instance);
				else
					listModel = (ListModel)modelMethod.invoke(instance,parameter);
		} catch (Exception e) {
			throw new ComponentExecutionException("Can't set list model",e);
		}
		if(listModel != null)
			arrayHandler.setCompleteData(listModel);
		synchronized(loading){
			loading = false;
			if(tempValue != null){
				arrayHandler.setValue(tempValue);
				tempValue = null;
			}else{
				cleanValue();
			}
		}
	}
	
	public void createArrayHandler(String name, String type){
		if(type.equals(TypeConstants.CHECKBOX_LIST))
			arrayHandler = new CheckBoxList();
		else
			arrayHandler = new TwoListsPanel(name,"Selected");
	}

	public Object getValue() {
		Object[] value = arrayHandler.getValue();
		return value;
	}

	public void setValue(Object value) {
		synchronized (loading) {
			if(!loading){
				arrayHandler.setValue((Object[])value);
			}else{
				tempValue = (Object[])value;
			}
		}
	}

	public void cleanValue() {
		arrayHandler.resetComponent();		
	}

	public Component getComponent() {
		if(arrayHandler instanceof TwoListsPanel)
			return (TwoListsPanel)arrayHandler;
		else{
			JScrollPane sp = new JScrollPane((JList)arrayHandler);
			CellConstraints cc = new CellConstraints();
			JPanel panel = new JPanel(new FormLayout("FILL:DEFAULT:GROW(1.0)","FILL:DEFAULT:GROW(1.0)"));
			panel.setBorder(new EmptyBorder(1,1,10,1));
			panel.add(sp,cc.xy(1,1));
			return panel;
		}
	}

	public void reload() {
		if(modelClass != null || instance != null){
			tempValue = (Object[])getValue();
			arrayHandler.setCompleteData(new Object[]{});
			loadModel();
		}
	}

	public void setEnable(boolean enable) {
		arrayHandler.setEnabled(enable);
	}

	public void initComponent() throws Exception {
		createArrayHandler(label,type);
		if(list != null){
			String[] valueList = list.split(";");
			DefaultListModel model = new DefaultListModel();
			for(String item : valueList)
				model.addElement(item);
			arrayHandler.setCompleteData(model);
		}
		if(listModelClass != null){
			modelClass = Class.forName(listModelClass);
		}
		if(listModelMethod != null){
			if(parameter == null)
				modelMethod = modelClass.getMethod(listModelMethod);
			else
				modelMethod = modelClass.getMethod(listModelMethod,String.class);
			instance = modelClass.getMethod("getInstance").invoke(null);
		}
		loadModel();
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setListModelClass(String listModelClass) {
		this.listModelClass = listModelClass;
	}

	public void setListModelMethod(String listModelMethod) {
		this.listModelMethod = listModelMethod;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public void setThreadLoading(boolean threadLoading) {
		this.threadLoading = threadLoading;
	}

	public void setType(String type) {
		this.type = type;
	}

}
