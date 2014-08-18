package org.swingBean.visualTest;

public class Departamento {   
	
	private int id;
	private String nome;
	
	public Departamento(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String toString() {
		return nome;
	}
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		Departamento dep = (Departamento)obj;
		if(dep.getNome().equals(nome) && dep.getId() == id)
			return true;
		return false;
	}	

}
