package org.swingBean.gui.wrappers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.NumberFormatter;

import org.swingBean.descriptor.BeanModel;
import org.swingBean.descriptor.look.LookProvider;
import org.swingBean.exception.ComponentCreationException;

import com.jgoodies.validation.formatter.EmptyNumberFormatter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class WrapperCellEditor extends AbstractCellEditor implements TableCellEditor,TableCellRenderer{
	
	private ComponentWrapper componentWrapper;
	private BeanModel model;
	private BufferedImage cachedImage;
	private ActionListener listener;
	private KeyListener keyListener;
	private boolean hasError; 
	
	public ComponentWrapper getComponentWrapper() {
		return componentWrapper;
	}

	public WrapperCellEditor(BeanModel model,KeyListener keyListener){
		this.model = model;
		this.keyListener = keyListener;
	}
	
	public WrapperCellEditor(BeanModel model){
		this.model = model;
	}
	
	public void setListener(ActionListener listener) {
		this.listener = listener;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		
		if(componentWrapper == null){
			createWrapper(row, col);
			if(listener != null && componentWrapper != null){
				Class componentClass = componentWrapper.getComponent().getClass();
				try {
					Method metodo = componentClass.getMethod("addActionListener",new Class[]{ActionListener.class});
					metodo.invoke(componentWrapper.getComponent(),new Object[]{listener});
				} catch (Exception e) {
					throw new ComponentCreationException("The class "+componentClass+" does not support events.");
				}
			}
		}
		
		if(componentWrapper instanceof DependentComboWrapper){
			int dependentCol = model.getPropertyCol(model.getDescriptor().getDependentProperty(model.getColProperty(col)));
			((DependentComboWrapper)componentWrapper).valueChanged(model.getValueAt(row,dependentCol));
		}
				
		if(value==null)
			componentWrapper.cleanValue();
		else
			componentWrapper.setValue(value);
		
		if(keyListener != null)
			componentWrapper.getComponent().addKeyListener(keyListener);
		
		return componentWrapper.getComponent();
	}

	private void createWrapper(int row, int col) {
		WrapperFactory factory = new WrapperFactory();
		componentWrapper = factory.createWrapper(model.getColProperty(col),model.getDescriptor());
		
		if(componentWrapper instanceof BooleanWrapper){
			((JCheckBox)((BooleanWrapper)componentWrapper).getComponent()).setText("");
		}
	}

	public Object getCellEditorValue() {
		return componentWrapper.getValue();
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		
		JLabel returnLabel = new JLabel();
		returnLabel.setFont(LookProvider.getLook().getFieldsFont());
		if(isSelected){
			returnLabel.setOpaque(true);
			returnLabel.setBackground(LookProvider.getLook().getSelectedLineColor());
		}else if(hasError){
			returnLabel.setOpaque(true);
			returnLabel.setBackground(LookProvider.getLook().getErrorLineColor());
		}
		
		if(value == null)
			value = "";
		if(value instanceof String){
			returnLabel.setText((String)value);
			return returnLabel;
		}
		if(value instanceof Date){
			String formatString =  model.getDescriptor().getParameter(model.getColProperty(col),"format");
			if(formatString == null)
				formatString = "dd/MM/yyyy";
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			returnLabel.setText(format.format((Date)value));
			return returnLabel;
		}
		if(value instanceof Integer || value instanceof Long){
			try {
				NumberFormatter formater = new NumberFormatter(NumberFormat.getIntegerInstance());
				returnLabel.setText(formater.valueToString(value));
				return returnLabel;
			} catch (ParseException e) {
				returnLabel.setText("0");
				return returnLabel;
			}
		}
		if(value instanceof Float || value instanceof Double){
			try {
				NumberFormatter formater = new EmptyNumberFormatter(NumberFormat.getInstance());
				returnLabel.setText(formater.valueToString(value));
				return returnLabel;
			} catch (ParseException e) {
				returnLabel.setText("0");
				return returnLabel;
			}
		}
		if(value instanceof Boolean){
			JCheckBox checkBox = new JCheckBox();
			checkBox.setSelected(((Boolean)value).booleanValue());
			if(isSelected){
				checkBox.setOpaque(true);
				checkBox.setBackground(LookProvider.getLook().getSelectedLineColor());
			}
			return checkBox;
		}
		if(value instanceof Color){
			returnLabel.setText(" ");
			returnLabel.setOpaque(true);
			returnLabel.setBackground((Color)value);
			return returnLabel;
		}
		if(value != null && value.getClass().isArray() && value.getClass().getComponentType().isPrimitive()){
			try {
				String[] resolution = model.getDescriptor().getParameter(model.getColProperty(col),"showResolution").split("x");
				int showHeight = Integer.parseInt(resolution[0]);
				int showWidth = Integer.parseInt(resolution[1]);
				if(cachedImage == null){
					ByteArrayInputStream in = new ByteArrayInputStream((byte[])value);
					JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
					final BufferedImage image = decoder.decodeAsBufferedImage();
					double factor = getScaleFactor(showWidth,showHeight,image);
					int imageHeight = (int) (factor * image.getHeight());
					int imageWidth = (int) (factor * image.getWidth());
					cachedImage = new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_RGB);
					Graphics2D gc = cachedImage.createGraphics();
					gc.drawImage(image,0,0,imageWidth,imageHeight,null);
					table.setRowHeight(showHeight);
				}
				return new JLabel(new ImageIcon(cachedImage));
			} catch (Exception e) {
				returnLabel.setText("");
				return returnLabel;
			}
			
		}
		returnLabel.setText(value.toString());
		return returnLabel;
	}

	private double getScaleFactor(double width, double height, BufferedImage bi){
		if (height/bi.getHeight() > width/ bi.getWidth())
			return width / bi.getWidth();
		else
			return height / bi.getHeight();
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	
}
