package org.swingBean.descriptor.test;

import junit.framework.TestCase;

import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.TypeConstants;

public class TestGenericFieldDescriptor extends TestCase {
	
	public class TestVO{
		private String nomeTeste;
		private String valorTexto;
		private String estadoCivilNovo;
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
	
	public void testDefaultValuesText(){
		GenericFieldDescriptor des = new GenericFieldDescriptor(TestVO.class);
		assertEquals("Always one field per line",des.getNumberColumns(0),1);
		assertEquals("Number of lines equals number of properties ",des.getLineLenght(),3);
		//The order is alphabetic
		String[] properties = new String[] {"estadoCivilNovo","nomeTeste","valorTexto"};
		String[] labels = new String[] {"Estado Civil Novo","Nome Teste","Valor Texto"};;
		for(int i=0;i<3;i++){
			assertEquals("Name of property",des.getLinePropertys(i)[0],properties[i]);
			assertEquals("Name of label",des.getParameter("label",des.getLinePropertys(i)[0]),labels[i]);
			//assertEquals("Property TYPE is always TEXT",des.getPropertyComponentType(des.getLinePropertys(i)[0]),TypeConstants.TEXT);
		}
	}
	
}
