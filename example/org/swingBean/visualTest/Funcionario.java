package org.swingBean.visualTest;

import java.awt.Color;
import java.util.Date;

public class Funcionario{
	
	private String nome;
	private boolean participaDoSindicato;
	private float remuneracao; 
	private int idade;
	private String estadoCivil;
	private Date dataDeNascimento;
	private String comentarios;
	private String sexo;
	private Departamento[] departamentos;
	private String[] projetos;
	private Profissao profissao;
	private byte[] foto;
	private Color corFavorita;
	private InformacoesFuncionario info;
	
	public InformacoesFuncionario getInfo() {
		return info;
	}
	public void setInfo(InformacoesFuncionario info) {
		this.info = info;
	}
	public Color getCorFavorita() {
		return corFavorita;
	}
	public void setCorFavorita(Color corFavorita) {
		this.corFavorita = corFavorita;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public Profissao getProfissao() {
		return profissao;
	}
	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}
	public String[] getProjetos() {
		return projetos;
	}
	public void setProjetos(String[] projetos) {
		this.projetos = projetos;
	}
	public Departamento[] getDepartamentos() {
		return departamentos;
	}
	public void setDepartamentos(Departamento[] departamentos) {
		this.departamentos = departamentos;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public boolean isParticipaDoSindicato() {
		return participaDoSindicato;
	}
	public void setParticipaDoSindicato(boolean casado) {
		this.participaDoSindicato = casado;
	}
	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}
	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getRemuneracao() {
		return remuneracao;
	}
	public void setRemuneracao(float remuneracao) {
		this.remuneracao = remuneracao;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	
	
}