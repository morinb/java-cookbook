package org.bm.cookbook.gui.frames;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.metal.MetalBorders;

import org.bm.cookbook.gui.DesktopManager;
import org.bm.cookbook.gui.utils.CircleGlassPane;

@SuppressWarnings("serial")
public abstract class CookbookInternalFrame extends JInternalFrame {
	public static Color ERROR_BACKGROUND_TEXT_COLOR = new Color(1.0f, 0.85f, 0.85f);
	public static Color ERROR_BACKGROUND_BUTTON_COLOR = new Color(1.0f, 0.85f, 0.85f);
	public static Color NORMAL_BACKGROUND_TEXT_COLOR = null;
	public static Color NORMAL_BACKGROUND_BUTTON_COLOR = null;

	public CookbookInternalFrame() {
		CircleGlassPane cgp = new CircleGlassPane();
		this.setGlassPane(cgp);
		NORMAL_BACKGROUND_TEXT_COLOR = new JTextField().getBackground();
		NORMAL_BACKGROUND_BUTTON_COLOR = new JButton().getBackground();
		this.setMaximizable(true);
		this.setIconifiable(true);
		this.setResizable(true);
		this.setClosable(true);
		createGui();
		this.setBorder(new MetalBorders.InternalFrameBorder());
		DesktopManager.get().add(this);

		this.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				DesktopManager.get().remove(e.getInternalFrame());
			}
		});

		this.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				reload();
			}

		});

	}

	protected abstract void createGui();

	protected abstract void reload();

	protected abstract void reset();

	protected void addErrorCircle(Component toCircle) {
		CircleGlassPane gp = (CircleGlassPane) getGlassPane();
		gp.addComponentToCircle(toCircle);
	}

	protected void removeAllCircles() {
		CircleGlassPane gp = (CircleGlassPane) getGlassPane();
		gp.removeAllComponentsToCircle();
	}

	protected void removeCircle(Component toCircle) {
		CircleGlassPane gp = (CircleGlassPane) getGlassPane();
		gp.removeComponentToCircle(toCircle);
	}
}
