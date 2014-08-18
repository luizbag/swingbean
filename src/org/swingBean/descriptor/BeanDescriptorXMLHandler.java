package org.swingBean.descriptor;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import org.swingBean.descriptor.validator.ValidatorFactory;
import org.swingBean.gui.wrappers.WrapperFactory;
import org.swingBean.util.NameUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BeanDescriptorXMLHandler extends DefaultHandler {
	
	GenericFieldDescriptor descriptor;
	ArrayList<ArrayList<String>> paneStructure;
	ArrayList<String> atualLine;

	public BeanDescriptorXMLHandler(GenericFieldDescriptor descriptor) {
		super();
		this.descriptor = descriptor;
		paneStructure = new ArrayList<ArrayList<String>>();
	}

	@Override
	public void startElement(String uri, String localpart, String name, Attributes atrib) throws SAXException {
		try {
			if(name.equals("line")){
				atualLine = new ArrayList<String>();
			}
			if(name.equals("property")){
				String propertyName = atrib.getValue("name");
				atualLine.add(propertyName);
		
				for(int i=0;i<atrib.getLength();i++)
					descriptor.setParameter(propertyName,atrib.getLocalName(i),atrib.getValue(i));
				if(!descriptor.getParameters(propertyName).containsKey("label"))
					descriptor.setParameter(propertyName,"label",NameUtils.toDefaultName(propertyName));
				if(!descriptor.getParameters(propertyName).containsKey("type"))
					descriptor.setParameter(propertyName,"type",WrapperFactory.getComponentType(propertyName,descriptor));
				
				ValidatorFactory factory = new ValidatorFactory();
				factory.createValidators(propertyName,descriptor);
			}
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}
	
	@Override
	public void endElement(String uri, String localpart, String name) throws SAXException {
		if(name.equals("line")){
			paneStructure.add(atualLine);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		String[][] structure = new String[paneStructure.size()][];
		for(int i=0;i<paneStructure.size();i++){
			String[] line = new String[paneStructure.get(i).size()];
			for(int j=0;j<paneStructure.get(i).size();j++){
				line[j] = paneStructure.get(i).get(j);
			}
			structure[i] = line;
		}
		descriptor.setPropertyOrder(structure);
	}

}
