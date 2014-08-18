package org.swingBean.gui.custom.twoLists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.gui.wrappers.ArrayHandler;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TwoListsPanel extends JPanel implements ArrayHandler{
	
	private boolean m_selectionChanged = false;

	private MutableList m_leftList;

	private MutableList m_rightList;
	
	private Object[] completeData;
	
	private JButton btnToRight;
	private JButton btnToLeft;

	public TwoListsPanel(Object[] completeData, String leftTitle,
			Object[] selectedData, String rightTitle) {
		this(leftTitle,rightTitle);
		this.completeData = completeData;
		setValue(selectedData);
	}
	
	public TwoListsPanel(String leftTitle, String rightTitle) {
		
		FormLayout formlayout = new FormLayout("FILL:70DLU:GROW(0.5),FILL:5DLU:NONE,FILL:30DLU:NONE,FILL:5DLU:NONE,FILL:70DLU:GROW(0.5)","CENTER:DEFAULT:NONE,CENTER:DEFAULT:GROW(0.5),CENTER:DEFAULT:NONE,CENTER:8DLU:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:GROW(0.5)");
	    CellConstraints cc = new CellConstraints();
	    setLayout(formlayout);
				
		// Cria Lista Esquerda
		add(LookProvider.getLook().createFormLabel(leftTitle),cc.xy(1,1));
		m_leftList = new MutableList();
		JScrollPane spl = new JScrollPane(m_leftList);
		JPanel leftPanel = new JPanel(new FormLayout("FILL:DEFAULT:GROW(1.0)","FILL:DEFAULT:GROW(1.0)"));
		leftPanel.setBorder(new EmptyBorder(1,1,10,1));
		leftPanel.add(spl,cc.xy(1,1));
		add(leftPanel,cc.xywh(1,2,1,5));

		// Cria Lista Direita
		add(LookProvider.getLook().createFormLabel(rightTitle),cc.xy(5,1));
		m_rightList = new MutableList();
		JScrollPane spr = new JScrollPane(m_rightList);
		JPanel rightPanel = new JPanel(new FormLayout("FILL:DEFAULT:GROW(1.0)","FILL:DEFAULT:GROW(1.0)"));
		rightPanel.setBorder(new EmptyBorder(1,1,10,1));
		rightPanel.add(spr,cc.xy(1,1));
		add(rightPanel,cc.xywh(5,2,1,5));
		
		// Cria botões no centro
		btnToRight = new JButton(">>");
		btnToRight.setRequestFocusEnabled(false);
		btnToRight.addActionListener(new LeftToRightMover());
		add(btnToRight,cc.xy(3,3));
		
		btnToLeft = new JButton("<<");
		btnToLeft.setRequestFocusEnabled(false);
		btnToLeft.addActionListener(new RightToLeftMover());
		add(btnToLeft,cc.xy(3,5));
	}

	public void setValue(Object[] selectedData) {
		resetComponent();
		for(Object obj : selectedData)
			moveFromLeftToRight(obj);
		m_leftList.repaint();
		m_rightList.repaint();
	}

	public void setCompleteData(Object[] completeData) {
		m_leftList.setObjectArray(completeData);
		m_leftList.repaint();
		this.completeData = completeData;
	}
	
	public void resetComponent(){
		Object[] objs = ((SimpleListModel)m_rightList.getModel()).toArray();
		for(Object obj : objs)
			moveFromRightToLeft(obj);
		m_leftList.repaint();
		m_rightList.repaint();
		//m_rightList.setObjectArray(new Object[]{});
		//setCompleteData(completeData);
	}
	
	public void setCompleteData(ListModel model) {
		Object[] completeData = new Object[model.getSize()];
		for(int i=0;i<completeData.length;i++)
			completeData[i] = model.getElementAt(i);
		setCompleteData(completeData);
	}

	public boolean selectionChanged() {
		return m_selectionChanged;
	}

	public void moveFromLeftToRight(Object obj) {
		if (obj == null)
			return;
		m_leftList.removeElement(obj);
		m_rightList.addElement(obj);
	}

	public void moveFromRightToLeft(Object obj) {
		if (obj == null)
			return;
		m_rightList.removeElement(obj);
		m_leftList.addElement(obj);
	}
	
	public Object[] getValue(){
		return m_rightList.getData();
	}

	class LeftToRightMover implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			Object[] values = m_leftList.getSelectedValues();
			for (int k = 0; k < values.length; k++) {
				m_leftList.removeElement(values[k]);
				m_rightList.addElement(values[k]);
				m_selectionChanged = true;
			}
			m_leftList.repaint();
			m_rightList.repaint();
		}
	}

	class RightToLeftMover implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			Object[] values = m_rightList.getSelectedValues();
			for (int k = 0; k < values.length; k++) {
				m_rightList.removeElement(values[k]);
				m_leftList.addElement(values[k]);
				m_selectionChanged = true;
			}
			m_leftList.repaint();
			m_rightList.repaint();
		}
	}
	
	public void setEnabled(boolean enabled){
		m_leftList.setEnabled(enabled);
		m_leftList.setSelectedIndex(-1);
		m_rightList.setEnabled(enabled);
		m_leftList.setSelectedIndex(-1);
		btnToRight.setEnabled(enabled);
		btnToLeft.setEnabled(enabled);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		m_leftList.setName(name + "_leftlist");
		m_rightList.setName(name + "_rightlist");
		btnToRight.setName(name + "_toRight");
		btnToLeft.setName(name + "_toLeft");
	}
	
}
