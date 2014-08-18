package org.swingBean.example.formtotable;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.swingBean.actions.ApplicationAction;
import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.TableFieldDescriptor;
import org.swingBean.descriptor.XMLDescriptorFactory;
import org.swingBean.gui.JActButton;
import org.swingBean.gui.JBeanPanel;
import org.swingBean.gui.JBeanTable;

public class FormToTable {

	public static void main(String[] args) {
		GenericFieldDescriptor formdescriptor = XMLDescriptorFactory
				.getFieldDescriptor(
						Cachorro.class,
						"org\\swingBean\\example\\formtotable\\cachorroForm.xml",
						"CachorroForm");
		final JBeanPanel<Cachorro> panel = new JBeanPanel<Cachorro>(
				Cachorro.class, formdescriptor);

		TableFieldDescriptor tabledescriptor = XMLDescriptorFactory
				.getTableFieldDescriptor(
						Cachorro.class,
						"org\\swingBean\\example\\formtotable\\cachorroTable.xml",
						"CachorroTable");
		final BeanTableModel<Cachorro> model = new BeanTableModel<Cachorro>(
				tabledescriptor);
		final JBeanTable table = new JBeanTable(model);

		JActButton botaoParaTabela = new JActButton("Copiar para Tabela",
				new ApplicationAction() {
					public void execute() {
						Cachorro cao = new Cachorro();
						panel.populateBean(cao);
						model.addBean(cao);
					}
				});
		
		JActButton botaoLimpaForm = new JActButton("Limpar Formulário",
				new ApplicationAction() {
					public void execute() {
						panel.cleanForm();
					}
				});
		
		JActButton botaoLimpaTabela = new JActButton("Limpar Tabela",
				new ApplicationAction() {
					public void execute() {
						model.setBeanList(new ArrayList<Cachorro>());
					}
				});

		table.addDoubleClickAction(new ApplicationAction() {
			public void execute() {
				Cachorro cao = model.getBeanAt(table.getSelectedRow());
				panel.setBean(cao);
			}
		});

		JPanel panelButton = new JPanel();
		panelButton.add(botaoParaTabela);
		panelButton.add(botaoLimpaForm);
		panelButton.add(botaoLimpaTabela);

		// Cria Frame para exibição da tela
		JFrame frame = new JFrame("Exemplo SwingBean - FormToTable");
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		frame.getContentPane().add(panelButton, BorderLayout.SOUTH);

		frame.setVisible(true);

	}

}
