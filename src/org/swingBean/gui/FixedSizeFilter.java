package org.swingBean.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FixedSizeFilter extends DocumentFilter {   
	
	private int maxSize;
	private boolean toUppercase;

	public FixedSizeFilter(int limit, boolean toUppercase) {
		maxSize = limit;
		this.toUppercase = toUppercase;
	}

	public void insertString(DocumentFilter.FilterBypass fb, int offset,
			String str, AttributeSet attr) throws BadLocationException {
		replace(fb, offset, 0, str, attr);
	}

	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
			String str, AttributeSet attrs) throws BadLocationException {
		int newLength = fb.getDocument().getLength() - length + str.length();
		if(toUppercase)
			str = str.toUpperCase();
		if (newLength <= maxSize) {
			fb.replace(offset, length, str, attrs);
		}
	}
}
