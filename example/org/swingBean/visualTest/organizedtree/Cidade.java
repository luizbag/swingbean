package org.swingBean.visualTest.organizedtree;

public class Cidade {
	
	private String pais;
	private String estado;
	private String nome;
	private int populacao;
	private double PIB;
	
	public Cidade(String pais, String estado, String nome, int populacao, double pib) {
		this.pais = pais;
		this.estado = estado;
		this.nome = nome;
		this.populacao = populacao;
		PIB = pib;
	}
	public Cidade() {
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public double getPIB() {
		return PIB;
	}
	public void setPIB(double pib) {
		PIB = pib;
	}
	public int getPopulacao() {
		return populacao;
	}
	public void setPopulacao(int populacao) {
		this.populacao = populacao;
	}
	public String toString() {
		return nome;
	}
	
	

}
