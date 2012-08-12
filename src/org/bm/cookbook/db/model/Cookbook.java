package org.bm.cookbook.db.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bm.cookbook.db.DB;
import org.bm.cookbook.db.DBFactory;
import org.bm.cookbook.db.DBObject;
import org.bm.cookbook.db.IOID;
import org.bm.cookbook.db.impl.OID;

public class Cookbook extends DBObject {

	private String name;
	private NamedImage image;
	private Set<Ingredient> ingredients;

	public Cookbook(IOID oid, String name, NamedImage image) {
		super(oid);
		this.name = name;
		this.image = image;
		ingredients = new HashSet<Ingredient>();
	}

	public NamedImage getImage() {
		return image;
	}

	public void setImage(NamedImage image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void add(Ingredient ingredient) {
		ingredients.add(ingredient);
	}

	public boolean remove(Ingredient ingredient) {
		return ingredients.remove(ingredient);
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public static Cookbook findByOID(IOID oid) throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		PreparedStatement pStmt = c
				.prepareStatement("SELECT name, image_db_id FROM cookbook WHERE cookbook_db_id = ? ");

		pStmt.setLong(1, oid.getOID());
		ResultSet rs = pStmt.executeQuery();

		Cookbook cb = null;

		if (rs.next()) {
			String name = rs.getString("name");
			IOID imageOid = new OID(rs.getLong("image_db_id"));

			cb = new Cookbook(oid, name, NamedImage.findByOID(imageOid));
		} else {
			// no row returned
		}

		pStmt.close();

		return cb;
	}

	public static Cookbook findByName(String name) throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		PreparedStatement pStmt = c
				.prepareStatement("SELECT c.cookbook_db_id AS oid, c.image_db_id FROM cookbook c WHERE name = ? ");
		pStmt.setString(1, name);

		ResultSet rs = pStmt.executeQuery();

		Cookbook cb = null;
		if (rs.next()) {
			IOID oid = new OID(rs.getLong("oid"));

			IOID imageOid = new OID(rs.getLong("image_db_id"));

			cb = new Cookbook(oid, name, NamedImage.findByOID(imageOid));

		} else {
			// no row returned.

		}

		pStmt.close();
		c.close();

		return cb;
	}

	public static boolean insert(Cookbook cb) throws SQLException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		PreparedStatement pStmt = c
				.prepareStatement("INSERT INTO cookbook(cookbook_db_id, name, image_db_id) VALUE(cookbook_db_id.nextval, ?, ?) ");
		pStmt.setString(1, cb.getName());
		pStmt.setLong(2, cb.getImage().getOID().getOID());

		boolean result = pStmt.execute();

		pStmt.close();

		return result;
	}

	public static Collection<Cookbook> findAll() throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		Statement stmt = c.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT cookbook_db_id, name, image_db_id FROM cookbook");

		Collection<Cookbook> cookbooks = new ArrayList<Cookbook>();

		while (rs.next()) {
			IOID oid = new OID(rs.getLong("cookbook_db_id"));
			String name = rs.getString("name");
			IOID imageoid = new OID(rs.getLong("image_db_id"));

			Cookbook u = new Cookbook(oid, name, NamedImage.findByOID(imageoid));
			cookbooks.add(u);
		}

		return cookbooks;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
