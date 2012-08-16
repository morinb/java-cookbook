package org.bm.cookbook.gui.utils;

import org.bm.cookbook.utils.CBUtils;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		handle(e);
	}

	private void handle(Throwable e) {
		try {
			CBUtils.handleException(this.getClass().getName(), e);
			// insert your e-mail code here
		} catch (Throwable t) {
			// don't let the exception get thrown out, will cause infinite
			// looping!
		}
	}

	public static void registerExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
		System.setProperty("sun.awt.exception.handler", ExceptionHandler.class.getName());
	}
}
