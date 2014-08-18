package org.swingBean.visualTest;

public class InformacoesFuncionario {
	
	private boolean participaDoClube;
	private String CPF;
	private String login;
	private String senha;
	
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cpf) {
		CPF = cpf;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public boolean isParticipaDoClube() {
		return participaDoClube;
	}
	public void setParticipaDoClube(boolean participaDoClube) {
		this.participaDoClube = participaDoClube;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
