package org.swingBean.actions;

import javax.swing.JTable;

import org.swingBean.gui.FormTablePipe;

public class GetFromTable<Bean> extends ApplicationAction {
	
	private JTable table;
	private FormTablePipe<Bean> pipe;

	public GetFromTable(JTable table, FormTablePipe<Bean> pipe) {
		this.table = table;
		this.pipe = pipe;
	}

	public void execute() {
		if(table.getSelectedRow() >= 0)
			pipe.addFormFromTable(table.getSelectedRow());
	}

}
