package org.bm.cookbook.db.model;

import java.io.IOException;
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

public class Ingredient extends DBObject {

	private String name;
	private NamedImage image;

	public Ingredient(IOID oid, String name, NamedImage image) {
		super(oid);
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public NamedImage getImage() {
		return image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImage(NamedImage image) {
		this.image = image;
	}

	public static Ingredient findByOID(IOID oid) throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		PreparedStatement pStmt = c
				.prepareStatement("SELECT name, image_db_id FROM ingredient WHERE ingredient_db_id = ? ");
		ResultSet rs = pStmt.executeQuery();

		Ingredient i = null;
		if (rs.next()) {
			String name = rs.getString("name");
			IOID imageOID = new OID(rs.getLong("image_db_id"));

			i = new Ingredient(oid, name, NamedImage.findByOID(imageOID));

		} else {
			// no row returned
		}

		return i;
	}

	public static Ingredient findByName(String name) throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		PreparedStatement pStmt = c
				.prepareStatement("SELECT ingredient_db_id, image_db_id FROM ingredient WHERE name = ? ");
		ResultSet rs = pStmt.executeQuery();

		Ingredient i = null;
		if (rs.next()) {
			IOID oid = new OID(rs.getLong("ingredient_db_id"));
			IOID imageOID = new OID(rs.getLong("image_db_id"));

			i = new Ingredient(oid, name, NamedImage.findByOID(imageOID));

		} else {
			// no row returned
		}

		return i;
	}

	public static boolean insert(Ingredient ingredient) throws SQLException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		PreparedStatement pStmt = c
				.prepareStatement("INSERT INTO ingredient(ingredient_db_id, name, image_db_id) VALUE(?, ?, ?) ");
		pStmt.setLong(1, ingredient.getOID().getOID());
		pStmt.setString(2, ingredient.getName());
		pStmt.setLong(3, ingredient.getImage().getOID().getOID());

		boolean result = pStmt.execute();

		pStmt.close();

		return result;
	}

	
	public static Collection<Ingredient> findAll() throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		Statement stmt = c.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT ingredient_db_id, name, image_db_id FROM ingredient");

		Collection<Ingredient> ingredients = new ArrayList<Ingredient>();

		while (rs.next()) {
			IOID oid = new OID(rs.getLong("ingredient_db_id"));
			String name = rs.getString("name");
			IOID imageoid = new OID(rs.getLong("image_db_id"));

			Ingredient u = new Ingredient(oid, name, NamedImage.findByOID(imageoid));
			ingredients.add(u);
		}

		return ingredients;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
