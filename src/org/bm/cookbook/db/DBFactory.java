package org.bm.cookbook.db;

import org.bm.cookbook.db.impl.DefaultDB;

public class DBFactory {
	public static final String DEFAULT_PROVIDER = "derby";
	private static String provider;
	private static final DBFactory factory = new DBFactory();

	public static void setProvider(String provider) {
		DBFactory.provider = provider;
	}

	public static String getProvider() {
		return provider;
	}

	public static DBFactory get() {
		return factory;
	}

	public DB getDB() {
		return new DefaultDB();
	}
}
