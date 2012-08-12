package org.bm.cookbook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.bm.cookbook.db.DB;
import org.bm.cookbook.db.DBFactory;
import org.bm.cookbook.db.model.Ingredient;
import org.bm.cookbook.db.model.NamedImage;
import org.bm.cookbook.db.model.Unit;
import org.bm.cookbook.db.scripts.Scripts;
import org.hsqldb.cmdline.SqlToolError;

public class Test {

	@org.junit.Test
	public void test() throws SQLException, SqlToolError, IOException {
		DB db = DBFactory.get().getDB();

		db.runScript(Scripts.getFile("test.sql"));
	}

	@org.junit.Test
	public void testImageBlob() throws IOException, SQLException {
		DB db = DBFactory.get().getDB();
		Connection c = db.getConnection();

		String imagePath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg";
		File imageFile = new File(imagePath);
		System.out.println(imageFile.getAbsolutePath());

		Statement stmt = c.createStatement();
		stmt.execute("CREATE TABLE BLOBY(image BLOB)");
		stmt.close();

		PreparedStatement pStmt = c.prepareStatement("INSERT INTO BLOBY VALUES (?)");
		pStmt.setBlob(1, new FileInputStream(imageFile));
		pStmt.execute();
		pStmt.close();

		stmt = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery("select image from bloby");

		while (rs.next()) {
			Blob blob = rs.getBlob("image");

			BufferedInputStream bis = new BufferedInputStream(blob.getBinaryStream());

			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("c:\\temp\\koala.jpg"));

			IOUtils.copy(bis, bos);

			bos.flush();
			bis.close();
			bos.close();

		}
		rs.close();
		stmt.close();

		stmt = c.createStatement();
		stmt.execute("DROP TABLE BLOBY");
		stmt.close();

	}

	@org.junit.Test
	public void testData() throws SqlToolError, SQLException, IOException {
		DB db = DBFactory.get().getDB();

		db.runScript(Scripts.getFile("CLEAN_ALL.SQL"));
		db.runScript(Scripts.getFile("COOKBOOK.SQL"));
		db.runScript(Scripts.getFile("SOME_DATA.SQL"));

		Collection<Unit> units = Unit.findAll();
		for (Unit unit : units) {
			System.out.println(unit);
		}
		
		Collection<NamedImage> images = NamedImage.findAll();
		for(NamedImage ni : images) {
			System.out.println(ni);
		}
		
		Collection<Ingredient> ingredients = Ingredient.findAll();
		for (Ingredient ingredient : ingredients) {
			System.out.println(ingredient);
		}

		db.runScript(Scripts.getFile("CLEAN_ALL.SQL"));

	}

}
