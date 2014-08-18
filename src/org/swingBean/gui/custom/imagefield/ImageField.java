package org.swingBean.gui.custom.imagefield;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.swingBean.exception.ComponentExecutionException;
import org.swingBean.util.ConfigUtils;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageField extends JPanel {

	private JPEGPanel jpegPanel;

	private JFileChooser m_chooser;

	protected File m_currentDir;
	
	private JButton buttonLoad = null;
	private JButton buttonSave = null;
	private JButton buttonReset = null;

	public ImageField(int showHeight, int showWidth, int saveHeight,
			int saveWidth) {

		try {
			m_currentDir = (new File(".")).getCanonicalFile();
		} catch (IOException ex) {
			throw new RuntimeException("Problems reading current directory.",ex);
		}
		m_chooser = new JFileChooser();
		m_chooser.setFileFilter(new SimpleFilter("jpg", "JPEG Image Files"));
		setBorder(new EmptyBorder(2,2,2,2));
		FormLayout formlayout1 = new FormLayout(
				"FILL:" + (showWidth+1) + "PX:NONE", "CENTER:" + (showHeight+1)
						+ "PX:NONE,CENTER:DEFAULT:NONE");
		CellConstraints cc = new CellConstraints();
		setLayout(formlayout1);
		jpegPanel = new JPEGPanel(showHeight, showWidth, saveHeight, saveWidth);
		add(jpegPanel, cc.xy(1, 1));
		
		buttonLoad = new JButton(new ImageIcon(ConfigUtils
					.getResourceAsByteArray("cam+.png")));
		buttonSave = new JButton(new ImageIcon(ConfigUtils
					.getResourceAsByteArray("cam.png")));
		buttonReset = new JButton(new ImageIcon(ConfigUtils
					.getResourceAsByteArray("camx.png")));

		buttonLoad.setPreferredSize(new Dimension(24,24));
		buttonSave.setPreferredSize(new Dimension(24,24));
		buttonReset.setPreferredSize(new Dimension(24,24));
		buttonLoad.setToolTipText("Carrega uma imagem");
		buttonSave.setToolTipText("Salva em disco a imagem exibida");
		buttonReset.setToolTipText("Remove a imagem exibida");
		
		ActionListener load = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_chooser.setCurrentDirectory(m_currentDir);
				m_chooser.rescanCurrentDirectory();
				m_chooser.setDialogTitle("Abrir");
				int result = m_chooser.showOpenDialog(ImageField.this);
				repaint();
				if (result != JFileChooser.APPROVE_OPTION)
					return;
				m_currentDir = m_chooser.getCurrentDirectory();
				File fChoosen = m_chooser.getSelectedFile();
				try {
					openFile(new FileInputStream(fChoosen));
				} catch (FileNotFoundException ex) {
					JOptionPane.showMessageDialog(ImageField.this,
							"O arquivo não foi encontrado");
				}
			}
		};
		buttonLoad.addActionListener(load);

		ActionListener save = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jpegPanel.getBufferedImage() == null)
					return;
				m_chooser.setCurrentDirectory(m_currentDir);
				m_chooser.rescanCurrentDirectory();
				m_chooser.setDialogTitle("Salvar");
				int result = m_chooser.showSaveDialog(ImageField.this);
				repaint();
				if (result != JFileChooser.APPROVE_OPTION)
					return;
				m_currentDir = m_chooser.getCurrentDirectory();
				File fChoosen = m_chooser.getSelectedFile();
				if (fChoosen != null && fChoosen.exists()) {
					String message = "O arquivo " + fChoosen.getName()
							+ " já existe. Sobrescrever?";
					int result2 = JOptionPane.showConfirmDialog(
							ImageField.this, message, "",
							JOptionPane.YES_NO_OPTION);
					if (result2 != JOptionPane.YES_OPTION)
						return;
				}
				try {
					FileOutputStream out = new FileOutputStream(fChoosen);
					saveFile(out);
					out.close();
				} catch (FileNotFoundException ex) {
					JOptionPane.showMessageDialog(ImageField.this,
							"O arquivo não foi encontrado.");
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(ImageField.this,
							"Erro ao salvar arquivo.");
				}
			}
		};
		buttonSave.addActionListener(save);
		
		ActionListener reset = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		};
		buttonReset.addActionListener(reset);

		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButton.add(buttonReset);
		panelButton.add(buttonLoad);
		panelButton.add(buttonSave);
		add(panelButton, cc.xy(1, 2));
	}

	protected void openFile(final InputStream in) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
			final BufferedImage image = decoder.decodeAsBufferedImage();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					jpegPanel.setBufferedImage(image);
				}
			});
		} catch (Exception ex) {
			throw new ComponentExecutionException("Error opening file",ex);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				throw new ComponentExecutionException("Error closing file",e);
			}
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	protected void saveFile(final OutputStream out) {
		if (jpegPanel.getBufferedImage() == null)
			return;
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(jpegPanel.getBufferedImage());
		} catch (Exception ex) {
			throw new ComponentExecutionException("Error encoding image",ex);
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void setValue(byte[] byteArray){
		ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
		openFile(in);
	}
	
	public byte[] getValue(){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		saveFile(out);
		byte[] resposta = out.toByteArray();
		if(resposta.length == 0)
			return null;
		return out.toByteArray();
	}
	
	public void reset(){
		jpegPanel.reset();
	}
	
	public void setEnabled(boolean enabled){
		buttonLoad.setEnabled(enabled);
		buttonSave.setEnabled(enabled);
		buttonReset.setEnabled(enabled);
	}

}
