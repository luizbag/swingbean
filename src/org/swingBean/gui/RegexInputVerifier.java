package org.swingBean.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public class RegexInputVerifier extends InputVerifier {

	private Matcher fMatcher;

	public RegexInputVerifier(Pattern aPattern) {
		fMatcher = aPattern.matcher("");
	}

	public boolean verify(JComponent aComponent) {
		JTextComponent textComponent = (JTextComponent) aComponent;
		String text = textComponent.getText();
		if(text == null || text.equals(""))
			return true;
		fMatcher.reset(text);
		if (fMatcher.matches()) {
			return true;
		}
		return false;
	}

}
