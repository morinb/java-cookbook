package org.bm.cookbook.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.jdesktop.swingx.JXErrorPane;

public class HSQLDBShutdownHook implements Runnable {

	@Override
	public void run() {
		try {
			DriverManager.getConnection(
			          "jdbc:hsqldb:file:CookbookDB;shutdown=true", "SA", "");
		} catch (SQLException e) {
			JXErrorPane.showDialog(e);
		}
	}

}
