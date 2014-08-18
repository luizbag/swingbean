package org.swingBean.visualTest.organizedtree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.swingBean.actions.ApplicationAction;
import org.swingBean.descriptor.BeanTreeTableModel;
import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.TableFieldDescriptor;
import org.swingBean.descriptor.XMLDescriptorFactory;
import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.gui.JActButton;
import org.swingBean.gui.JBeanPanel;
import org.swingBean.gui.JBeanTreeTable;
import org.swingBean.visualTest.ExampleLook;

public class TestOrganizedTree {

	public void executa(){
		final JFrame frame = new JFrame();
		frame.setSize(800, 650);
		frame.setLocation(50, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LookProvider.setLook(new ExampleLook());
		
		List<Cidade> list = new ArrayList<Cidade>();
		list.add(new Cidade("Brasil","Minas Gerais","Juiz de Fora",500000,9999.99));
		list.add(new Cidade("Brasil","Minas Gerais","Uberlândia",400000,9999.99));
		list.add(new Cidade("Brasil","São Paulo","SJcampos",600000,9999.99));
		list.add(new Cidade("EUA","Oklahoma","Oklahoma",1000000,9999.99));
		list.add(new Cidade("EUA","Florida","Orlando",2000000,9999.99));
		list.add(new Cidade("EUA","Florida","Tampa",50000,9999.99));
		list.add(new Cidade("Brasil","São Paulo","São Paulo",5000000,99999.99));
		
		//TreeTable
		TableFieldDescriptor tableDescriptor = XMLDescriptorFactory.getTableFieldDescriptor(Cidade.class, "missaoTeste.xml",null);
		final BeanTreeTableModel tableModel = new BeanTreeTableModel(tableDescriptor, Cidade.class, list);
		final JBeanTreeTable table = new JBeanTreeTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        //Open Form
        GenericFieldDescriptor descriptor = XMLDescriptorFactory.getFieldDescriptor(Cidade.class, "exemploFormCidade.xml",null);
        final JBeanPanel<Cidade> panelCidade = new JBeanPanel<Cidade>(Cidade.class, descriptor);

        //Buttons Panel
        JPanel buttonPanel = new JPanel();
        JActButton inserir = new JActButton("Nova Cidade",new ApplicationAction(){
			public void execute() {
				final JDialog dialog = new JDialog(frame, "Nova cidade",true);
				dialog.setLayout(new BorderLayout());
				dialog.add(panelCidade, BorderLayout.CENTER);
				tableModel.setBeanPath(panelCidade,table.getSelectedComposite());
				JActButton insere = new JActButton("Nova Cidade",new ApplicationAction(){
					public void execute() {
						Cidade cidade = new Cidade();
						panelCidade.populateBean(cidade);
						tableModel.addBean(cidade);
						panelCidade.cleanForm();
						dialog.dispose();
					}
				});
				dialog.add(insere,BorderLayout.SOUTH);
				dialog.setLocationRelativeTo(null);
				dialog.setSize(new Dimension(350,200));
				dialog.show();			
			}
        });
        JActButton excluir = new JActButton("Exclui cidade",new ApplicationAction(){
			public void execute() {
				tableModel.removeBean(table.getSelectedBean());
			}
        });
        buttonPanel.add(inserir);
        buttonPanel.add(excluir);
        
		// Organize Frame
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
		frame.setVisible(true);
	}


	public static void main(String[] args) {
		TestOrganizedTree teste = new TestOrganizedTree();
		teste.executa();

	}

}
