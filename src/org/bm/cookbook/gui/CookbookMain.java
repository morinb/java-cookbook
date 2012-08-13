package org.bm.cookbook.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

public class CookbookMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				try {
					UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
				}
				
				
				CookbookFrame frame = new CookbookFrame();
				
				frame.setSize(800, 600);
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
