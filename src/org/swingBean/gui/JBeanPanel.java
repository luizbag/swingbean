package org.swingBean.gui;

import static org.swingBean.util.BeanUtils.getProperty;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import org.swingBean.actions.ApplicationAction;
import org.swingBean.actions.FilterAction;
import org.swingBean.actions.ListenerActionExecuter;
import org.swingBean.binding.BeanPanelBinding;
import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.TypeConstants;
import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.exception.ComponentCreationException;
import org.swingBean.exception.InvalidBeanException;
import org.swingBean.gui.wrappers.ComboWrapper;
import org.swingBean.gui.wrappers.ComponentWrapper;
import org.swingBean.gui.wrappers.DecoratorWrapper;
import org.swingBean.gui.wrappers.DependentComboWrapper;
import org.swingBean.gui.wrappers.ReloadableWrapper;
import org.swingBean.gui.wrappers.WrapperFactory;
import org.swingBean.util.BeanUtils;
import org.swingBean.util.NameUtils;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;

public class JBeanPanel<Bean> extends JPanel{

	private static final long serialVersionUID = 1L;
	private GenericFieldDescriptor descriptor;
	private Map<String,ComponentWrapper> components;
	private Class<Bean> beanClass;
	private String panelTitle;
	private Map<String,String> propertyToDependent;
	//private Map<String,Object> originalValues;
	
	
	public JBeanPanel(Class<Bean> beanClass){
		this(beanClass,NameUtils.toDefaultName(beanClass.getSimpleName()));
	}
	
	public JBeanPanel(Class<Bean> beanClass, GenericFieldDescriptor descriptor){
		this(beanClass,NameUtils.toDefaultName(beanClass.getSimpleName()),descriptor);
	}
	
	public JBeanPanel(Class<Bean> beanClass, String panelTitle){
		this(beanClass, panelTitle, new GenericFieldDescriptor(beanClass));
	}
	
	public JBeanPanel(Class<Bean> beanClass, String panelTitle, GenericFieldDescriptor descriptor){
		this.panelTitle = panelTitle;
		components = new TreeMap<String,ComponentWrapper>();
		propertyToDependent = new TreeMap<String,String>();
		this.descriptor = descriptor;
		this.beanClass = beanClass;
		setName(descriptor.getName());
		createComponents();
		putFieldsInPanel();
		mapDependences();
	}
	
	private void createComponents(){
		for(int i=0;i<descriptor.getLineLenght();i++){
			for(String property : descriptor.getLinePropertys(i)){
					createComponent(property);
			}
		}
	}
	
	private void mapDependences() {
		List<String> dependences = descriptor.getDependentList();
			for(String property:dependences){
				DependentComboListener.createDependence((ComboWrapper)components.get(descriptor.getDependentProperty(property)),(DependentComboWrapper)components.get(property));	
			}
	}

	public void populateBean(Bean bean){
		for(String property:components.keySet())
			BeanUtils.setProperty(bean,property,getComponentValue(property));
	}
	
	public void setBean(Bean bean){
		//originalValues = new HashMap<String,Object>();
		for(int i=0;i<descriptor.getLineLenght();i++){
			for(String property : descriptor.getLinePropertys(i)){
				Object value = getProperty(bean,property);
				//originalValues.put(property,value);
				if(value != null)
					setComponentValue(property, value);
				else
					cleanComponent(property);
			}
		}
	}
	
	private void cleanComponent(String property) {
		ComponentWrapper wrapper = components.get(property);
		if(wrapper != null)
			wrapper.cleanValue();
	}

	private void createComponent(String property) {
		ComponentWrapper componentWrapper = WrapperFactory.createWrapper(property,descriptor);
		components.put(property,componentWrapper);
	}
	
	private void setComponentValue(String property, Object object) {
		ComponentWrapper wrapper = components.get(property);
		if(wrapper != null)
			wrapper.setValue(object);
	}
	
