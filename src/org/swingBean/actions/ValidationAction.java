package org.swingBean.actions;

import java.util.ArrayList;
import java.util.List;

import org.swingBean.gui.ErrorDialog;
import org.swingBean.gui.JBeanPanel;

import com.jgoodies.validation.ValidationResult;

public class ValidationAction extends ApplicationAction{
	
	private List<JBeanPanel> formPanels;
	private boolean valid = true;
	private String panelName;

	public ValidationAction(JBeanPanel... panels) {
		formPanels = new ArrayList<JBeanPanel>();
		for(JBeanPanel panel : panels)
			formPanels.add(panel);
	}
	
	public ValidationAction(String panelName, JBeanPanel... panels) {
		this(panels);
		this.panelName = panelName;
	}

	public void execute() {
		ValidationResult result = new ValidationResult();
		for(JBeanPanel panel : formPanels)
			result.addAllFrom(panel.getValidationResult());
		if(result.size() > 0){
			valid = false;
			ErrorDialog dialog = new ErrorDialog(result);
			dialog.setLocationRelativeTo(null);
			if(panelName != null)
				dialog.setTitle(panelName);
			dialog.setVisible(true);
		}else{
			valid = true;
		}
	}
	
	public void executeActionChain(){
		this.execute();
		if(chainedAction != null && valid)
			chainedAction.executeActionChain();
	}

}
