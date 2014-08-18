package org.swingBean.actions;

public class ActionChainFactory {

	public static ApplicationAction createChainActions(ApplicationAction... actions){
		if(actions.length >= 2){
			for(int i=1;i<actions.length;i++)
				actions[i-1].setChainedAction(actions[i]);
		}
		return actions[0];
	}

}