	private Object getComponentValue(String property) {
		ComponentWrapper wrapper = components.get(property);
		if(wrapper != null)
			return wrapper.getValue();
		return null;
	}
	
	public Component getComponent(String property){
		return ((ComponentWrapper)components.get(property)).getComponent();
	}
	
	private void putFieldsInPanel(){
	      if(panelTitle!=null){
			  this.setBorder(LookProvider.getLook().getBorder(panelTitle));
	      }
	      FormLayout formlayout = new FormLayout("FILL:DEFAULT:GROW(1.0)",getRowInformation(descriptor.getLineLenght()));
	      CellConstraints cc = new CellConstraints();
	      this.setLayout(formlayout);
		  
		  for(int i=1;i<=descriptor.getLineLenght();i++){
			  this.add(getRowPanel(i-1),cc.xy(1,i));
		  }
	}
	
	private JPanel getRowPanel(int row){
		JPanel rowPanel = new JPanel();
		rowPanel.setBorder(new EmptyBorder(1,1,1,1));
		FormLayout formlayout = new FormLayout(getColumnsInformation(row),"CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE");
	    CellConstraints cc = new CellConstraints();
		rowPanel.setLayout(formlayout);
		int counter = 0;
		for(String property: descriptor.getLinePropertys(row)){
			String type = WrapperFactory.getComponentType(property, descriptor);
			if(type.equals(TypeConstants.MULTIPLE_LIST) || type.equals(TypeConstants.BOOLEAN)){
				if(descriptor.getLinePropertys(row).length == 1)
					rowPanel.add(getComponent(property),cc.xywh(2*counter+1,1,descriptor.getColSpan(property)*2-1,1));
				else{
					rowPanel.add(LookProvider.getLook().createFormLabel(" "),cc.xy(2*counter+1,1));
					rowPanel.add(getComponent(property),cc.xywh(2*counter+1,1,descriptor.getColSpan(property)*2-1,2));
				}
			}else {
				if(descriptor.getParameter(property,"mandatory") != null && descriptor.getParameter(property,"mandatory").equals("true"))
					rowPanel.add(LookProvider.getLook().createFormMandatoryLabel(descriptor.getParameter(property,"label")),cc.xy(2*counter+1,1));
				else
					rowPanel.add(LookProvider.getLook().createFormLabel(descriptor.getParameter(property,"label")),cc.xy(2*counter+1,1));
				if(type.equals(TypeConstants.CHECKBOX_LIST))
					rowPanel.add(getComponent(property),new CellConstraints(2*counter+1,2,descriptor.getColSpan(property)*2-1,1,CellConstraints.DEFAULT,CellConstraints.FILL));
				else
					rowPanel.add(getComponent(property),cc.xywh(2*counter+1,2,descriptor.getColSpan(property)*2-1,1));
			}
			
			counter += descriptor.getColSpan(property);
		}
		return rowPanel;
	}
	
	private String getRowInformation(int lineLenght){
		StringBuilder builder = new StringBuilder();
		builder.append("CENTER:DEFAULT:NONE");
		for(int i=1;i<lineLenght;i++)
			builder.append(",CENTER:DEFAULT:NONE");
		return builder.toString();
	}
	
	private String getColumnsInformation(int row) {
		StringBuilder builder = new StringBuilder();
		String[] properties = descriptor.getLinePropertys(row);
		for (int i = 0; i < properties.length; i++) {
			if (descriptor.getParameter(properties[i],"dlu") != null) {
				builder.append("FILL:");
				builder.append(descriptor.getParameter(properties[i],"dlu"));
				builder.append("DLU:NONE");
			} else if(descriptor.getParameter(properties[i],"colspan")!= null){
				int numCols = descriptor.getColSpan(properties[i]);
				for(int j = 0; j < numCols; j++){
					builder.append("FILL:DEFAULT:GROW");
					if (j != (numCols - 1)) {
						builder.append(",FILL:4DLU:NONE,");
					}
				}
		    }else {
		    	builder.append("FILL:DEFAULT:GROW");
			}
		
			if (i != (properties.length - 1)) {
				builder.append(",FILL:4DLU:NONE,");
			}
		}
		return builder.toString();
	}
	
