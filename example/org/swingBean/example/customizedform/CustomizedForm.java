package org.swingBean.example.customizedform;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.swingBean.actions.ApplicationAction;
import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.XMLDescriptorFactory;
import org.swingBean.gui.JActButton;
import org.swingBean.gui.JBeanPanel;


public class CustomizedForm {

	public static void main(String[] args) {
		GenericFieldDescriptor descriptor = XMLDescriptorFactory
				.getFieldDescriptor(
						Dog.class,
						"org\\swingBean\\example\\customizedform\\dogForm.xml",
						"dogForm");
		final JBeanPanel<Dog> panel = new JBeanPanel<Dog>(
				Dog.class, descriptor);

		JActButton botaoExibir = new JActButton("Exibir Objeto",
				new ApplicationAction() {
					public void execute() {
						Dog cao = new Dog();
						panel.populateBean(cao);
						StringBuilder builder = new StringBuilder();
						builder.append("Nome: " + cao.getName() + "\n");
						builder.append("Raça: " + cao.getRace() + "\n");
						builder.append("Canil: " + cao.getKennelNumber() + "\n");
						builder.append("Nascimento: "
								+ cao.getBirthDate() + "\n");
						builder.append("Pedigree? " + cao.isHasPedigree()+ "\n");
						builder.append("Telefone: " + cao.getPhone());
						JOptionPane.showMessageDialog(panel, builder);
					}
				});

		JPanel panelButton = new JPanel();
		panelButton.add(botaoExibir);

		// Cria Frame para exibição da tela
		JFrame frame = new JFrame("Exemplo SwingBean - CustomizedForm");
		frame.setSize(600, 220);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.getContentPane().add(panelButton, BorderLayout.SOUTH);

		frame.setVisible(true);

	}

}
