package org.bm.cookbook.gui.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.Transient;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private Image image;
	private int iWidth, iHeight;
	private Color bg;

	public ImagePanel(Image image) {
		this.image = image;
		bg = this.getBackground();
		repaint();
	}

	public ImagePanel() {
		this(null);
		iWidth = 150;
		iHeight = 150;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		if (image == null) {
			iWidth = 150;
			iHeight = 200;
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(bg);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		if (image == null) {
			g.drawRect(1, 1, iWidth - 2, iHeight - 2);
			g.drawLine(1, 1, iWidth - 2, iHeight - 2);
			g.drawLine(1, iHeight - 2, iWidth - 2, 1);
		} else {
			g.drawImage(image, 1, 1, iWidth - 1, iHeight - 1, this);
		}
		// g.drawRect(1, 1, iWidth-1, iHeight-1);
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(150, 150);
	}

}
