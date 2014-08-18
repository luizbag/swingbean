package org.swingBean.descriptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.swingBean.util.ConfigUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class BackgroundLoadthread extends Thread {

	private static String loadFileName = "descriptor.prop";

	public static String getLoadFileName() {
		return loadFileName;
	}

	public static void setLoadFileName(String loadFileName) {
		BackgroundLoadthread.loadFileName = loadFileName;
	}

	public void run() {
		InputStream xmlStream = null;
		try {
			xmlStream = ConfigUtils.getResourceAsStream(loadFileName);
			XMLReader parser = XMLReaderFactory.createXMLReader();
			LoaderDescriptorXMLHandler handler = new LoaderDescriptorXMLHandler();
			parser.setContentHandler(handler);
			InputSource input = new InputSource(xmlStream);
			parser.parse(input);
		} catch (Exception e) {
			throw new RuntimeException("Can't read descriptors",e);
		} finally{
			try {
				if(xmlStream != null)
					xmlStream.close();
			} catch (IOException e) {
				throw new RuntimeException("Can't close xml streams",e);
			}
		}
	}
	
	public static void loadDescriptors(){
		BackgroundLoadthread thread = new BackgroundLoadthread();
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

}
