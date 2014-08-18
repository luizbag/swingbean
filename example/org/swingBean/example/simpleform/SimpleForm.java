package org.swingBean.example.simpleform;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

public class SimpleForm {
    
    public static void main(String[] args) {
        // Cria componente de formulário
        GenericFieldDescriptor descriptor = XMLDescriptorFactory
                .getFieldDescriptor(
                Cachorro.class,
                "org\\swingBean\\example\\simpleform\\cachorroForm.xml",
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
                builder.append("Pedigree? " + cao.isTemPedigree());
                JOptionPane.showMessageDialog(panel, builder);
            }
        });
        
        JPanel panelButton = new JPanel();
        panelButton.add(botaoExibir);
        
        // Cria Frame para exibição da tela
        JFrame frame = new JFrame("Exemplo SwingBean - SimpleForm");
        frame.setSize(600, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(panelButton, BorderLayout.SOUTH);
        
        frame.setVisible(true);
        
    }
    
}
