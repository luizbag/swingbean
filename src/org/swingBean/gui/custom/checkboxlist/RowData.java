package org.swingBean.gui.custom.checkboxlist;

public class RowData {
	
	protected Object data;
	protected boolean m_selected;

	public RowData(Object data) {
		this.data = data;
		m_selected = false;
	}

	public Object getData() {
		return data;
	}

	public void setSelected(boolean selected) {
		m_selected = selected;
	}

	public void invertSelected() {
		m_selected = !m_selected;
	}

	public boolean isSelected() {
		return m_selected;
	}

	public String toString() {
		return data.toString();
	}
}