package org.swingBean.example.panelbinding;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.swingBean.actions.ApplicationAction;
import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.XMLDescriptorFactory;
import org.swingBean.gui.JActButton;
import org.swingBean.gui.JBeanPanel;
import org.swingBean.util.BeanUtils;

public class PanelBinding {

    public static void main(String[] args) {
        // Cria componente de formulário
        GenericFieldDescriptor descriptor = XMLDescriptorFactory.getFieldDescriptor(
                Cachorro.class,
                "org\\swingBean\\example\\panelbinding\\cachorroForm.xml",
                "CachorroForm");
        final JBeanPanel<Cachorro> panel = new JBeanPanel<Cachorro>(
                Cachorro.class, descriptor);
        Cachorro original = new Cachorro();
        original.setOutrasInformacoes("Teste");
        final Cachorro c = panel.getSynchoronizedObject(original);

        JActButton botaoExibir = new JActButton("Exibir Nome",
                new ApplicationAction() {

                    public void execute() {
                        JOptionPane.showMessageDialog(panel, c.getNome());
                    }
                });

        JActButton botaoSetar = new JActButton("Setar Nome",
                new ApplicationAction() {

                    public void execute() {
                        c.setNome("Totó");
                    }
                });

        JActButton botaoCopiar = new JActButton("Copiar Objeto",
                new ApplicationAction() {

                    public void execute() {
                        try {
                            Cachorro c2 = (Cachorro) BeanUtils.copyBySerialization(c);
                            JOptionPane.showMessageDialog(panel, c2.getClass().getName());
                            JOptionPane.showMessageDialog(panel, c2.getOutrasInformacoes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        JPanel panelButton = new JPanel();
        panelButton.add(botaoExibir);
        panelButton.add(botaoSetar);
        panelButton.add(botaoCopiar);

        // Cria Frame para exibição da tela
        JFrame frame = new JFrame("Exemplo SwingBean - PanelBinding");
        frame.setSize(600, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(panelButton, BorderLayout.SOUTH);

        frame.setVisible(true);

    }
}
