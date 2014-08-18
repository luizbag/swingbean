package org.swingBean.example.comboloading;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class EmpresasProvider {
	
	private static EmpresasProvider instance = new EmpresasProvider();
	
	public static EmpresasProvider getInstance(){
		return instance;
	}
	
	private List<Empresa> empresas = new ArrayList<Empresa>();
	
	public EmpresasProvider(){
		empresas.add(new Empresa("Criadores Caninos Ltda","CRIADOR"));
		empresas.add(new Empresa("Rações Bom pra Cachorro","FORNECEDOR"));
		empresas.add(new Empresa("Melhores Amigos do Cão","CRIADOR"));
		empresas.add(new Empresa("Maternidade Canina","CRIADOR"));
		empresas.add(new Empresa("Veterinarios Silva","FORNECEDOR"));
	}
	
	public ComboBoxModel getEmpresas(String tipo){
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(Empresa empresa : empresas){
			if(empresa.getTipo().equals("CRIADOR"))
				model.addElement(empresa);
		}
		return model;
	}
	

}
