package org.bm.cookbook.db.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Blob;
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
import org.bm.cookbook.utils.CBUtils;

public class NamedImage extends DBObject {
	public static final int INDEX_IMAGE_DB_ID = 1;
	public static final int INDEX_NAME = 2;
	public static final int INDEX_IMAGE = 3;

	private final String name;
	private final BufferedImage image;
	private final String type;

	public NamedImage(IOID ioid, String name, BufferedImage image, String type) {
		super(ioid);
		this.name = name;
		this.image = image;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public String getType() {
		return type;
	}

	public static NamedImage findByOID(IOID oid) throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		PreparedStatement pStmt = c.prepareStatement("SELECT name, image, type FROM image where image_db_id=?");
		pStmt.setLong(INDEX_IMAGE_DB_ID, oid.getOID());
		ResultSet rs = pStmt.executeQuery();

		NamedImage ni = null;
		if (rs.next()) {
			String name = rs.getString("name");
			Blob imageBlob = rs.getBlob("image");
			String type = rs.getString("type");

			ni = new NamedImage(oid, name, CBUtils.from(imageBlob), type);
		} else {
			// no row returned
		}

		rs.close();
		pStmt.close();
		c.close();

		return ni;
	}

	public static boolean insert(NamedImage ni) throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		PreparedStatement pStmt = c.prepareStatement("INSERT INTO image(image_db_id, name, image) VALUE(image_db_id.nextval, ?, ?) ");
		pStmt.setString(INDEX_NAME, ni.getName());
		pStmt.setBlob(INDEX_IMAGE, CBUtils.from(ni.getImage(), ni.getType()));

		boolean result = pStmt.execute();

		pStmt.close();

		return result;
	}
	
	public static Collection<NamedImage> findAll() throws SQLException, IOException {
		DB db = DBFactory.get().getDB();

		Connection c = db.getConnection();

		Statement stmt = c.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT image_db_id, name, image, type FROM image");

		Collection<NamedImage> images = new ArrayList<NamedImage>();

		while (rs.next()) {
			IOID oid = new OID(rs.getLong("image_db_id"));
			String name = rs.getString("name");
			Blob imageBlob = rs.getBlob("image");
			String type = rs.getString("type");

			NamedImage u = new NamedImage(oid, name, CBUtils.from(imageBlob), type);
			images.add(u);
		}

		return images;
	}

}
