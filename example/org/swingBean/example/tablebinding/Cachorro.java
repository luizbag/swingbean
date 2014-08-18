package org.swingBean.example.tablebinding;

import java.io.Serializable;
import java.util.Date;

public class Cachorro implements Serializable{

	private String nome;
	private String raca;
	private int numeroCanil;
	private Date dataDeNascimento;
	private boolean temPedigree;
	
	public Cachorro() {
	}
	public Cachorro(String nome, String raca, Date dataDeNascimento, boolean temPedigree) {
		this.nome = nome;
		this.raca = raca;
		this.dataDeNascimento = dataDeNascimento;
		this.temPedigree = temPedigree;
	}
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
