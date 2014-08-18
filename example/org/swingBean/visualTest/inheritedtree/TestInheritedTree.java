package org.swingBean.visualTest.inheritedtree;

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

public class TestInheritedTree {

	public void executa(){
		final JFrame frame = new JFrame();
		frame.setSize(800, 650);
		frame.setLocation(50, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LookProvider.setLook(new ExampleLook());
		
		List<Unidade> list = getListUnidade();

		//TreeTable
		final TableFieldDescriptor tableDescriptor = XMLDescriptorFactory.getTableFieldDescriptor(Unidade.class, "unidadeTree.xml",null);
		final BeanTreeTableModel tableModel = new BeanTreeTableModel(tableDescriptor, Unidade.class, list);
		final JBeanTreeTable table = new JBeanTreeTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        //Open Form
        GenericFieldDescriptor descriptor = XMLDescriptorFactory.getFieldDescriptor(Unidade.class, "exemploFormUnidade.xml",null);
        final JBeanPanel<Unidade> panelUnidade = new JBeanPanel<Unidade>(Unidade.class, descriptor);

        //Buttons Panel
        JPanel buttonPanel = new JPanel();
        JActButton inserir = new JActButton("Nova Unidade",new ApplicationAction(){
			public void execute() {
				final JDialog dialog = new JDialog(frame, "Nova Unidade",true);
				dialog.setLayout(new BorderLayout());
				dialog.add(panelUnidade, BorderLayout.CENTER);
				panelUnidade.cleanForm();
				tableModel.setBeanPath(panelUnidade,table.getSelectedComposite());
				JActButton insere = new JActButton("Nova Unidade",new ApplicationAction(){
					public void execute() {
						Unidade unidade = new Unidade();
						panelUnidade.populateBean(unidade);
						tableModel.addBean(unidade);
						panelUnidade.cleanForm();
						dialog.dispose();
					}
				});
				dialog.add(insere,BorderLayout.SOUTH);
				dialog.setLocationRelativeTo(null);
				dialog.setSize(new Dimension(350,200));
				dialog.show();			
			}
        });
        JActButton excluir = new JActButton("Exclui unidade",new ApplicationAction(){
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


	private List<Unidade> getListUnidade() {
		List<Unidade> list = new ArrayList<Unidade>();
		list.add(new Unidade("SuperTour",null,"Gerencia","Brasilia"));
		list.add(new Unidade("Rio Tour","SuperTour","Central de vendas","Rio"));
		list.add(new Unidade("SP Turismo","SuperTour","Central de vendas","São Paulo"));
		list.add(new Unidade("Sem Idéia Tour","Rio Tour","Posto de vendas","Petropolis"));
		list.add(new Unidade("Pão de Queijo Tour","Rio Tour","Posto de vendas","Juiz de Fora"));
		list.add(new Unidade("Vale Tour","SP Turismo","Posto de vendas","SJCampos"));
		return list;
	}


	public static void main(String[] args) {
		TestInheritedTree teste = new TestInheritedTree();
		teste.executa();

	}

}