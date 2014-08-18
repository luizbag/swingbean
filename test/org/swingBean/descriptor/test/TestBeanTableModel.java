package org.swingBean.descriptor.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.swingBean.descriptor.BeanTableModel;

public class TestBeanTableModel extends TestCase {
	
	public class StringVO{
		
		public StringVO(){}
		
		public StringVO(String nome, String sobrenome){
			this.nome = nome;
			this.sobrenome = sobrenome;
		}
		
		private String nome;
		private String sobrenome;
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getSobrenome() {
			return sobrenome;
		}
		public void setSobrenome(String sobrenome) {
			this.sobrenome = sobrenome;
		}
	}
	
	public void testCreation(){
		
		BeanTableModel<StringVO> model = new BeanTableModel<StringVO>(StringVO.class);
		StringVO eduardo = new StringVO("Eduardo","Guerra");
		StringVO pedro = new StringVO("Pedro","Cavalero");
		
		model.addBean(eduardo);
		model.addBean(pedro);
		
		assertEquals(model.getValueAt(0,0),"Eduardo");
		assertEquals(model.getValueAt(0,1),"Guerra");
		assertEquals(model.getValueAt(1,0),"Pedro");
		assertEquals(model.getValueAt(1,1),"Cavalero");
		assertEquals(model.getColumnCount(),2);
		assertEquals(model.getRowCount(),2);
		assertEquals(model.getColumnClass(0),String.class);
		assertEquals(model.getColumnClass(1),String.class);
		assertEquals(model.getBeanAt(0),eduardo);
		assertEquals(model.getBeanAt(1),pedro);
	}
	
	public void testInsertedDeletedUpdated(){
		
		BeanTableModel<StringVO> model = new BeanTableModel<StringVO>(StringVO.class);
		List<StringVO> lista = new ArrayList<StringVO>();
		StringVO eduardo = new StringVO("Eduardo","Guerra");
		StringVO pedro = new StringVO("Pedro","Cavalero");
		StringVO stemps = new StringVO("Bruno","Stemposky");
		StringVO cunha = new StringVO("Rodrigo","Cunha");
		StringVO arruda = new StringVO("Marcos","Arruda");
		
		lista.add(eduardo);
		lista.add(pedro);
		lista.add(stemps);
		lista.add(cunha);
		model.setBeanList(lista);
		
		model.addBean(arruda);
		model.deleteBeanAt(1);
		model.setValueAt("Cunhoso",2,1);
		model.setValueAt("Arrudoso",3,1);
		
		List<StringVO> inserted = model.getInserted();
		List<StringVO> deleted = model.getDeleted();
		List<StringVO> updated = model.getUpdated();
		
		assertTrue("The inserted line",inserted.get(0) == arruda);
		assertTrue("The deleted line",deleted.get(0) == pedro);
		assertTrue("The updated line",updated.get(0) == cunha);
		assertTrue("Just have one updated, because the other is inserted",updated.size() == 1);
		
		model.deleteBeanAt(3);
		model.deleteBeanAt(2);
		
		inserted = model.getInserted();
		deleted = model.getDeleted();
		updated = model.getUpdated();
		
		assertTrue("The inserted line is deleted",inserted.size() == 0);
		assertTrue("The inserted line is not include in the deleted list",deleted.size() == 2);
		assertTrue("The updated line is deleted",updated.size() == 0);

	}

}
