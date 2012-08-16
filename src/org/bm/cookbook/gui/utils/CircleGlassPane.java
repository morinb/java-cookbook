package org.bm.cookbook.gui.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CircleGlassPane extends JPanel {
	private Set<Component> componentsToCircle;

	public CircleGlassPane() {
		componentsToCircle = new java.util.HashSet<>();
		setOpaque(false);
	}

	public void addComponentToCircle(Component s) {
		componentsToCircle.add(s);
		setVisible(true);
		repaint();
		System.out.println(this.getBounds());
	}

	public void removeComponentToCircle(Component s) {
		componentsToCircle.remove(s);
		setVisible(componentsToCircle.size() == 0);
		repaint();
	}

	public void removeAllComponentsToCircle() {
		componentsToCircle.clear();
		setVisible(false);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(new Color(1.0f, 0.15f, 0.15f, 0.6f));
		Stroke k = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(k);

		for (Component comp : componentsToCircle) {
			Rectangle bounds = comp.getBounds();
			Point p = (Point) comp.getLocation().clone();

			RoundRectangle2D.Double rect = new RoundRectangle2D.Double(p.getX(), p.getY(), bounds.getWidth() - 1,
					bounds.getHeight() - 1, 10, 10);
			g2.draw(rect);
		}

	}
}
