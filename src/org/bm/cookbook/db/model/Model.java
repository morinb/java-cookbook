package org.bm.cookbook.db.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class Model {
	protected static EntityManager em;
	static {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(null);
		em = emf.createEntityManager();
	}

	public static EntityManager getEm() {
		return em;
	}
	
	public abstract void save();

	public void remove() {
		// TODO Auto-generated method stub
		
	}

	
}
