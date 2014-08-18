package org.swingBean.actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionMouseAdapter extends MouseAdapter {
	
	private ApplicationAction action;

	public ActionMouseAdapter(ApplicationAction action) {
		this.action = action;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2)
			action.executeActionChain();
	}
	
	

}
