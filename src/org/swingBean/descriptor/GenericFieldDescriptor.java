package org.swingBean.descriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;  
import javax.swing.ListModel;

import org.swingBean.descriptor.validator.Validator;
import org.swingBean.gui.wrappers.WrapperFactory;
import org.swingBean.util.BeanUtils;
import org.swingBean.util.NameUtils;

import com.jgoodies.validation.ValidationResult;

public class GenericFieldDescriptor implements FieldDescriptor{
	
	protected List<List<String>> propertysPosition;
	protected Map<String,String> dependences;
	protected Map<String,Map<String,String>> parameters;
	protected List<Validator> validators;
	protected Class beanClass;
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GenericFieldDescriptor(Class beanClass){
		propertysPosition = new ArrayList<List<String>>();
		
		if(beanClass != null){
			List<String> propertys = BeanUtils.getPropertyList(beanClass);
			for(String property: propertys){
				ArrayList<String> line = new ArrayList<String>();
				line.add(property);
				propertysPosition.add(line);
			}
		}
		this.beanClass = beanClass;
		mapInicialization();
	}
	
	public GenericFieldDescriptor(Class beanClass, String[][] propertyOrder){
		setPropertyOrder(propertyOrder);
		this.beanClass = beanClass;
		mapInicialization();
	}
	
	private void mapInicialization() {
		dependences = new HashMap<String,String>();
		parameters = new HashMap<String,Map<String,String>>();
		validators = new ArrayList<Validator>();
	}

	public void setPropertyOrder(String[][] propertyOrder) {
		propertysPosition = new ArrayList<List<String>>();
		for(String[] propertyLine: propertyOrder){
			ArrayList<String> line = new ArrayList<String>();
			for(String property : propertyLine){
				line.add(property);
			}
			propertysPosition.add(line);
		}
	}

	public int getLineLenght() {
		return propertysPosition.size();
	}

	public int getNumberColumns(int row){
		int numberCol = 0;
		for(String property : propertysPosition.get(row))
			numberCol += getColSpan(property);
		return numberCol;
	}
	

	public String[] getLinePropertys(int lineNumber) {
		List<String> lineList = propertysPosition.get(lineNumber);
		String[] propertyLine = new String[lineList.size()];
		int count = 0;
		for(String property: lineList){
			propertyLine[count] = property;
			count++;
		}	
		return propertyLine;
	}
	
	public List<String> getDependentList(){
		return new ArrayList<String>(dependences.keySet());
	}
	
	public String getDependentProperty(String property){
		return dependences.get(property);
	}

	public Class getBeanClass() {
		return beanClass;
	}

	public int getColSpan(String property) {
		if(getParameter(property,"dlu") != null)
			return 1;
		if(getParameter(property,"colspan") != null)
			return Integer.parseInt(getParameter(property,"colspan"));
		return 1;
	}
	
	public void setParameter(String property, String parameter, String value){
		if(!parameters.containsKey(property))
			parameters.put(property, new HashMap<String,String>());
		parameters.get(property).put(parameter,value);
		if(parameter.equals("dependentProperty")){
			dependences.put(property,value);
		}
	}
	
	public String getParameter(String property, String parameter){
		if(!parameters.containsKey(property))
			return null;
		if(!parameters.get(property).containsKey(parameter))
			return null;
		return parameters.get(property).get(parameter);
	}
	
	public void addValidator(Validator validator){
		validators.add(validator);
	}
	
	public ValidationResult validate(Object bean){
		ValidationResult result = new ValidationResult();
		for(Validator validator : validators)
			if(!validator.isValid(bean))
				result.add(validator.getValidationMessage(getParameter(validator.getProperty(),"label")));
		return result;
	}

	public Map<String, String> getParameters(String property) {
		return parameters.get(property);
	}

}
