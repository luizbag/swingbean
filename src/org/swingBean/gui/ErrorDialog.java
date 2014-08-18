package org.swingBean.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationResultViewFactory;

public class ErrorDialog extends JDialog {

	public ErrorDialog(ValidationResult result) throws HeadlessException {
		super(JOptionPane.getRootFrame(), "Erros na validação",true);
		setLayout(new BorderLayout());
		DefaultValidationResultModel model = new DefaultValidationResultModel();
		model.setResult(result);
		JComponent errorPanel = ValidationResultViewFactory.createReportList(model);
		add(errorPanel,BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {dispose();}
			});
		buttonPanel.add(button);
		add(buttonPanel,BorderLayout.SOUTH);
		setSize(new Dimension(350,200));
	}

}
