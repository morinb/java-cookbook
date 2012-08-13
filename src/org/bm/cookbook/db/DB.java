package org.bm.cookbook.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface DB {
	public static final String DATABASE_NAME = "CookbookDB";

	String getProtocol();

	String getDriver();

	boolean loadDriver();

	Connection getConnection();

	void addShutdownHook();

	void initializeDB();
	
	void runScript(String filename) throws SQLException, IOException;
}
