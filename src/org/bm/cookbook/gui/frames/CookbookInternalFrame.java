package org.bm.cookbook.gui.frames;

import java.awt.Color;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.metal.MetalBorders;

import org.bm.cookbook.gui.DesktopManager;

@SuppressWarnings("serial")
public abstract class CookbookInternalFrame extends JInternalFrame {
	protected Color ERROR_BACKGROUND_COLOR = new Color(1.0f, 0.85f, 0.85f);
	protected Color NORMAL_BACKGROUND_COLOR = null;

	public CookbookInternalFrame() {
		NORMAL_BACKGROUND_COLOR = this.getBackground();
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
	}

	protected abstract void createGui();

	protected void reload() {
		reset();
	}

	protected abstract void reset();
}