	public void cleanForm(){
		for(String property : components.keySet())
			 cleanComponent(property);
	}
	
	public ValidationResult getValidationResult(){
		Bean bean = null;
		try {
			bean = beanClass.newInstance();
		} catch (Exception e) {
			throw new InvalidBeanException("Can't create bean for validation",e);
		}
		populateBean(bean);
		return descriptor.validate(bean);
	}
	
	public void decorateField(String property, JComponent component){
		DecoratorWrapper decorator = new DecoratorWrapper(components.get(property),component,descriptor.getParameter(property,"dlu"));
		components.put(property,decorator);
		this.removeAll();
		putFieldsInPanel();
	}
	
	public void associateAction(String property, ApplicationAction action){
		Class componentClass = components.get(property).getComponent().getClass();
		ListenerActionExecuter listener = new ListenerActionExecuter(action);
		Component component = components.get(property).getComponent();
		if(component instanceof JTextComponent){
			((JTextComponent)component).getDocument().addDocumentListener(listener);
		} else {
			try {
				Method metodo = componentClass.getMethod("addActionListener",new Class[]{ActionListener.class});
				metodo.invoke(components.get(property).getComponent(),new Object[]{listener});
			} catch (Exception e) {
				throw new ComponentCreationException("The component of property "+property + "does not support actions",e);
			}
		}
	}
	
	public void reloadList(String property){
		if(components.get(property) instanceof ReloadableWrapper)
			((ReloadableWrapper)components.get(property)).reload();
	}
	
	public void setEnable(String property, boolean enable){
		if(components.get(property) != null)
			components.get(property).setEnable(enable);
	}
	
	public Object getPropertyValue(String property){
		if(components.get(property) != null)
			return components.get(property).getValue();
		return null;
	}
	
	public void setPropertyValue(String property, Object value){
		if(components.get(property) != null)
			components.get(property).setValue(value);
	}
	
	public void filterModel(BeanTableModel model){
		model.cleanFilter();
		for(String property : components.keySet()){
			String filterProp = descriptor.getParameter(property,"filterProperty");
			if(filterProp != null){
				String filterType = descriptor.getParameter(property,"filterType");
				Object value = getPropertyValue(filterProp);
				if(value == null || (value instanceof String && "".equals(value)) )
					continue;
				if(filterType == null || filterType.equals("equals")){
					model.filterEquals(filterProp,getPropertyValue(filterProp));
				} else if(filterType.equals("contains")){
					model.filterContains(filterProp,getPropertyValue(filterProp).toString());
				} else if(filterType.equals("started")){
					model.filterStartedBy(filterProp,getPropertyValue(filterProp).toString());
				} else if(filterType.equals("greaterThan")){
					model.filterBetween(filterProp,(Comparable)getPropertyValue(filterProp),null);
				} else if(filterType.equals("lesserThan")){
					model.filterBetween(filterProp,null,(Comparable)getPropertyValue(filterProp));
				}
			}
		}
	}
	
	public void installAutomaticFiltering(BeanTableModel model){
		FilterAction action = new FilterAction(this,model);
		for(String property : components.keySet()){
			String filterProp = descriptor.getParameter(property,"filterProperty");
			if(filterProp != null){
				associateAction(property,action);
			}
		}
	}
	
	public void enabledAllComponents(boolean enabled) {
		for (String key : components.keySet()) {
			ComponentWrapper wrapper = components.get(key);
			wrapper.setEnable(enabled);
		}
	}
	
	public boolean existsProperty(String property){
		return components.containsKey(property);
	}
	
	public Bean getSynchoronizedObject(Bean base){
		setBean(base);
		return (Bean)BeanPanelBinding.createSynchonizedBean(base, this);
	}

	public Class<Bean> getBeanClass() {
		return beanClass;
	}

}
