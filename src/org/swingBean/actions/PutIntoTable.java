package org.swingBean.actions;

import org.swingBean.exception.ComponentExecutionException;
import org.swingBean.gui.FormTablePipe;

public class PutIntoTable<Bean> extends ApplicationAction {
	
	private FormTablePipe<Bean> pipe;
	private Class<Bean> beanClass;

	public PutIntoTable(FormTablePipe<Bean> pipe, Class<Bean> instance) {
		this.pipe = pipe;
		this.beanClass = instance;
	}

	public void execute() {
		try {
			pipe.addTableFromForm(beanClass.newInstance());
		} catch (Exception e) {
			throw new ComponentExecutionException("Can't create bean "+beanClass.getName(),e);
		}
	}

}
