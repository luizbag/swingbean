package org.swingBean.actions;

import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.gui.ErrorDialog;

import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;

public class TableValidationAction extends ApplicationAction{

	private BeanTableModel model;
	private boolean valid = true;
	private String panelName;
	
	public TableValidationAction(BeanTableModel model) {
		this.model = model;
	}
	
	public TableValidationAction(String panelName, BeanTableModel model) {
		this.model = model;
		this.panelName = panelName;
	}

	@Override
	public void execute() {
		ValidationResult result = new ValidationResult();
		for(int row =0; row < model.getRowCount(); row++){
			ValidationResult rowResult = model.getValidationResult(row);
			for(Object message : rowResult.getMessages()){
				ValidationMessage vm = (ValidationMessage)message;
				result.addError("Linha "+(row+1)+": "+vm.formattedText());
			}
		}
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
