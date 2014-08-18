package org.swingBean.actions.test;

import junit.framework.TestCase;

import org.swingBean.actions.ActionChainFactory;
import org.swingBean.actions.ApplicationAction;

public class TestActionChainFactory extends TestCase {
	
	public class ActionWriter extends ApplicationAction{
		
		private StringBuffer buffer;
		private String string;

		public ActionWriter(StringBuffer buffer, String string){
			this.buffer = buffer;
			this.string = string;
		}

		public void execute() {
			buffer.append(string);			
		}
	}
	
	public void testChainCreation(){
		
		ApplicationAction action1 = new ApplicationAction(){public void execute(){}};
		ApplicationAction action2 = new ApplicationAction(){public void execute(){}};
		ApplicationAction action3 = new ApplicationAction(){public void execute(){}};
		ApplicationAction action4 = new ApplicationAction(){public void execute(){}};
		
		ApplicationAction action = ActionChainFactory.createChainActions(action1,action2,action3,action4);
		
		assertEquals("Returns action1",action1,action);
		assertEquals("action2 after action1",action1.getChainedAction(),action2);
		assertEquals("action3 after action2",action2.getChainedAction(),action3);
		assertEquals("action4 after action3",action3.getChainedAction(),action4);
	}
	
	public void testChainExecution(){
		
		StringBuffer buffer = new StringBuffer();
		
		ApplicationAction action1 = new ActionWriter(buffer,"1");
		ApplicationAction action2 = new ActionWriter(buffer,"2");
		ApplicationAction action3 = new ActionWriter(buffer,"3");
		ApplicationAction action4 = new ActionWriter(buffer,"4");
		
		ApplicationAction action = ActionChainFactory.createChainActions(action1,action2,action3,action4);
		action.executeActionChain();
		
		assertEquals("Verify action execution order",buffer.toString(),"1234");
		
	}

}
