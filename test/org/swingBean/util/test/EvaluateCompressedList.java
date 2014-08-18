package org.swingBean.util.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.swingBean.util.CompressedList;

public class EvaluateCompressedList {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteArrayListOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream dataArrayList = new ObjectOutputStream(byteArrayListOutputStream);
		
		ByteArrayOutputStream byteCompressedListOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream dataCompressedlist = new ObjectOutputStream(byteCompressedListOutputStream);
		
		List list = new ArrayList();
		list.add(new Pessoa("Eduardo Martins Guerra","Masculino",25));
		list.add(new Pessoa("Roberta Tosetti Geara Guerra","Feminino",23));
		list.add(new Pessoa("Maria Eduarda Tosetti Guerra","Feminino",0));
		list.add(new Pessoa("Fernanda Tosetti Geara","Feminino",27));
		list.add(new Pessoa("Dolores Martins Guerra","Feminino",23));
		list.add(new Pessoa("Silvia Martins Guerra","Feminino",21));
		list.add(new Pessoa("Rodrigo Tosetti Geara","Masculino",29));
		list.add(new Pessoa("José Maria Pereira Guerra","Masculino",45));
		list.add(new Pessoa("Maria da Graça Martins Guerra","Feminino",47));
		
		dataArrayList.writeObject(list);
		dataArrayList.flush();
		dataArrayList.close();
		
		int bytesArrayList = byteArrayListOutputStream.size();
		System.out.println("O ArrayList possui "+bytesArrayList+" bytes serializado");
		
		list = CompressedList.compressList(list);
		dataCompressedlist.writeObject(list);
		dataCompressedlist.flush();
		dataCompressedlist.close();
		
		int bytesCompressedList = byteCompressedListOutputStream.size();
		System.out.println("O CompressedList possui "+bytesCompressedList+" bytes serializado");
		
		int porcentagemCompressao = (bytesArrayList -bytesCompressedList)*100/bytesArrayList;
		System.out.println("Houve uma compressão de "+ porcentagemCompressao+"% em relaçao a lista original");
	}
}

class Pessoa implements Serializable{
	
	private String nome;
	private String sexo;
	private int idade;
	
	public Pessoa(String nome, String sexo, int idade) {
		this.idade = idade;
		this.nome = nome;
		this.sexo = sexo;
	}
}
