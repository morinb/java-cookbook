package org.bm.cookbook.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.bm.cookbook.db.DB;

public class HSQLDBShutdownHook implements Runnable {

	@Override
	public void run() {
		try {
			DriverManager.getConnection(
			          "jdbc:hsqldb:file:"+DB.DATABASE_NAME+";shutdown=true", "SA", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
