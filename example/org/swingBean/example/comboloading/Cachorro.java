package org.swingBean.example.comboloading;

import java.util.Date;

public class Cachorro {

	private String nome;
	private String raca;
	private int numeroCanil;
	private Date dataDeNascimento;
	private boolean temPedigree;
	private String origem;
	private Empresa criador;
	private String cor;
	
	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}
	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getNumeroCanil() {
		return numeroCanil;
	}
	public void setNumeroCanil(int numeroCanil) {
		this.numeroCanil = numeroCanil;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public boolean isTemPedigree() {
		return temPedigree;
	}
	public void setTemPedigree(boolean temPedigree) {
		this.temPedigree = temPedigree;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public Empresa getCriador() {
		return criador;
	}
	public void setCriador(Empresa criador) {
		this.criador = criador;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	
}
