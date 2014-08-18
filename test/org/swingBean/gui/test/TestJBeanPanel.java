package org.swingBean.gui.test;

import java.util.Date;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import junit.framework.TestCase;

import org.swingBean.descriptor.DependentComboModel;
import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.TypeConstants;
import org.swingBean.gui.JBeanPanel;

import com.toedter.calendar.JDateChooser;

public class TestJBeanPanel extends TestCase {
	
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

	public class TestDateField{
		
		private Date data;

		public Date getData() {
			return data;
		}
		public void setData(Date data) {
			this.data = data;
		}
	}
	
	public class TestBooleanField{
		
		private boolean verdadeiro;
		private boolean falso;
		
		public boolean isFalso() {
			return falso;
		}
		public void setFalso(boolean falso) {
			this.falso = falso;
		}
		public boolean isVerdadeiro() {
			return verdadeiro;
		}
		public void setVerdadeiro(boolean verdadeiro) {
			this.verdadeiro = verdadeiro;
		}
	}
	
	public class TestNumericField{	
		private double decimal;
		private float pontoFlutuante;
		private int inteiro;
		private long longo;
		
		public long getLongo() {
			return longo;
		}
		public void setLongo(long longo) {
			this.longo = longo;
		}
		public float getPontoFlutuante() {
			return pontoFlutuante;
		}
		public void setPontoFlutuante(float pontoFlutuante) {
			this.pontoFlutuante = pontoFlutuante;
		}
		public double getDecimal() {
			return decimal;
		}
		public void setDecimal(double decimal) {
			this.decimal = decimal;
		}
		public int getInteiro() {
			return inteiro;
		}
		public void setInteiro(int inteiro) {
			this.inteiro = inteiro;
		}
	}
	
	public class ObjectVO{
		private int id;
		private String nome;
		
		public ObjectVO(int id, String nome) {
			super();
			this.id = id;
			this.nome = nome;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public boolean equals(Object obj) {
			if(obj == null)
				return false;
			return this.id == ((ObjectVO)obj).getId();
		}
		
	}	
	
	public class TestObjectField{
		ObjectVO obj;
		public ObjectVO getObj() {
			return obj;
		}
		public void setObj(ObjectVO obj) {
			this.obj = obj;
		}
	}
	
	public void testCreation(){
		TestVO test = new TestVO();
		test.setEstadoCivilNovo("1");
		test.setNomeTeste("2");
		test.setValorTexto("3");
		
		JBeanPanel<TestVO> beanPanel = new JBeanPanel<TestVO>(TestVO.class);
		beanPanel.setBean(test);
		String estadoCivilNovo = ((JTextField)beanPanel.getComponent("estadoCivilNovo")).getText();
		String nomeTeste = ((JTextField)beanPanel.getComponent("nomeTeste")).getText();
		String valorTexto = ((JTextField)beanPanel.getComponent("valorTexto")).getText();
		
		assertEquals("Component creation and evaluation",estadoCivilNovo,"1");
		assertEquals("Component creation and evaluation",nomeTeste,"2");
		assertEquals("Component creation and evaluation",valorTexto,"3");
		
		((JTextField)beanPanel.getComponent("estadoCivilNovo")).setText("4");
		((JTextField)beanPanel.getComponent("nomeTeste")).setText("5");
		((JTextField)beanPanel.getComponent("valorTexto")).setText("6");
		
		beanPanel.populateBean(test);
		
		assertEquals("Component recovering",test.getEstadoCivilNovo(),"4");
		assertEquals("Component recovering",test.getNomeTeste(),"5");
		assertEquals("Component recovering",test.getValorTexto(),"6");
	}
	
	public void testDateField(){
		TestDateField test = new TestDateField();
		Date data1 = new Date();
		Date data2 = new Date(data1.getTime()+24*60*60*1000);
		test.setData(data1);
		
		JBeanPanel<TestDateField> beanPanel = new JBeanPanel<TestDateField>(TestDateField.class);
		beanPanel.setBean(test);
		assertTrue("Verifying the JDateChooser component",beanPanel.getComponent("data") instanceof JDateChooser);
		assertEquals("Verifying the JDateChooser value",((JDateChooser)beanPanel.getComponent("data")).getDate(),data1);
		((JDateChooser)beanPanel.getComponent("data")).setDate(data2);
		
		beanPanel.populateBean(test);
		
		assertEquals("Verifying the Bean value",test.getData(),data2);
	}
	
	public void testBooleanField(){
		TestBooleanField test = new TestBooleanField();
		test.setFalso(false);
		test.setVerdadeiro(true);
				
		JBeanPanel<TestBooleanField> beanPanel = new JBeanPanel<TestBooleanField>(TestBooleanField.class);
		beanPanel.setBean(test);
		assertTrue("Verifying the JCheckBox component",beanPanel.getComponent("verdadeiro") instanceof JCheckBox);
		assertTrue("Verifying the JCheckBox value verdadeiro",((JCheckBox)beanPanel.getComponent("verdadeiro")).isSelected());
		assertFalse("Verifying the JCheckBox value falso",((JCheckBox)beanPanel.getComponent("falso")).isSelected());
	}
	
