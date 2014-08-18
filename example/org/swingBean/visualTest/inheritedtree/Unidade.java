package org.swingBean.visualTest.inheritedtree;

public class Unidade{
   
	private String nome;
	private String superior;
	private String tipo;
	private String localidade;
	
	public Unidade(){};
	
	public Unidade(String nome, String superior, String tipo, String localidade) {
		super();
		this.nome = nome;
		this.superior = superior;
		this.tipo = tipo;
		this.localidade = localidade;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSuperior() {
		return superior;
	}
	public void setSuperior(String superior) {
		this.superior = superior;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String toString() {
		return nome;
	}
	
	

}
