package org.bm.cookbook.db.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bm.cookbook.db.DB;
import org.bm.cookbook.db.DBFactory;
import org.bm.cookbook.utils.CBUtils;
import org.bm.cookbook.utils.HSQLDBShutdownHook;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

public class DefaultDB implements DB {
	private Connection _conn = null;

	public DefaultDB() {
		addShutdownHook();
		loadDriver();
	}

	@Override
	public boolean loadDriver() {
		try {
			Class.forName(getDriver()).newInstance();
			return true;
		} catch (ClassNotFoundException cnfe) {
			System.err.println("\nUnable to load the JDBC driver " + getDriver());
			System.err.println("Please check your CLASSPATH.");
			cnfe.printStackTrace(System.err);
		} catch (InstantiationException ie) {
			System.err.println("\nUnable to instantiate the JDBC driver " + getDriver());
			ie.printStackTrace(System.err);
		} catch (IllegalAccessException iae) {
			System.err.println("\nNot allowed to access the JDBC driver " + getDriver());
			iae.printStackTrace(System.err);
		}

		return false;
	}

	@Override
	public Connection getConnection() {
		try {
			if (null == _conn || _conn.isClosed()) {
				String db = getProtocol() + DATABASE_NAME;
				_conn = DriverManager.getConnection(db, "SA", "");
				// Disable autocommit, to control manually the transactions.
				_conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			CBUtils.printSQLException(e);
		}
		return _conn;
	}

	@Override
	public void addShutdownHook() {
		
		Runtime.getRuntime().addShutdownHook(new Thread(new HSQLDBShutdownHook()));
	}

	@Override
	public void initializeDB() {
		
	}

	@Override
	public String getDriver() {
		return "org.hsqldb.jdbc.JDBCDriver";
	}

	@Override
	public String getProtocol() {
		return "jdbc:hsqldb:file:CookbookDB/";
	}
	
	@Override
	public void runScript(String filename) throws SQLException, IOException, SqlToolError {
		
		SqlFile sqlFile = new SqlFile(new File(filename));
		sqlFile.setConnection(DBFactory.get().getDB().getConnection());
		sqlFile.execute();
		
		
	}

}
