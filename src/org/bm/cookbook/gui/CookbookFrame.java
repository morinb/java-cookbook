package org.bm.cookbook.gui;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;


@SuppressWarnings("serial")
public class CookbookFrame extends JFrame {
	public CookbookFrame() {
		super(Messages.getString("CookbookFrame.title")); //$NON-NLS-1$
		constructGUI();
	}
	
	private void constructGUI() {
		JDesktopPane desktop = new JDesktopPane();
		this.setContentPane(desktop);
		
		JMenuBar menuBar = new JMenuBar();
		createMenuBar(menuBar);
		this.setJMenuBar(menuBar);
		
	}

	private void createMenuBar(JMenuBar menuBar) {
		JMenu menuFile = new JMenu(Messages.getString("CookbookFrame.menuFile")); //$NON-NLS-1$
		menuBar.add(menuFile);
		
		JMenu menuHelp = new JMenu(Messages.getString("CookbookFrame.menuHelp")); //$NON-NLS-1$
		menuBar.add(menuHelp);
	}
}
