package org.swingBean.gui.wrappers;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.swingBean.descriptor.FieldDescriptor;
import org.swingBean.descriptor.TypeConstants;
import org.swingBean.exception.ComponentCreationException;
import org.swingBean.gui.RegexInputVerifier;
import org.swingBean.util.BeanUtils;

public class WrapperFactory { 
	
	private static Map<String,Class<? extends ComponentWrapper>> typeMapping = new HashMap<String,Class<? extends ComponentWrapper>>();
	private static Map<Class, String> defaultTypes = new HashMap<Class, String>();
	private static Map<String, String> defaultParameters =  new HashMap<String, String>();
	
	static {
		typeMapping.put(TypeConstants.TEXT, TextWrapper.class);
		typeMapping.put(TypeConstants.LARGE_TEXT, TextWrapper.class);
		typeMapping.put(TypeConstants.PASSWORD, TextWrapper.class);
		typeMapping.put(TypeConstants.DATE, DateWrapper.class);
		typeMapping.put(TypeConstants.LABEL, LabelWrapper.class);
		typeMapping.put(TypeConstants.BOOLEAN, BooleanWrapper.class);
		typeMapping.put(TypeConstants.INTEGER, NumericWrapper.class);
		typeMapping.put(TypeConstants.LONG, NumericWrapper.class);
		typeMapping.put(TypeConstants.DOUBLE, NumericWrapper.class);
		typeMapping.put(TypeConstants.FLOAT, NumericWrapper.class);
		typeMapping.put(TypeConstants.COMBO, ComboWrapper.class);
		typeMapping.put(TypeConstants.DEPENDENT_COMBO, DependentComboWrapper.class);
		typeMapping.put(TypeConstants.MULTIPLE_LIST, ArrayWrapper.class);
		typeMapping.put(TypeConstants.CHECKBOX_LIST, ArrayWrapper.class);
		typeMapping.put(TypeConstants.COLOR, ColorWrapper.class);
		typeMapping.put(TypeConstants.IMAGE, ImageWrapper.class);
		
		defaultTypes.put(Object.class, TypeConstants.TEXT);
		defaultTypes.put(String.class, TypeConstants.TEXT);
		defaultTypes.put(Date.class, TypeConstants.DATE);
		defaultTypes.put(Color.class, TypeConstants.COLOR);
		defaultTypes.put(Integer.class, TypeConstants.INTEGER);
		defaultTypes.put(int.class, TypeConstants.INTEGER);
		defaultTypes.put(Long.class, TypeConstants.LONG);
		defaultTypes.put(long.class, TypeConstants.LONG);
		defaultTypes.put(Float.class, TypeConstants.FLOAT);
		defaultTypes.put(float.class, TypeConstants.FLOAT);
		defaultTypes.put(Double.class, TypeConstants.DOUBLE);
		defaultTypes.put(double.class, TypeConstants.DOUBLE);
		defaultTypes.put(boolean.class, TypeConstants.BOOLEAN);
		defaultTypes.put(Boolean.class, TypeConstants.BOOLEAN);
		defaultTypes.put(byte[].class, TypeConstants.IMAGE);
		
		defaultParameters.put("comboList", TypeConstants.COMBO);
		defaultParameters.put("comboModelClass", TypeConstants.COMBO);
		defaultParameters.put("dependentProperty", TypeConstants.DEPENDENT_COMBO);
		defaultParameters.put("list", TypeConstants.MULTIPLE_LIST);
		defaultParameters.put("listModelClass", TypeConstants.MULTIPLE_LIST);
	}
	
	public static void registerTypeMapping(String typeName, Class<? extends ComponentWrapper> wrapperClass){
		typeMapping.put(typeName,wrapperClass);		
	}
	
	public static void registerDefaultType(Class returnClass, String typeName){
		defaultTypes.put(returnClass, typeName);		
	}
	
	public static void registerDefaultParameter(String parameterName, String typeName){
		defaultParameters.put(parameterName, typeName);		
	}

	public static ComponentWrapper createWrapper(String property, FieldDescriptor descriptor){
		String type = getComponentType(property, descriptor);
		if(type == null){
			Class propertyClass = BeanUtils.getPropertyClass(descriptor.getBeanClass(),property);
			throw new ComponentCreationException(property + " : Não existe componente para a classe "+propertyClass.getName());
		}
		Class<? extends ComponentWrapper> wrapperClass = typeMapping.get(type);
		if(wrapperClass == null){
			throw new ComponentCreationException(property + " : Não existe componente para o tipo "+type);
		}
		try {
			ComponentWrapper wrapper = wrapperClass.newInstance();
			Map<String,String> parameters = descriptor.getParameters(property);
			for(String parameter : parameters.keySet())
				BeanUtils.setProperty(wrapper,parameter, parameters.get(parameter));
			wrapper.initComponent();
			wrapper.getComponent().setName(property);
			return wrapper;
		} catch (Exception e) {
			throw new ComponentCreationException("Problemas ao criar o componente da propriedade "+property, e);
		}

	}
	
	public static String getComponentType(String property, FieldDescriptor descriptor){
		String type = descriptor.getParameter(property,"type");
		if(type == null){
			Map<String,String> parameters = descriptor.getParameters(property);
			for(String parameter : parameters.keySet())
				if(defaultParameters.containsKey(parameter))
					return defaultParameters.get(parameter);
			Class propertyClass = BeanUtils.getPropertyClass(descriptor.getBeanClass(),property);
			if(defaultTypes.containsKey(propertyClass))
				return defaultTypes.get(propertyClass);
		}
		return type;
	}

}
