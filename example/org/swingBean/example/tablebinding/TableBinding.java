package org.swingBean.example.tablebinding;

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
import org.swingBean.gui.JActButton;
import org.swingBean.gui.JBeanTable;
import org.swingBean.util.BeanUtils;

public class TableBinding {

	public static void main(String[] args) {
		TableFieldDescriptor tabledescriptor = XMLDescriptorFactory
				.getTableFieldDescriptor(
						Cachorro.class,
						"org\\swingBean\\example\\tablebinding\\cachorroTable.xml",
						"CachorroTable");
		final BeanTableModel<Cachorro> model = new BeanTableModel<Cachorro>(
				tabledescriptor);
		final JBeanTable table = new JBeanTable(model);
		final List<Cachorro> list = model.getSynchronizedList();

		JActButton botaoAdiciona = new JActButton("Adicionar",
				new ApplicationAction() {
					public void execute() {
						Cachorro c = new Cachorro("Totó","Basset", new Date(),false);
						list.add(c);
					}
				});

		JActButton botaoRemove = new JActButton("Remover",
				new ApplicationAction() {
					public void execute() {
						list.remove(0);
					}
				});

		JActButton botaoAlteraPropriedade = new JActButton("Altera",
				new ApplicationAction() {
					public void execute() {
						list.get(0).setNome("Neo");
					}
				});
		
		JActButton botaoExibir = new JActButton("Exibe",
				new ApplicationAction() {
					public void execute() {
						JOptionPane.showMessageDialog(null,list.get(0).getNome());
					}
				});
		
		JActButton botaoExibirPropriedadeNaoExibida = new JActButton("Exibe 2",
				new ApplicationAction() {
					public void execute() {
						list.get(0).setNumeroCanil(5);
						JOptionPane.showMessageDialog(null,list.get(0).getNumeroCanil());
					}
				});
		
		JActButton botaoCopiaLista = new JActButton("Copia Lista",
				new ApplicationAction() {
					public void execute() {
						try{
							Object obj = BeanUtils.copyBySerialization((Serializable)list);
							JOptionPane.showMessageDialog(null, obj.getClass().getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		
		JActButton botaoCopiaObj = new JActButton("Copia Obj",
				new ApplicationAction() {
					public void execute() {
						try{
							Object obj = BeanUtils.copyBySerialization(list.get(0));
							JOptionPane.showMessageDialog(null, obj.getClass().getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

		JPanel panelButton = new JPanel();
		panelButton.add(botaoAdiciona);
		panelButton.add(botaoRemove);
		panelButton.add(botaoAlteraPropriedade);
		panelButton.add(botaoExibir);
		panelButton.add(botaoExibirPropriedadeNaoExibida);
		panelButton.add(botaoCopiaLista);
		panelButton.add(botaoCopiaObj);

		// Cria Frame para exibição da tela
		JFrame frame = new JFrame("Exemplo SwingBean - TableBinding");
		frame.setSize(700, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		frame.getContentPane().add(panelButton, BorderLayout.SOUTH);

		frame.setVisible(true);

	}

}
