package org.swingBean.gui.custom.imagefield;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JPEGPanel extends JPanel {

	private BufferedImage bufferedImage;

	private int showHeight;

	private int showWidth;

	private int resolutionHeight;

	private int resolutionWidth;

	public JPEGPanel() {
		this(640, 480);
	}

	public JPEGPanel(int height, int width) {
		this(height, width, height, width);
	}

	public JPEGPanel(int showHeight, int showWidth, int resolutionHeight,
			int resolutionWidth) {
		this.showHeight = showHeight;
		this.showWidth = showWidth;
		this.resolutionHeight = resolutionHeight;
		this.resolutionWidth = resolutionWidth;
		Dimension d = new Dimension(showWidth, showHeight);
		setPreferredSize(d);

	}
	
	public void reset(){
		//bufferedImage = (BufferedImage) this.createImage((int)Math.round(resolutionWidth), (int)Math.round(resolutionHeight));
		//Graphics2D gc = bufferedImage.createGraphics();
		bufferedImage = null;
		revalidate();
		repaint();
	}

	public void setBufferedImage(BufferedImage bi) {
		if (bi == null)
			return;
		double scaleFactor = getScaleFactor(resolutionWidth, resolutionHeight,bi);
		bufferedImage = (BufferedImage) this.createImage((int)Math.round(bi.getWidth()*scaleFactor), (int)Math.round(bi.getHeight()*scaleFactor));
		Graphics2D gc = bufferedImage.createGraphics();
		AffineTransform at = new AffineTransform();
		at.scale(scaleFactor,scaleFactor);
		gc.transform(at);
		gc.drawImage(bi, null, 0, 0);

		revalidate();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double scaleFactor = 1;
		if (bufferedImage != null) {
			scaleFactor = getScaleFactor(showWidth, showHeight, bufferedImage);
			AffineTransform at = new AffineTransform();
			at.scale(scaleFactor,scaleFactor);
			((Graphics2D) g).transform(at);
			g.drawImage(bufferedImage, 0, 0, this);
		}
		AffineTransform at = new AffineTransform();
		at.scale(1/scaleFactor,1/scaleFactor);
		((Graphics2D) g).transform(at);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, showWidth-1, showHeight-1);
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public double getResolutionHeight() {
		return resolutionHeight;
	}

	public void setResolutionHeight(int resolutionHeight) {
		this.resolutionHeight = resolutionHeight;
	}

	public int getResolutionWidth() {
		return resolutionWidth;
	}

	public void setResolutionWidth(int resolutionWidth) {
		this.resolutionWidth = resolutionWidth;
	}

	public int getShowHeight() {
		return showHeight;
	}

	public void setShowHeight(int showHeight) {
		this.showHeight = showHeight;
	}

	public int getShowWidth() {
		return showWidth;
	}

	public void setShowWidth(int showWidth) {
		this.showWidth = showWidth;
	}
	
	private double getScaleFactor(double width, double height, BufferedImage bi){
		if (height/bi.getHeight() > width/ bi.getWidth())
			return width / bi.getWidth();
		else
			return height / bi.getHeight();
	}

}