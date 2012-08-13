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
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.bm.cookbook.db.DB;
import org.bm.cookbook.db.DBFactory;
import org.bm.cookbook.db.model.Unit;
import org.bm.cookbook.db.scripts.Scripts;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class Test {
	private static EntityManager em;
	private static EntityTransaction transaction = null;

	@org.junit.Test
	public void test() throws SQLException, IOException {
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
	public void testData() throws SQLException, IOException {
		
		Unit gramme = new Unit();
		gramme.setName("gramme");
		gramme.setAbbreviation("g");
		
		em.persist(gramme);
		
		Unit oz = new Unit();
		oz.setName("oz");
		oz.setAbbreviation("oz");
		
		em.persist(oz);
		
		

		Query query = em.createNamedQuery("findAllUnit");
		@SuppressWarnings("unchecked")
		List<Unit> resultList = query.getResultList();

		for (Unit unit : resultList) {
			System.out.println(unit.getOid() + " " + unit.getName() + " " + unit.getAbbreviation() + "("+unit.getVersion()+") : at "+unit.getCreationDate());
		}

	}

	@BeforeClass
	public static void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(null);

		em = emf.createEntityManager();

		transaction = em.getTransaction();

		transaction.begin();
	}

	@AfterClass
	public static void tearDown() {
		transaction.rollback();
		em.close();
	}
}
