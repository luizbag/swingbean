package org.swingBean.descriptor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.swingBean.exception.DescriptorCreationException;
import org.swingBean.util.ConfigUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLDescriptorFactory{
	
	private static Map<String,GenericFieldDescriptor> cache;
	
	static{
		cache = new HashMap<String,GenericFieldDescriptor>();
	}
	
	public static GenericFieldDescriptor getFieldDescriptor(Class beanClass, String config, String name) {
		return getFieldDescriptor(beanClass,ConfigUtils.getResourceAsStream(config), name);
	}
	
	public static GenericFieldDescriptor getFieldDescriptor(Class beanClass, File xmlFile, String name){
		try {
			return getFieldDescriptor(beanClass,new FileInputStream(xmlFile), name);
		} catch (Exception e) {
			throw new DescriptorCreationException("O Descriptor não pode ser criado",e);
		}
	}
	
	public static GenericFieldDescriptor getFieldDescriptor(Class beanClass, InputStream xmlStream, String name){
		GenericFieldDescriptor descriptor = new GenericFieldDescriptor(beanClass);
		return getFieldDescriptor(xmlStream, descriptor, name);
	}

	private static GenericFieldDescriptor getFieldDescriptor(InputStream xmlStream, GenericFieldDescriptor descriptor, String name) {
		try {
			synchronized(cache){
				if(name != null && cache.containsKey(name))
					return cache.get(name);
				XMLReader parser = XMLReaderFactory.createXMLReader();
				BeanDescriptorXMLHandler handler = new BeanDescriptorXMLHandler(descriptor);
				parser.setContentHandler(handler);
				descriptor.setName(name);
				InputSource input = new InputSource(xmlStream);
				parser.parse(input);
				if(name != null)
					cache.put(name, descriptor);
				}
			return descriptor;
		} catch (Exception e) {
			throw new DescriptorCreationException("O Descriptor não pode ser criado",e);
		} finally{
			try {
				if(xmlStream != null)
					xmlStream.close();
			} catch (IOException e) {
				throw new RuntimeException("Can't close xml streams",e);
			}
		}
	}
	
	public static TableFieldDescriptor getTableFieldDescriptor(Class beanClass, String config, String name) {
		return getTableFieldDescriptor(beanClass,ConfigUtils.getResourceAsStream(config), name);
	}
	
	public static TableFieldDescriptor getTableFieldDescriptor(Class beanClass, File xmlFile, String name){
		try {
			return getTableFieldDescriptor(beanClass,new FileInputStream(xmlFile), name);
		} catch (Exception e) {
			throw new DescriptorCreationException("O Descriptor não pode ser criado",e);
		}
	}
	
	public static TableFieldDescriptor getTableFieldDescriptor(Class beanClass, InputStream xmlStream, String name){
		TableFieldDescriptor descriptor = new TableFieldDescriptor(beanClass);
		return (TableFieldDescriptor)getFieldDescriptor(xmlStream, descriptor, name);
	}
	

}
