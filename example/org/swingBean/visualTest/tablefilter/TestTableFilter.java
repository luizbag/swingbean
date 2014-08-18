package org.swingBean.visualTest.tablefilter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.descriptor.TableFieldDescriptor;
import org.swingBean.descriptor.XMLDescriptorFactory;
import org.swingBean.gui.JBeanTable;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TestTableFilter implements ActionListener {
	
	JButton m_jbutton1 = new JButton();
	JButton m_jbutton2 = new JButton();
	JButton m_jbutton3 = new JButton();
	JButton m_jbutton4 = new JButton();
	JComboBox m_jcombobox1 = new JComboBox(new DefaultComboBoxModel(new String[]{"nome","sobrenome"}));
	JTextField m_jtextfield1 = new JTextField();
	JComboBox m_jcombobox2 = new JComboBox(new DefaultComboBoxModel(new String[]{"Iniciado","Contendo"}));
	TableFieldDescriptor tableDescriptor = XMLDescriptorFactory.getTableFieldDescriptor(Pessoa.class, "PessoaTable.xml","PessoaTable");
	BeanTableModel<Pessoa> tableModel = new BeanTableModel<Pessoa>(tableDescriptor);
	JBeanTable table = new JBeanTable(tableModel);

	public TestTableFilter() {
		super();
	}
	
	public void init() {

		JFrame frame = new JFrame();
		frame.setSize(600, 400);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        JScrollPane scrollPane = new JScrollPane(table);
        tableModel.orderByProperty("nome");
        tableModel.setBeanList(getList());
        table.enableQuickEditing();

		JPanel jpanel1 = new JPanel();
		FormLayout formlayout1 = new FormLayout(
				"FILL:DEFAULT:GROW(1.0),FILL:DEFAULT:GROW(1.0),FILL:DEFAULT:GROW(1.0),FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE",
				"CENTER:DEFAULT:NONE");
		CellConstraints cc = new CellConstraints();
		jpanel1.setLayout(formlayout1);
		m_jbutton1.setText("Filtrar");
		jpanel1.add(m_jbutton1, cc.xy(4, 1));
		m_jbutton2.setText("Selecionar");
		jpanel1.add(m_jbutton2, cc.xy(5, 1));
		m_jbutton3.setText("Excluir");
		jpanel1.add(m_jbutton3, cc.xy(6, 1));
		m_jbutton4.setText("Filtrar Num");
		jpanel1.add(m_jbutton4, cc.xy(7, 1));
		jpanel1.add(m_jcombobox1, cc.xy(3, 1));
		jpanel1.add(m_jtextfield1, cc.xy(1, 1));
		jpanel1.add(m_jcombobox2, cc.xy(2, 1));
		
		m_jbutton1.addActionListener(this);
		m_jbutton2.addActionListener(this);
		m_jbutton3.addActionListener(this);
		m_jbutton4.addActionListener(this);

		frame.setLayout(new BorderLayout());
		frame.add(jpanel1,BorderLayout.NORTH);
		frame.add(scrollPane,BorderLayout.CENTER);
		frame.setVisible(true);

	}
	
	public List<Pessoa> getList(){
		List<Pessoa> list = new ArrayList<Pessoa>();
		list.add(new Pessoa("Eduardo", "Guerra",25));
		list.add(new Pessoa("Roberta", "Guerra",23));
		list.add(new Pessoa("Maria Eduarda", "Guerra",0));
		list.add(new Pessoa("José Pedro", null,24));
		list.add(new Pessoa("Gustavo", "Militão",25));
		list.add(new Pessoa("Rodrigo", "Cunha",26));
		return list;
	}
	
	public static void main(String[] args){
		TestTableFilter filter = new TestTableFilter();
		filter.init();
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == m_jbutton1)
			if(m_jcombobox2.getSelectedItem().equals("Iniciado"))
				tableModel.filterStartedBy(m_jcombobox1.getSelectedItem().toString(),m_jtextfield1.getText());
			else
				tableModel.filterContains(m_jcombobox1.getSelectedItem().toString(),m_jtextfield1.getText());
		
		if(event.getSource() == m_jbutton2)
			if(m_jcombobox2.getSelectedItem().equals("Iniciado")){
				int index = tableModel.getIndexStartedBy(m_jcombobox1.getSelectedItem().toString(),m_jtextfield1.getText());
				table.setRowSelectionInterval(index,index);
			}
			else{
				int index = tableModel.getIndexContains(m_jcombobox1.getSelectedItem().toString(),m_jtextfield1.getText());
				table.setRowSelectionInterval(index,index);
			}
		
		if(event.getSource() == m_jbutton3){
			table.editingStopped(null);
			tableModel.deleteBeanAt(table.getSelectedRow());
		}
		if(event.getSource() == m_jbutton4){
			tableModel.filterBetween("idade",null,25);
		}
		
	}

}
