package org.bm.cookbook.gui.utils;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.bm.cookbook.gui.Messages;
import org.bm.cookbook.gui.frames.CookbookInternalFrame;

@SuppressWarnings("serial")
public class GuiErrorList extends ArrayList<GuiError> {
	private Component c ;
	public GuiErrorList(Component comp) {
		this.c = comp;
	}

	public boolean showErrors() {
		if (!this.isEmpty()) {
			StringBuilder messages = new StringBuilder();
			for (GuiError ge : this) {
				messages.append(ge.getMessage()).append("\n"); //$NON-NLS-1$
				if (ge.getComponent() != null) {
					ge.getComponent().setBackground(CookbookInternalFrame.ERROR_BACKGROUND_TEXT_COLOR);
				}
			}
			String message = messages.substring(0, messages.length() - 1);

			JOptionPane.showMessageDialog(c, message,
					Messages.getString("Frame.errorWarning"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$

			return true;
		}
		return false;
	}

}
