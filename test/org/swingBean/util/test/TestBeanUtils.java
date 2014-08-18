package org.swingBean.util.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.swingBean.util.BeanUtils;
import org.swingBean.util.PropertyComparator;

public class TestBeanUtils extends TestCase {
	
	TesteVO test;
	
	public class Teste{
		private byte[] teste;
		public byte[] getTeste() {
			return teste;
		}
		public void setTeste(byte[] teste) {
			this.teste = teste;
		}
	}

	protected void setUp() throws Exception {
		test = new TesteVO();
	}
	
	public void testGetPropertyValue(){
		test.setStrProp("String");
		test.setIntProp(35);
		test.setBolProp(true);
		String getStringProp = (String)BeanUtils.getProperty(test,"strProp");
		int getIntProp = (Integer)BeanUtils.getProperty(test,"intProp");
		Object nullProp = BeanUtils.getProperty(test,"notHere");
		boolean isBolProp = (Boolean)BeanUtils.getProperty(test,"bolProp");
		assertEquals("String recover","String",getStringProp);
		assertEquals("Int recover",35,getIntProp);
		assertEquals("Non existent property",null,nullProp);
		assertTrue("Boolean recover",isBolProp);
	}
	
	public void testSetPropertyValue(){
		BeanUtils.setProperty(test,"strProp","String");
		BeanUtils.setProperty(test,"intProp",35);
		BeanUtils.setProperty(test,"nonExistentProp","String");
		BeanUtils.setProperty(test,"bolProp",true);
		assertEquals("String writer","String",test.getStrProp());
		assertEquals("Int writer",35,test.getIntProp());
		assertTrue("Boolean writer",test.isBolProp());
	}
	
	public void testSetStrPropertyValue(){
		BeanUtils.setProperty(test,"intProp","35");
		BeanUtils.setProperty(test,"bolProp","true");
		assertEquals("Int writer",35,test.getIntProp());
		assertTrue("Boolean writer",test.isBolProp());
	}
	
	public void testIsReadOnly(){
		boolean intReadOnly = BeanUtils.isReadOnly(test,"intProp");
		boolean twoIntReadOnly = BeanUtils.isReadOnly(test,"twoInt");
		boolean bolPropReadOnly = BeanUtils.isReadOnly(test,"bolProp");
		assertFalse("intProp has get and set, so is not read only",intReadOnly);
		assertTrue("twoInt has only get, so is read only",twoIntReadOnly);
		assertFalse("bolProp has is and set, so is not read only",bolPropReadOnly);
	}
	
	public void testListPropertys(){
		List<String> propertys = BeanUtils.getPropertyList(test.getClass());
		assertEquals("Has 5 properties", 5, propertys.size());
		assertTrue("Has intProp", propertys.contains("intProp"));
		assertTrue("Has strProp", propertys.contains("strProp"));
		assertTrue("Has twoInt", propertys.contains("twoInt"));
		assertTrue("Has bolProp", propertys.contains("bolProp"));
	}
	
	public void testPropertyComparator(){
		ArrayList<TesteVO> list = new ArrayList<TesteVO>();
		TesteVO t1 = new TesteVO(1,"Maria Eduarda");
		TesteVO t2 = new TesteVO(2,"Roberta");
		TesteVO t3 = new TesteVO(3,"Eduardo");
		TesteVO t4 = new TesteVO(4,null);
		list.add(t2);
		list.add(t3);
		list.add(t4);
		list.add(t1);
		
		Collections.sort(list, new PropertyComparator("strProp"));
		
		assertEquals(list.get(0),t3);
		assertEquals(list.get(1),t1);
		assertEquals(list.get(2),t2);
		assertEquals(list.get(3),t4);
		
		Collections.sort(list, new PropertyComparator("intProp"));
		
		assertEquals(list.get(0),t1);
		assertEquals(list.get(1),t2);
		assertEquals(list.get(2),t3);
		assertEquals(list.get(3),t4);
		
	}
	
	public void testSubPropertys(){
		TesteVO t1 = new TesteVO(1,"Maria Eduarda");
		TesteVO t2 = new TesteVO(2,"Roberta");
		t2.setInnerVO(t1);
		
		assertEquals(BeanUtils.getProperty(t2,"innerVO.strProp"),"Maria Eduarda");
		assertNull(BeanUtils.getProperty(t1,"innerVO.strProp"));
		
		BeanUtils.setProperty(t2,"innerVO.strProp","Novo Nome");
		BeanUtils.setProperty(t1,"innerVO.strProp","Novo Objeto");
		
		assertEquals(t2.getInnerVO().getStrProp(),"Novo Nome");
		assertEquals(t1.getInnerVO().getStrProp(),"Novo Objeto");	
	}
	
	public void testSubPropertyClass(){
		assertEquals(BeanUtils.getPropertyClass(TesteVO.class,"innerVO.strProp"),String.class);	
	}

	public void testPrimitiveClass(){
		assertEquals(byte[].class,BeanUtils.getPropertyClass(Teste.class,"teste"));
	}

}
