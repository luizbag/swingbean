package org.swingBean.example.tabledata;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.swingBean.actions.ApplicationAction;
import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.descriptor.TableFieldDescriptor;
import org.swingBean.descriptor.XMLDescriptorFactory;
import org.swingBean.example.tablebinding.Cachorro;
import org.swingBean.gui.JActButton;
import org.swingBean.gui.JBeanTable;
import org.swingBean.util.BeanUtils;

public class TableData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TableFieldDescriptor tabledescriptor = XMLDescriptorFactory
				.getTableFieldDescriptor(
						Cachorro.class,
						"org\\swingBean\\example\\tabledata\\cachorroTable.xml",
						"CachorroTable");
		final BeanTableModel<Cachorro> model = new BeanTableModel<Cachorro>(
				tabledescriptor);
		final JBeanTable table = new JBeanTable(model);
		
		model.addBean(new Cachorro("Neo","Yorkshire", new Date(),true));
		model.addBean(new Cachorro("Link","Yorkshire", new Date(),true));
		model.addBean(new Cachorro("Dondoca","Basset", new Date(),false));
		model.resetCounters();

		JActButton botaoAdiciona = new JActButton("Adicionar",
				new ApplicationAction() {
					public void execute() {
						model.addBean(new Cachorro());
					}
				});

		JActButton botaoRemove = new JActButton("Remover",
				new ApplicationAction() {
					public void execute() {
						model.deleteBeanAt(table.getSelectedRow());
					}
				});

		JActButton botaoInseridos = new JActButton("Mostra Inseridos",
				new ApplicationAction() {
					public void execute() {
						StringBuilder b = new StringBuilder();
						for(Cachorro c:model.getInserted()){
							b.append("- " + c.getNome() + "\n");
						}
						JOptionPane.showMessageDialog(null, b);
					}
				});
		
		JActButton botaoRemovidos = new JActButton("Mostra Removidos",
				new ApplicationAction() {
					public void execute() {
						StringBuilder b = new StringBuilder();
						for(Cachorro c:model.getDeleted()){
							b.append("- " + c.getNome() + "\n");
						}
						JOptionPane.showMessageDialog(null, b);
					}
				});
		
		JActButton botaoAtualizados = new JActButton("Mostra Atualizados",
				new ApplicationAction() {
					public void execute() {
						StringBuilder b = new StringBuilder();
						for(Cachorro c:model.getUpdated()){
							b.append("- " + c.getNome() + "\n");
						}
						JOptionPane.showMessageDialog(null, b);
					}
				});


		JPanel panelButton = new JPanel();
		panelButton.add(botaoAdiciona);
		panelButton.add(botaoRemove);
		panelButton.add(botaoInseridos);
		panelButton.add(botaoRemovidos);
		panelButton.add(botaoAtualizados);


		// Cria Frame para exibição da tela
		JFrame frame = new JFrame("Exemplo SwingBean - TableData");
		frame.setSize(700, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		frame.getContentPane().add(panelButton, BorderLayout.SOUTH);

		frame.setVisible(true);

	}

}
