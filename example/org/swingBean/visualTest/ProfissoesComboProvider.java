package org.swingBean.visualTest;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class ProfissoesComboProvider {
	
	private static ProfissoesComboProvider provider;
	
	public static ProfissoesComboProvider getInstance(){
		if(provider == null)
			provider = new ProfissoesComboProvider();
		return provider;
	}

	public ComboBoxModel getProfissoes() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			return model;
		}
		model.addElement(new Profissao(1,"Pintor de Rodapé"));
		model.addElement(new Profissao(2,"Computeiro Sinistro"));
		model.addElement(new Profissao(3,"Enxedor de Vidro de Catchup"));
		model.addElement(new Profissao(4,"Tiozinho do Banheiro"));
		model.addElement(new Profissao(5,"Colocador de Olho de Gato no Asfalto"));
		model.addElement(new Profissao(6,"Colador de Vidro de Aquario"));
		model.addElement(new Profissao(7,"Contador de Contagem Regressiva d Foguete"));
		model.addElement(new Profissao(8,"Bagunceirinha Profissional"));
		System.out.println("Recuperou");
		return model;
	}

}
