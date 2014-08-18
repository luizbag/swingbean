package org.swingBean.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class NoCharacterFilter extends DocumentFilter {

	public void insertString(DocumentFilter.FilterBypass fb, int offset,
			String text, AttributeSet attr) throws BadLocationException {
		fb.insertString(offset, removeCharacters(text), attr);
	}

	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
			String text, AttributeSet attr) throws BadLocationException {
		fb.replace(offset, length, removeCharacters(text), attr);
	}
	
	private String removeCharacters(String str){
		char[] charArray = str.toCharArray();
		String result = "";
		for(char chr : charArray)
			if(Character.isDigit(chr) || chr == '.' || chr == ',' || chr == '-')
				result += chr;
		return result;
	}

	
	
}
