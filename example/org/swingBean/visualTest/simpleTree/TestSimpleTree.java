package org.swingBean.visualTest.simpleTree;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.swingBean.actions.ApplicationAction;
import org.swingBean.descriptor.*;
import org.swingBean.descriptor.composite.GroupBean;
import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.gui.JActButton;
import org.swingBean.visualTest.ExampleLook;

public class TestSimpleTree {

	public void executa(){
		final JFrame frame = new JFrame();
		frame.setSize(800, 650);
		frame.setLocation(50, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LookProvider.setLook(new ExampleLook());
		
		List<Produto> list = getListProduto();

		//Tree
		final TableFieldDescriptor treeDescriptor = XMLDescriptorFactory.getTableFieldDescriptor(Produto.class, "produtosTree.xml",null);
		final BeanTreeModel treeModel = new BeanTreeModel(treeDescriptor, Produto.class, list);
		
		final JTree tree = new JTree(treeModel);
        JScrollPane scrollPane = new JScrollPane(tree);
        
        //Buttons Panel
        JPanel buttonPanel = new JPanel();
        JActButton botaoExibeSelecionado = new JActButton("Selecionado", new ApplicationAction(){
			public void execute(){JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),tree.getSelectionPath().getLastPathComponent());}
		});
        
        JActButton botaoAdiciona = new JActButton("Adiciona", new ApplicationAction(){
			public void execute(){treeModel.addBean(new Produto("Produto 10"));}
		});
        
        JActButton botaoAdicionaPosicao = new JActButton("Adiciona Posicao", new ApplicationAction(){
			public void execute(){((Produto)((GroupBean)tree.getSelectionPath().getLastPathComponent()).getBean()).addProduto(new Produto("Novo Produto"));  treeModel.refreshTree();}
		});
        
        buttonPanel.add(botaoExibeSelecionado);
        buttonPanel.add(botaoAdiciona);
        buttonPanel.add(botaoAdicionaPosicao);
        
		// Organize Frame
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}


	private List<Produto> getListProduto() {
		List<Produto> list = new ArrayList<Produto>();
		Produto produto1 = new Produto("Produto 1");
		Produto produto2 = new Produto("Produto 2");
		Produto produto3 = new Produto("Produto 3");
		Produto produto4 = new Produto("Produto 4");
		Produto produto5 = new Produto("Produto 5");
		Produto produto6 = new Produto("Produto 6");
		Produto produto7 = new Produto("Produto 7");
		produto1.addProduto(produto2);
		produto2.addProduto(produto3);
		produto2.addProduto(produto4);
		produto5.addProduto(produto6);
		produto5.addProduto(produto7);
		list.add(produto1);
		list.add(produto2);
		list.add(produto3);
		list.add(produto4);
		list.add(produto5);
		list.add(produto6);
		//list.add(produto7);
		return list;
	}


	public static void main(String[] args) {
		TestSimpleTree teste = new TestSimpleTree();
		teste.executa();

	}

}
