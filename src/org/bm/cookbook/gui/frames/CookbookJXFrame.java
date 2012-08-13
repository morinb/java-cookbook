package org.bm.cookbook.gui.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.persistence.Persistence;
import javax.swing.Box;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.bm.cookbook.gui.DesktopManager;
import org.bm.cookbook.gui.Messages;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXStatusBar;

@SuppressWarnings("serial")
public class CookbookJXFrame extends JXFrame {
	private final JDesktopPane desktop;
	private JMenu menuWindow;

	private static JLabel statusLabel;

	private static Timer clearStatusTimer = new Timer(5000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText(""); //$NON-NLS-1$
		}
	});

	public CookbookJXFrame() {
		super(Messages.getString("CookbookFrame.title")); //$NON-NLS-1$
		Persistence.createEntityManagerFactory(null);
		DesktopManager.get().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateMenuWindow();
				updateStatusBar();
			}
		});
		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		createGUI();
	}

	private void createGUI() {
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		contentPane.add(desktop, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		createMenuBar(menuBar);
		this.setJMenuBar(menuBar);

		JXStatusBar statusBar = new JXStatusBar();

		statusLabel = new JLabel();
		statusBar.add(statusLabel);

		contentPane.add(statusBar, BorderLayout.SOUTH);
		updateStatus(Messages.getString("CookbookJXFrame.statusApplicationStarted")); //$NON-NLS-1$
	}

	private void createMenuBar(JMenuBar menuBar) {
		{
			JMenu menuFile = new JMenu(Messages.getString("CookbookFrame.menuFile")); //$NON-NLS-1$
			menuBar.add(menuFile);

			JMenuItem itemNew = new JMenuItem(Messages.getString("CookbookFrame.itemNew")); //$NON-NLS-1$
			JMenuItem itemOpen = new JMenuItem(Messages.getString("CookbookFrame.itemOpen")); //$NON-NLS-1$
			JMenuItem itemExit = new JMenuItem(Messages.getString("CookbookFrame.itemExit")); //$NON-NLS-1$
			itemExit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			menuFile.add(itemNew);
			menuFile.add(itemOpen);
			menuFile.addSeparator();
			menuFile.add(itemExit);
		}
		{
			JMenu menuConfigure = new JMenu(Messages.getString("CookbookFrame.menuConfigure")); //$NON-NLS-1$

			menuBar.add(menuConfigure);
			JMenuItem itemCookbook = new JMenuItem(Messages.getString("CookbookFrame.itemCookbook")); //$NON-NLS-1$
			itemCookbook.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JInternalFrame f = DesktopManager.get().getWindow(Messages.getString("CookbookFrame.itemCookbook")); //$NON-NLS-1$
					if (null == f) {
						f = new CookbookFrame();
						desktop.add(f);
						f.pack();
						f.setVisible(true);
					} else {
						try {
							f.setSelected(true);
						} catch (PropertyVetoException e1) {}
					}
				}
			});
			JMenuItem itemRecipe = new JMenuItem(Messages.getString("CookbookFrame.itemRecipe")); //$NON-NLS-1$
			itemRecipe.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JInternalFrame f = DesktopManager.get().getWindow(Messages.getString("CookbookFrame.itemRecipe")); //$NON-NLS-1$
					if (null == f) {
						f = new RecipeFrame();
						desktop.add(f);
						f.pack();
						f.setVisible(true);
					} else {
						try {
							f.setSelected(true);
						} catch (PropertyVetoException e1) {}
					}
				}
			});
			JMenuItem itemIngredient = new JMenuItem(Messages.getString("CookbookFrame.itemIngredient")); //$NON-NLS-1$
			itemIngredient.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JInternalFrame f = DesktopManager.get().getWindow(
							Messages.getString("CookbookFrame.itemIngredient")); //$NON-NLS-1$
					if (null == f) {
						f = new IngredientFrame();
						desktop.add(f);
						f.pack();
						f.setVisible(true);
					} else {
						try {
							f.setSelected(true);
						} catch (PropertyVetoException e1) {}
					}
				}
			});
			JMenuItem itemUnit = new JMenuItem(Messages.getString("CookbookFrame.itemUnit")); //$NON-NLS-1$
			itemUnit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JInternalFrame f = DesktopManager.get().getWindow(Messages.getString("CookbookFrame.itemUnit")); //$NON-NLS-1$
					if (null == f) {
						f = new UnitFrame();
						desktop.add(f);
						f.pack();
						f.setVisible(true);
					} else {
						try {
							f.setSelected(true);
						} catch (PropertyVetoException e1) {}
					}
				}
			});
			JMenuItem itemImage = new JMenuItem(Messages.getString("CookbookFrame.itemImage")); //$NON-NLS-1$
			itemImage.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JInternalFrame f = DesktopManager.get().getWindow(Messages.getString("CookbookFrame.itemImage")); //$NON-NLS-1$
					if (null == f) {
						f = new ImageFrame();
						desktop.add(f);
						f.pack();
						f.setVisible(true);
					} else {
						try {
							f.setSelected(true);
						} catch (PropertyVetoException e1) {}
					}
				}
			});

			menuConfigure.add(itemCookbook);
			menuConfigure.add(itemRecipe);
			menuConfigure.add(itemIngredient);
			menuConfigure.add(itemUnit);
			menuConfigure.add(itemImage);
		}
		{
			menuWindow = new JMenu(Messages.getString("CookbookFrame.menuWindow")); //$NON-NLS-1$
			updateMenuWindow();
			menuBar.add(menuWindow);
		}

		menuBar.add(Box.createHorizontalGlue());
		{
			JMenu menuHelp = new JMenu(Messages.getString("CookbookFrame.menuHelp")); //$NON-NLS-1$

			JMenu menuLaF = new JMenu(Messages.getString("CookbookJXFrame.menuLookAndFeel")); //$NON-NLS-1$
			createLaFMenus(menuLaF, UIManager.getInstalledLookAndFeels());
			menuHelp.add(menuLaF);

			menuBar.add(menuHelp);
		}
	}

	private void createLaFMenus(JMenu menuLaf, LookAndFeelInfo[] installedLookAndFeels) {
		
		for (final LookAndFeelInfo lookAndFeelInfo : installedLookAndFeels) {
			JMenuItem laf = new JMenuItem(lookAndFeelInfo.getName());
			laf.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String lafClassName = lookAndFeelInfo.getClassName();
					try {
						UIManager.setLookAndFeel(lafClassName);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						JXErrorPane.showDialog(e1);
					}
					SwingUtilities.updateComponentTreeUI(CookbookJXFrame.this);
				}
			});

			menuLaf.add(laf);
		}
	}

	private void updateMenuWindow() {
		JMenuItem itemCascade = new JMenuItem(Messages.getString("CookbookJXFrame.itemCascade")); //$NON-NLS-1$
		itemCascade.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int step = 15;
				int x = step;
				int y = step;

				double dw = desktop.getSize().getWidth();
				double dh = desktop.getSize().getHeight();

				for (final JInternalFrame jif : DesktopManager.get().getAllWindows()) {
					double w = jif.getSize().getWidth();
					double h = jif.getSize().getHeight();

					if (x + w > dw) {
						x = step;
					}

					if (y + h > dh) {
						y = step;
					}

					jif.setLocation(x, y);

					x += step;
					y += step;
				}
			}
		});

		JMenuItem itemReduceAll = new JMenuItem(Messages.getString("CookbookJXFrame.itemReduceAll")); //$NON-NLS-1$
		itemReduceAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (final JInternalFrame jif : DesktopManager.get().getAllWindows()) {
					try {
						jif.setIcon(true);
					} catch (PropertyVetoException e1) {}
				}
			}
		});

		JMenuItem itemShowAll = new JMenuItem(Messages.getString("CookbookJXFrame.itemShowAll")); //$NON-NLS-1$
		itemShowAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (final JInternalFrame jif : DesktopManager.get().getAllWindows()) {
					try {
						jif.setIcon(false);
					} catch (PropertyVetoException e1) {}
				}
			}
		});

		menuWindow.removeAll();
		menuWindow.add(itemCascade);
		menuWindow.add(itemReduceAll);
		menuWindow.add(itemShowAll);
		menuWindow.addSeparator();

		int index = 1;
		for (final JInternalFrame jif : DesktopManager.get().getAllWindows()) {
			String item = index++ + ": " + jif.getTitle(); //$NON-NLS-1$
			JMenuItem menuItem = new JMenuItem(item);
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						jif.setSelected(true);
						if (jif.isIcon()) {
							jif.setIcon(false);
						}
					} catch (PropertyVetoException e1) {}
				}
			});
			menuWindow.add(menuItem);
		}

	}

	private void updateStatusBar() {

	}

	public static void updateStatus(String statusText) {
		statusLabel.setText(statusText);
		clearStatusTimer.restart();
	}
}
