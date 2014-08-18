package org.swingBean.descriptor.test;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.TypeConstants;
import org.swingBean.descriptor.XMLDescriptorFactory;

public class TestXMLDescriptor extends TestCase {
	
	public class TestVO{
		private String nomeTeste;
		private String valorTexto;
		private String estadoCivilNovo;
		private String sexo;
		
		public String getSexo() {
			return sexo;
		}
		public void setSexo(String sexo) {
			this.sexo = sexo;
		}
		public String getEstadoCivilNovo() {
			return estadoCivilNovo;
		}
		public void setEstadoCivilNovo(String estadoCivilNovo) {
			this.estadoCivilNovo = estadoCivilNovo;
		}
		public String getNomeTeste() {
			return nomeTeste;
		}
		public void setNomeTeste(String nomeTeste) {
			this.nomeTeste = nomeTeste;
		}
		public String getValorTexto() {
			return valorTexto;
		}
		public void setValorTexto(String valorTexto) {
			this.valorTexto = valorTexto;
		}
	}

	
	public void testNumberOfLines() throws Exception{
		
		String xml = "<beanDescriptor>"+
					 "<line><property name='nomeTeste' /></line>"+
					 "<line><property name='valorTexto' /></line>"+
					 "<line><property name='estadoCivilNovo' /></line>"+
					 "</beanDescriptor>";
		
		ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
		
		GenericFieldDescriptor descriptor = XMLDescriptorFactory.getFieldDescriptor(TestVO.class,stream, null);
		assertEquals("Verifying line numbers",descriptor.getLineLenght(),3);
		assertEquals("Verifying property 1",descriptor.getLinePropertys(0)[0],"nomeTeste");
		assertEquals("Verifying property 2",descriptor.getLinePropertys(1)[0],"valorTexto");
		assertEquals("Verifying property 3",descriptor.getLinePropertys(2)[0],"estadoCivilNovo");
	}
	
	public void testManyPropertysInLine() throws Exception{
		
		String xml = "<beanDescriptor>"+
					 "<line><property name='valorTexto' /></line>"+
					 "<line><property name='estadoCivilNovo' /><property name='nomeTeste' /></line>"+
					 "</beanDescriptor>";
		
		ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
		
		GenericFieldDescriptor descriptor = XMLDescriptorFactory.getFieldDescriptor(TestVO.class,stream,null);
		assertEquals("Verifying line numbers",descriptor.getLineLenght(),2);
		assertEquals("Verifying property 1",descriptor.getLinePropertys(0)[0],"valorTexto");
		assertEquals("Verifying property 2",descriptor.getLinePropertys(1)[0],"estadoCivilNovo");
		assertEquals("Verifying property 3",descriptor.getLinePropertys(1)[1],"nomeTeste");

	}
	
	public void testSpecialPropertys() throws Exception{
		
		String xml = "<beanDescriptor>"+
					 "<line>" +
					 "		<property name='valorTexto' label='Somente Texto' type='LARGE_TEXT' colspan='2'/>" +
					 "		<property name='sexo' comboList='Masculino;Feminino' />" +
					 "</line>"+
					 "<line>" +
					 "      <property name='estadoCivilNovo' comboModelClass='org.swingBean.descriptor.test.DummyComboModel' />" +
					 "      <property name='nomeTeste' comboModelClass='org.swingBean.descriptor.test.DummyDependentModel' dependentProperty='estadoCivilNovo' />" +
					 "</line>"+
					 "</beanDescriptor>";
		
		ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
		
		GenericFieldDescriptor descriptor = XMLDescriptorFactory.getFieldDescriptor(TestVO.class,stream, null);
		assertEquals("Verifing label property",descriptor.getParameter("valorTexto","label"),"Somente Texto");
		assertEquals("Verifing type property",descriptor.getParameter("valorTexto","type"),TypeConstants.LARGE_TEXT);
		assertEquals("Verifing combo property",Class.forName(descriptor.getParameter("estadoCivilNovo","comboModelClass")),DummyComboModel.class);
		//assertTrue("Verifing dependent combo model",descriptor.getDependentModel("nomeTeste") instanceof DummyDependentModel);
		assertEquals("Verifing dependent combo property",descriptor.getDependentProperty("nomeTeste"),"estadoCivilNovo");
		//assertEquals("Verifing list combo property",descriptor.getComboModel("sexo").getElementAt(0),"Masculino");
		//assertEquals("Verifing list combo property",descriptor.getComboModel("sexo").getElementAt(1),"Feminino");
		assertEquals("Verifing the colspan property",descriptor.getColSpan("valorTexto"),2);


	}

}
