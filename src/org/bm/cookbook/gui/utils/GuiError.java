package org.bm.cookbook.gui.utils;

import java.awt.Component;

public class GuiError {
	private Component component;
	private String message;

	public GuiError(Component component, String message) {
		super();
		this.component = component;
		this.message = message;
	}

	public Component getComponent() {
		return component;
	}

	public String getMessage() {
		return message;
	}

}