	public void testNumericField(){
		TestNumericField test = new TestNumericField();
		test.setDecimal(123.123);
		test.setPontoFlutuante(12.345f);
		test.setInteiro(123);
		test.setLongo(123123);
		
		JBeanPanel<TestNumericField> beanPanel = new JBeanPanel<TestNumericField>(TestNumericField.class);
		beanPanel.setBean(test);
		assertTrue("Verifying the JFormattedTextField component",beanPanel.getComponent("decimal") instanceof JFormattedTextField);
		assertTrue("Verifying the JFormattedTextField component",beanPanel.getComponent("inteiro") instanceof JFormattedTextField);
		assertEquals("Get double value","123,123",((JFormattedTextField)beanPanel.getComponent("decimal")).getText());
		assertEquals("Get float value","12,345",((JFormattedTextField)beanPanel.getComponent("pontoFlutuante")).getText());
		assertEquals("Get int value","123",((JFormattedTextField)beanPanel.getComponent("inteiro")).getText());
		assertEquals("Get long value","123.123",((JFormattedTextField)beanPanel.getComponent("longo")).getText());
		
		((JFormattedTextField)beanPanel.getComponent("inteiro")).setText("321");
		((JFormattedTextField)beanPanel.getComponent("longo")).setText("321.321");
		((JFormattedTextField)beanPanel.getComponent("decimal")).setText("321,321");
		((JFormattedTextField)beanPanel.getComponent("pontoFlutuante")).setText("54,321");
		
		
		beanPanel.populateBean(test);
		assertEquals("Get double value",321.321,test.getDecimal());
		assertEquals("Get float value",54.321f,test.getPontoFlutuante());
		assertEquals("Get int value",321,test.getInteiro());
		assertEquals("Get long value",321321,test.getLongo());
	}
	
	public void testCustomTextFields(){
		TestVO test = new TestVO();
		test.setValorTexto("Valor 2");
		
		GenericFieldDescriptor descriptor = new GenericFieldDescriptor(TestVO.class);
		descriptor.setParameter("estadoCivilNovo","type",TypeConstants.LARGE_TEXT);
		descriptor.setParameter("nomeTeste","type",TypeConstants.PASSWORD);
		descriptor.setParameter("valorTexto","comboList","Valor 1,Valor 2,Valor 3");
		
		JBeanPanel<TestVO> beanPanel = new JBeanPanel<TestVO>(TestVO.class,descriptor);
		beanPanel.setBean(test);
		assertTrue("Verifying the JTextArea component (inside JScrollPane)",beanPanel.getComponent("estadoCivilNovo") instanceof JScrollPane);
		assertTrue("Verifying the JPasswordField component",beanPanel.getComponent("nomeTeste") instanceof JPasswordField);
		assertTrue("Verifying the JComboBox component",beanPanel.getComponent("valorTexto") instanceof JComboBox);
		assertEquals("Verifying the JComboBox component",((JComboBox)beanPanel.getComponent("valorTexto")).getSelectedIndex(), 1);
		
		((JComboBox)beanPanel.getComponent("valorTexto")).setSelectedIndex(0);
		beanPanel.populateBean(test);
		assertEquals("Verifying the bean value",test.getValorTexto(),"Valor 1");
	}
	
	public void testDependentCombos(){
		TestVO test = new TestVO();
		
		GenericFieldDescriptor descriptor = new GenericFieldDescriptor(TestVO.class);
		//descriptor.setComboModel("valorTexto",new DefaultComboBoxModel(new String[]{"Valor 1", "Valor 2"}));
		//descriptor.setComboDependence("estadoCivilNovo","valorTexto", 
		//		new DependentComboModel(){
		//	
		//		public ComboBoxModel getComboModel(Object value) {
		//			if(((String)value).equals("Valor 1"))
		//				return new DefaultComboBoxModel(new String[]{"1","2"});
		//			else
		//				return new DefaultComboBoxModel(new String[]{"3","4"});
		//			}
		//		});
		
		JBeanPanel<TestVO> beanPanel = new JBeanPanel<TestVO>(TestVO.class,descriptor);
		
		((JComboBox)beanPanel.getComponent("valorTexto")).setSelectedIndex(0);
		((JComboBox)beanPanel.getComponent("estadoCivilNovo")).setSelectedIndex(0);
		
		beanPanel.populateBean(test);
					
		assertEquals("Verifying the estadoCivilNovo value",test.getEstadoCivilNovo(),"1");
		
		((JComboBox)beanPanel.getComponent("valorTexto")).setSelectedIndex(1);
		((JComboBox)beanPanel.getComponent("estadoCivilNovo")).setSelectedIndex(0);
		
		beanPanel.populateBean(test);
		
		assertEquals("Verifying the estadoCivilNovo value",test.getEstadoCivilNovo(),"3");
	}
	
	public void testObjectField(){
		TestObjectField obj = new TestObjectField();
		obj.setObj(new ObjectVO(2,"Teste2"));
		
		GenericFieldDescriptor descriptor = new GenericFieldDescriptor(TestObjectField.class);
		//descriptor.setComboModel("obj",new DefaultComboBoxModel(new ObjectVO[]{new ObjectVO(1,"Teste1"), new ObjectVO(2,"Teste2")}));
		
		JBeanPanel<TestObjectField> beanPanel = new JBeanPanel<TestObjectField>(TestObjectField.class,descriptor);
		
		beanPanel.setBean(obj);
		
		assertEquals(((JComboBox)beanPanel.getComponent("obj")).getSelectedIndex(),1);
		((JComboBox)beanPanel.getComponent("obj")).setSelectedIndex(0);
		
		beanPanel.populateBean(obj);
		
		assertEquals(obj.getObj(),new ObjectVO(1,"Teste1"));
	}

}
