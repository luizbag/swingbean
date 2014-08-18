package org.swingBean.descriptor;

import java.io.File;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LoaderDescriptorXMLHandler extends DefaultHandler {
	
	public void startElement(String uri, String localpart, String name, Attributes atrib) throws SAXException {
		try {
			if(name.equals("descriptor")){
				String filename = atrib.getValue("file");
				String classname = atrib.getValue("class");
				String descriptorname = atrib.getValue("name");
				String type = atrib.getValue("type");
				
				if(filename != null && classname != null){
					if(type != null && type.equals("table"))
						XMLDescriptorFactory.getTableFieldDescriptor(Class.forName(classname), filename,descriptorname);
					else
						XMLDescriptorFactory.getFieldDescriptor(Class.forName(classname), filename,descriptorname);
				}
			}
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}

}
