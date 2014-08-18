package org.swingBean.visualTest;

import org.swingBean.actions.ColumnAction;

public class DisableProfissaoAction extends ColumnAction {

	public void execute() {
		boolean value = (Boolean) getWrapper().getComponentWrapper().getValue();
		getModel().setEnabledCell(getRow(),5,value);
		System.out.println("Executou!");
	}

}
