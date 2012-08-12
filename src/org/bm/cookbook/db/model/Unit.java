package org.bm.cookbook.db.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.bm.cookbook.db.DB;
import org.bm.cookbook.db.DBFactory;
import org.bm.cookbook.db.DBObject;
import org.bm.cookbook.db.IOID;
import org.bm.cookbook.db.impl.OID;

public class Unit extends DBObject {
	private String abbreviation;
	private String name;

	public Unit(IOID oid, String abbreviation, String name) {
		super(oid);
		this.abbreviation = abbreviation;
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Unit findByOID(IOID oid) throws SQLException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();
		PreparedStatement pStmt = c.prepareStatement("SELECT name, abbreviation FROM unit where unit_db_id = ?");
		pStmt.setLong(1, oid.getOID());

		ResultSet rs = pStmt.executeQuery();
		Unit u = null;
		if (rs.next()) {
			String name = rs.getString("name");
			String abbreviation = rs.getString("abbreviation");

			u = new Unit(oid, abbreviation, name);
		} else {
			// no row returned
		}

		return u;
	}

	public static Collection<Unit> findAll() throws SQLException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		Statement stmt = c.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT unit_db_id, name, abbreviation FROM unit");

		Collection<Unit> units = new ArrayList<Unit>();

		while (rs.next()) {
			IOID oid = new OID(rs.getLong("unit_db_id"));
			String name = rs.getString("name");
			String abbreviation = rs.getString("abbreviation");

			Unit u = new Unit(oid, abbreviation, name);
			units.add(u);
		}

		return units;
	}

	public static Unit findByName(String name) throws SQLException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();
		PreparedStatement pStmt = c
				.prepareStatement("SELECT unit_db_id, abbreviation FROM unit where name = ?");
		pStmt.setString(1, name);

		ResultSet rs = pStmt.executeQuery();
		Unit u = null;
		if (rs.next()) {
			IOID oid = new OID(rs.getLong("unit_db_id"));
			String abbreviation = rs.getString("abbreviation");

			u = new Unit(oid, abbreviation, name);
		} else {
			// no row returned
		}

		return u;
	}

	public static boolean insert(Unit unit) throws SQLException {

		DB db = DBFactory.get().getDB();
		Connection c = db.getConnection();

		PreparedStatement pStmt = c
				.prepareStatement("INSERT INTO unit(unit_db_id, abbreviation, name) VALUE (unit_db_id.nextval, ?, ?)");
		pStmt.setString(1, unit.getName());
		pStmt.setString(2, unit.getAbbreviation());

		boolean result = pStmt.execute();

		pStmt.close();

		return result;

	}
	
	@Override
	public String toString() {
		return this.name + " ("+this.abbreviation+")";
	}

}
