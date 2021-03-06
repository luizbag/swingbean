package org.swingBean.visualTest;

public class Profissao {

	private int id;
	private String nome;
	
	public Profissao(int id, String nome) {
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
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		return ((Profissao)obj).getId() == getId();
	}
	public String toString() {
		return getNome();
	}
}
