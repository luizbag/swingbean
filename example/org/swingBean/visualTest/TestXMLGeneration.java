package org.swingBean.visualTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.swingBean.actions.ActionChainFactory;
import org.swingBean.actions.ApplicationAction;
import org.swingBean.actions.CheckBoxEnableAction;
import org.swingBean.actions.GetFromTable;
import org.swingBean.actions.MessageAction;
import org.swingBean.actions.PutIntoTable;
import org.swingBean.actions.ReloadListAction;
import org.swingBean.actions.ThreadedAction;
import org.swingBean.actions.ValidationAction;
import org.swingBean.descriptor.BackgroundLoadthread;
import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.descriptor.GenericFieldDescriptor;
import org.swingBean.descriptor.TableFieldDescriptor;
import org.swingBean.descriptor.XMLDescriptorFactory;
import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.gui.FormTablePipe;
import org.swingBean.gui.JActButton;
import org.swingBean.gui.JBeanPanel;
import org.swingBean.gui.JBeanTable;

public class TestXMLGeneration {
	
	JBeanTable table;
	JBeanPanel<Funcionario> panel; 

	public void executa(){
		
		//Cria o frame
		JFrame frame = new JFrame();
		frame.setSize(800, 650);
		frame.setLocation(50, 50);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		long time = System.currentTimeMillis();
		
		//Seta a vizualização
		LookProvider.setLook(new ExampleLook());
		
		//Cria o form
		GenericFieldDescriptor descriptor = XMLDescriptorFactory.getFieldDescriptor(Funcionario.class, "FuncionarioForm.xml","FuncionarioForm");
		panel = new JBeanPanel<Funcionario>(Funcionario.class, descriptor);

		//Cria a JTable
		TableFieldDescriptor tableDescriptor = XMLDescriptorFactory.getTableFieldDescriptor(Funcionario.class, "FuncionarioTable.xml","FuncionarioTable");
		BeanTableModel<Funcionario> tableModel = new BeanTableModel<Funcionario>(tableDescriptor);
		table = new JBeanTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.associateActionToColumn(DisableProfissaoAction.class,6);
        table.enableHeaderOrdering();
        //table.getColumnModel().getColumn(0).setPreferredWidth(150);
        
		//Pipe - Ligação entra a tabela e o form
		FormTablePipe<Funcionario> pipe = new FormTablePipe<Funcionario>(panel, tableModel);
		
	    // Botões
		MessageAction mensagemAction = new MessageAction("A ação foi executada!");
		ValidationAction validationAction = new ValidationAction(panel);
		
		
		JButton botaoInsere = new JActButton("Insere", new PutIntoTable<Funcionario>(pipe,Funcionario.class));
		table.addDoubleClickAction(new GetFromTable<Funcionario>(table, pipe));
		JActButton botaoThread = new JActButton("Thread", new ThreadedAction(){
			public void execute(){try{Thread.sleep(5000);}catch(Exception e){} System.out.println("OK");}
		});
		JActButton botaoExibeSelecionado = new JActButton("Selecionado", new ApplicationAction(){
			public void execute(){JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),table.getSelectedRow());}
		});
		JActButton botaoSemThread = new JActButton("Validação", ActionChainFactory.createChainActions(new MessageAction("Antes"),validationAction,new MessageAction("Depois")));
		JActButton objeto = new JActButton("Dados do Objeto", new ApplicationAction(){
			public void execute(){
				Funcionario func = new Funcionario();
				panel.populateBean(func);
				System.out.println(func.getProfissao().getId()+"-"+func.getProfissao().getNome());
			}
		});
		
		JActButton cleanButton = new JActButton("Clean Combos", new ApplicationAction(){
			public void execute(){
				table.resetComboValues("profissao");
			}
		});

		botaoThread.getApplicationAction().setChainedAction(mensagemAction);
		
		//Cria panel de botões
		JPanel panelButton = new JPanel();
		panelButton.add(botaoInsere);
		panelButton.add(botaoThread);
		panelButton.add(botaoSemThread);
		panelButton.add(objeto);
		panelButton.add(botaoExibeSelecionado);
		panelButton.add(cleanButton);
		
		//Criando botão adicional
		JActButton decoratorButton = new JActButton("...",new ReloadListAction(panel,"profissao"));
		decoratorButton.setPreferredSize(new Dimension(22,22));
		panel.decorateField("profissao",decoratorButton);
		
		//Associando ação ao componente
		panel.associateAction("participaDoSindicato",new CheckBoxEnableAction(panel,"participaDoSindicato","CPF","profissao"));
	
		// Organizando o Frame
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(panelButton, BorderLayout.SOUTH);

		frame.setVisible(true);
		
		Funcionario func = new Funcionario();
		func.setNome("Eduardo");
		func.setProfissao(new Profissao(2,"Computeiro Sinistro"));
		func.setDepartamentos(null);
		//func.setDepartamentos(new Departamento[]{new Departamento(3,"Marketing"),new Departamento(4,"Outro")});
		panel.setBean(func);
		
		System.out.println("Rodou em "+(System.currentTimeMillis()-time)+" ms");
		
	}
	
	public static void main(String[] args){
		
		BackgroundLoadthread.loadDescriptors();
		
		JFrame frame = new JFrame();
		frame.setSize(100, 100);
		frame.setLocation(50, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton button = new JButton("Abrir");
		button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				TestXMLGeneration teste = new TestXMLGeneration();
				teste.executa();				
			}
		});
		
		frame.add(button);
		frame.setVisible(true);
		
		
	}

}
