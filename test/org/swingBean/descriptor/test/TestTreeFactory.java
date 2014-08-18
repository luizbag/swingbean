package org.swingBean.descriptor.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.swingBean.descriptor.composite.CompositeBean;
import org.swingBean.descriptor.composite.CompositeTree;
import org.swingBean.descriptor.composite.GroupBean;
import org.swingBean.descriptor.composite.TreeFactory;

public class TestTreeFactory extends TestCase {
	
	public class TesteVO{
		private String nome;
		private String parametro1;
		private String parametro2;
		private String chefe;
		public TesteVO(String nome, String parametro1, String parametro2, String chefe) {
			this.nome = nome;
			this.parametro1 = parametro1;
			this.parametro2 = parametro2;
			this.chefe = chefe;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getParametro1() {
			return parametro1;
		}
		public void setParametro1(String parametro1) {
			this.parametro1 = parametro1;
		}
		public String getParametro2() {
			return parametro2;
		}
		public void setParametro2(String parametro2) {
			this.parametro2 = parametro2;
		}
		public String getChefe() {
			return chefe;
		}
		public void setChefe(String chefe) {
			this.chefe = chefe;
		}
		public boolean equals(Object obj) {
			return !(obj == null) && nome.equals(((TesteVO)obj).getNome());
		}
		
	}
	
	List<TesteVO> list;
	
	protected void setUp() throws Exception {
		list = new ArrayList<TesteVO>();
		list.add(new TesteVO("Pedro","Homem","AB",null));
		list.add(new TesteVO("Eduardo","Homem","AB",null));
		list.add(new TesteVO("Silvia","Mulher","A","Pedro"));
		list.add(new TesteVO("João","Homem","A","Silvia"));
		list.add(new TesteVO("Maria Eduarda","Mulher","AB","Eduardo"));
	}

	public void testConstrucTreeOneParameter(){
		
		TreeFactory<TesteVO> factory =  new TreeFactory<TesteVO>();
		CompositeBean<TesteVO> root = factory.constructOrganizationalTree("Sexo",list,"parametro1").getRoot();
				
		assertEquals(root.toString(),"Sexo");
		assertTrue(root.getChild().contains(new GroupBean("Homem")));
		assertTrue(root.getChild().contains(new GroupBean("Mulher")));
		assertTrue(((GroupBean<TesteVO>)root).getChild("Homem").getChildCount() == 3);
		assertTrue(((GroupBean<TesteVO>)root).getChild("Mulher").getChildCount() == 2);
		
	}
	
	public void testConstrucTreeTwoParameter(){
		
		TreeFactory<TesteVO> factory =  new TreeFactory<TesteVO>();
		CompositeTree<TesteVO> tree = factory.constructOrganizationalTree("Sangue",list,"parametro2","parametro1");
		GroupBean<TesteVO> root = (GroupBean) tree.getRoot();
				
		assertEquals(root.toString(),"Sangue");
		assertTrue(root.getChild().contains(new GroupBean("A")));
		assertTrue(root.getChild().contains(new GroupBean("AB")));
		assertTrue(root.getChild("A").getChild().contains(new GroupBean("Homem")));
		assertTrue(root.getChild("A").getChild().contains(new GroupBean("Mulher")));
		assertTrue(root.getChild("AB").getChild().contains(new GroupBean("Homem")));
		assertTrue(root.getChild("AB").getChild().contains(new GroupBean("Mulher")));
		
		assertTrue(((GroupBean<TesteVO>)root.getChild("A")).getChild("Homem").getChildCount() == 1);
		assertTrue(((GroupBean<TesteVO>)root.getChild("AB")).getChild("Homem").getChildCount() == 2);
		
		tree.addChild(new TesteVO("Zé","Homem","AB",null));
		assertTrue(((GroupBean<TesteVO>)root.getChild("AB")).getChild("Homem").getChildCount() == 3);
		
	}
	
	public void testConstrucInheritanceTree(){
		TreeFactory<TesteVO> factory =  new TreeFactory<TesteVO>();
		CompositeTree<TesteVO> tree = factory.constructInheritedTree("Empregados","nome","chefe",list);
		GroupBean<TesteVO> root = (GroupBean) tree.getRoot();
		
		assertEquals(root.toString(),"Empregados");
		assertEquals(root.getChild().size(),2);
		assertEquals(root.getChild(list.get(0)).getChild().size(), 1);
		assertEquals(root.getChild(list.get(0)).getChild().get(0).getBean().getNome(), "Silvia");
		assertEquals(root.getChild(list.get(1)).getChild().get(0).getBean().getNome(), "Maria Eduarda");
		assertEquals(root.getChild(list.get(0)).getChild().get(0).getChild().get(0).getBean().getNome(), "João");
		
		tree.addChild(new TesteVO("Zé","Homem","AB","Pedro"));
		assertEquals(root.getChild(list.get(0)).getChild().size(), 2);
	}

}
