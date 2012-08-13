package org.bm.cookbook;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.bm.cookbook.db.model.Image;
import org.bm.cookbook.db.model.Model;
import org.bm.cookbook.db.model.Unit;
import org.junit.AfterClass;

public class Test {

	@org.junit.Test
	public void testData() throws SQLException, IOException {

		Unit gramme = new Unit();
		gramme.setName("gramme");
		gramme.setAbbreviation("g");

		gramme.save();

		Unit oz = new Unit();
		oz.setName("oz");
		oz.setAbbreviation("oz");

		oz.save();
		Model.getEm().getTransaction().commit();
		Query query = Model.getEm().createNamedQuery("findAllUnit");
		@SuppressWarnings("unchecked")
		List<Unit> resultList = query.getResultList();

		for (Unit unit : resultList) {
			System.out.println(unit.getOid() + " " + unit.getName() + " " + unit.getAbbreviation() + "("
					+ unit.getVersion() + ") : at " + unit.getCreationDate());
		}

	}

	@org.junit.Test
	public void testEntities() {
		Collection<Image> images = Image.findAll();
		for (Image image : images) {
			System.out.println(image);
		}
	}

	@AfterClass
	public static void tearDown() {
		if (Model.getEm().getTransaction().isActive()) {
			Model.getEm().getTransaction().rollback();
		}
	}

}
