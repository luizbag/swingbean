package org.swingBean.example.comboloading;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.swingBean.actions.ApplicationAction;
import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.XMLDescriptorFactory;
import org.swingBean.gui.JActButton;
import org.swingBean.gui.JBeanPanel;

public class ComboLoading {

	public static void main(String[] args) {
		GenericFieldDescriptor descriptor = XMLDescriptorFactory
				.getFieldDescriptor(
						Cachorro.class,
						"org\\swingBean\\example\\comboloading\\cachorroForm.xml",
						"CachorroForm");
		final JBeanPanel<Cachorro> panel = new JBeanPanel<Cachorro>(
				Cachorro.class, descriptor);

		JActButton botaoExibir = new JActButton("Exibir Objeto",
				new ApplicationAction() {
					public void execute() {
						Cachorro cao = new Cachorro();
						panel.populateBean(cao);
						StringBuilder builder = new StringBuilder();
						builder.append("Nome: " + cao.getNome() + "\n");
						builder.append("Raça: " + cao.getRaca() + "\n");
						builder.append("Canil: " + cao.getNumeroCanil() + "\n");
						builder.append("Nascimento: "
								+ cao.getDataDeNascimento() + "\n");
						builder.append("Pedigree? " + cao.isTemPedigree() + "\n");
						builder.append("Criador: " + cao.getCriador()+ "\n");
						builder.append("Cor: " + cao.getCor()+ "\n");
						builder.append("Origem: " + cao.getOrigem()+ "\n");
						JOptionPane.showMessageDialog(panel, builder);
					}
				});

		JPanel panelButton = new JPanel();
		panelButton.add(botaoExibir);

		// Cria Frame para exibição da tela
		JFrame frame = new JFrame("Exemplo SwingBean - ComboLoading");
		frame.setSize(600, 270);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.getContentPane().add(panelButton, BorderLayout.SOUTH);

		frame.setVisible(true);

	}

}
