package org.swingBean.actions;

public abstract class ThreadedAction extends ApplicationAction implements Runnable {

	public void run() {
		this.execute();
		if(chainedAction != null)
			chainedAction.executeActionChain();
	}
	
	public void executeActionChain(){
		Thread actionThread = new Thread(this);
		actionThread.start();
	}

}
