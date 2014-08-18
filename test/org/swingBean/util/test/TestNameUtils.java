package org.swingBean.util.test;

import junit.framework.TestCase;

import org.swingBean.util.NameUtils;

public class TestNameUtils extends TestCase {
	
    public void testTransformAcessorToProperties(){
        String getterName = "getTest";
        String propertieName = NameUtils.acessorToProperty(getterName);
        assertEquals("Compare the propertie name from the getter",propertieName,"test");
        
        String setterName = "setTest";
        propertieName = NameUtils.acessorToProperty(setterName);
        assertEquals("Compare the propertie name from the setter",propertieName,"test");
		
		String isName = "isTest";
		propertieName = NameUtils.acessorToProperty(isName);
		assertEquals("Compare the propertie name from the boolean getter",propertieName,"test");
		
    }
	
	public void testTransformPropertieToGetter(){
		String propertieName = "test";
		String getterName = NameUtils.propertyToGetter(propertieName);;
        assertEquals("Compare the propertie name from the getter",getterName,"getTest");
		
		getterName = NameUtils.propertyToGetter(propertieName, true);
        assertEquals("Compare the propertie name from the boolean getter",getterName,"isTest");
	}
	
	public void testTransformPropertieToSetter(){
		String propertieName = "test";
		String getterName = NameUtils.propertyToSetter(propertieName);;
        assertEquals("Compare the propertie name from the getter",getterName,"setTest");
	}
	
	public void testTransformPropertyInDefaultName(){
		String propertieName = "testFirst";
		String labelName = NameUtils.toDefaultName(propertieName);;
		assertEquals("Compare the expected default name",labelName,"Test First");
		propertieName = "testFirstWithNumber12";
		labelName = NameUtils.toDefaultName(propertieName);;
		assertEquals("Compare the expected default name whith number",labelName,"Test First With Number 12");
	}
}
