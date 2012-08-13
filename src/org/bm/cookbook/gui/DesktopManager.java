package org.bm.cookbook.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class DesktopManager {
	private static DesktopManager manager = new DesktopManager();

	private List<JInternalFrame> windows;

	private EventListenerList listenerList;

	private DesktopManager() {
		windows = new LinkedList<JInternalFrame>();
		listenerList = new EventListenerList();
	}

	public void addChangeListener(ChangeListener changeListener) {
		listenerList.add(ChangeListener.class, changeListener);
	}

	public void removeChangeListener(ChangeListener changeListener) {
		listenerList.remove(ChangeListener.class, changeListener);
	}

	private void fireChange(ChangeEvent e) {
		ChangeListener[] changeListeners = listenerList.getListeners(ChangeListener.class);
		for (ChangeListener changeListener : changeListeners) {
			changeListener.stateChanged(e);
		}
	}

	public static DesktopManager get() {
		return manager;
	}

	public void add(JInternalFrame f) {
		windows.add(f);
		ChangeEvent e = new ChangeEvent(this);
		fireChange(e);
	}

	public void remove(JInternalFrame f) {
		windows.remove(f);
		ChangeEvent e = new ChangeEvent(this);
		fireChange(e);
	}

	public JInternalFrame getWindow(String windowTitle) {
		for (JInternalFrame jif : windows) {
			if (windowTitle.equals(jif.getTitle())) {
				return jif;
			}
		}
		return null;
	}

	public Collection<JInternalFrame> getAllWindows() {
		return Collections.unmodifiableCollection(windows);
	}

	public int getWindowCount() {
		return windows.size();
	}
}
