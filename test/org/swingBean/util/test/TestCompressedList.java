package org.swingBean.util.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.swingBean.util.CompressedList;   


public class TestCompressedList extends TestCase{
	
	public void testReadAndWrite() throws IOException, ClassNotFoundException {
		//Write
		FileOutputStream fileArraylist = new FileOutputStream("TesteInicial.txt");
		ObjectOutputStream dataArrayList = new ObjectOutputStream(fileArraylist);
		
		FileOutputStream fileCompressedlist = new FileOutputStream("TesteZipado.txt");
		ObjectOutputStream dataCompressedlist = new ObjectOutputStream(fileCompressedlist);
		
		List list = new ArrayList(1000);
		for(int i=0;i<1000;i++)
			list.add(new TestVO("valor1","valor2","valor3"));
		
		dataArrayList.writeObject(list);
		dataArrayList.flush();
		dataArrayList.close();
		
		list = CompressedList.compressList(list);
		
		dataCompressedlist.writeObject(list);
		dataCompressedlist.flush();
		dataCompressedlist.close();
		
		//Read
		FileInputStream file = new FileInputStream("TesteZipado.txt");
		ObjectInputStream data = new ObjectInputStream(file);
		
		List novaLista = (List)data.readObject();
		data.close();
		
		assertEquals(novaLista.size(),1000);
		TestVO comp = new TestVO("valor1","valor2","valor3");
		for(int i=0;i<1000;i++)
			assertEquals(list.get(i),comp);
		

	}

}

class TestVO implements Serializable{
	private String nomeTeste;
	private String valorTexto;
	private String estadoCivilNovo;
	
	public TestVO(String novo, String teste, String texto) {
		estadoCivilNovo = novo;
		nomeTeste = teste;
		valorTexto = texto;
	}
	public String getEstadoCivilNovo() {
		return estadoCivilNovo;
	}
	public void setEstadoCivilNovo(String estadoCivilNovo) {
		this.estadoCivilNovo = estadoCivilNovo;
	}
	public String getNomeTeste() {
		return nomeTeste;
	}
	public void setNomeTeste(String nomeTeste) {
		this.nomeTeste = nomeTeste;
	}
	public String getValorTexto() {
		return valorTexto;
	}
	public void setValorTexto(String valorTexto) {
		this.valorTexto = valorTexto;
	}
	@Override
	public boolean equals(Object obj) {
		TestVO outro = (TestVO) obj;
		return outro.getEstadoCivilNovo().equals(estadoCivilNovo) && outro.getNomeTeste().equals(nomeTeste) && outro.getValorTexto().endsWith(valorTexto);
	}
	
	
}

