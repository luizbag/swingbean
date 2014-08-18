package org.swingBean.example.formtotable;

import java.util.Date;

public class Cachorro {

	private String nome;
	private String raca;
	private int numeroCanil;
	private Date dataDeNascimento;
	private boolean temPedigree;
	
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
	
}
