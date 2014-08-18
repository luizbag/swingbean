package org.swingBean.actions;

public abstract class ApplicationAction {
	
	public ApplicationAction chainedAction;
	
	public abstract void execute();
	
	public void executeActionChain(){
		this.execute();
		if(chainedAction != null)
			chainedAction.executeActionChain();
	}

	public ApplicationAction getChainedAction() {
		return chainedAction;
	}

	public void setChainedAction(ApplicationAction chainedAction) {
		this.chainedAction = chainedAction;
	}

}
