package org.swingBean.visualTest.simpleTree;

import java.util.HashSet;
import java.util.Set;

public class Produto {
	
	private String nome;
	private Set produtos = new HashSet();
	
	public Produto(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Set getProdutos() {
		return produtos;
	}
	public void addProduto(Produto produto) {
		produtos.add(produto);
	}
	public String toString() {
		return nome;
	}

}
