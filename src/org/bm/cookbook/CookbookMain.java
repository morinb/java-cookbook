package org.bm.cookbook;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.bm.cookbook.gui.frames.CookbookJXFrame;
import org.jdesktop.swingx.JXErrorPane;

public class CookbookMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					JXErrorPane.showDialog(e);
				}

				CookbookJXFrame frame = new CookbookJXFrame();

				frame.setSize(800, 600);
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}
		});
	}
}
