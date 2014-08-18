package org.swingBean.gui;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.AbstractDocument;

import com.jgoodies.validation.formatter.EmptyNumberFormatter;

public class EmptyNumberFormatterNoLetter extends EmptyNumberFormatter {

	
	
	public EmptyNumberFormatterNoLetter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmptyNumberFormatterNoLetter(Number emptyValue) {
		super(emptyValue);
		// TODO Auto-generated constructor stub
	}

	public EmptyNumberFormatterNoLetter(NumberFormat format, Number emptyValue) {
		super(format, emptyValue);
		// TODO Auto-generated constructor stub
	}

	public EmptyNumberFormatterNoLetter(NumberFormat format) {
		super(format);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void install(JFormattedTextField ftf) {
		super.install(ftf);
		((AbstractDocument)ftf.getDocument()).setDocumentFilter(new NoCharacterFilter());
	}
	
}
