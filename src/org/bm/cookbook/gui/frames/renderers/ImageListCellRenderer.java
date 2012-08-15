package org.bm.cookbook.gui.frames.renderers;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import org.bm.cookbook.db.model.Image;

@SuppressWarnings("serial")
public class ImageListCellRenderer extends DefaultListCellRenderer {
	private int maxHeight = 36;
	public static Map<Image, ImageIcon> cache = new HashMap<>();

	public ImageListCellRenderer(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (value instanceof Image) {
			Image image = (Image) value;

			ImageIcon cacheIcon = cache.get(image);
			if (cacheIcon != null) {
				c.setIcon(cacheIcon);
				return c;
			}

			BufferedImage bi = image.getBufferedImage();
			int imageWidth = bi.getWidth();
			int imageHeight = bi.getHeight();

			double scale = (double) maxHeight / imageHeight;
			int newWidth = (int) (scale * imageWidth);

			BufferedImage i = new BufferedImage(newWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);

			Graphics g = i.getGraphics();
			g.drawImage(bi, 0, 0, newWidth, maxHeight, null);
			
			ImageIcon ii = new ImageIcon(i);
			cache.put(image, ii);
			c.setIcon(ii);
		}

		return c;
	}
}