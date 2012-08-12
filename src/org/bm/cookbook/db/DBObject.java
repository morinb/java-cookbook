package org.bm.cookbook.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DBObject {
	private final IOID oid;

	public IOID getOID() {
		return oid;
	}

	public DBObject(IOID oid) {
		this.oid = oid;
	}

	public static DBObject findByOID(IOID oid) throws SQLException, IOException {
		throw new NotImplementedException();
	}

	public static boolean insert(DBObject o) throws SQLException {
		throw new NotImplementedException();
	}

	public static Collection<? extends DBObject> findAll() throws SQLException, IOException {
		throw new NotImplementedException();
	}
	
	@Override
	public String toString() {
		
		return oid.toString();
	}
}
