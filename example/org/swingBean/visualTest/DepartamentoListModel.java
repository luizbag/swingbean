package org.swingBean.visualTest;

import javax.swing.DefaultListModel;

public class DepartamentoListModel{
	
	private static DefaultListModel cache;
	
	public static DepartamentoListModel getInstance(){
		return new DepartamentoListModel();
	}

	public DefaultListModel listDepartamentos() {
		if(cache != null)
			return cache;
		
		DefaultListModel list = new DefaultListModel();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			return list;
		}
		list.addElement(new Departamento(1,"Finanças"));
		list.addElement(new Departamento(2,"Tecnologia"));
		list.addElement(new Departamento(3,"Marketing"));
		list.addElement(new Departamento(4,"Brincadeiras"));
		list.addElement(new Departamento(5,"Bagunças"));
		list.addElement(new Departamento(6,"Outro"));
		list.addElement(new Departamento(7,"Gerencia"));
		
		cache = list;
		
		return list;
	}	

}
