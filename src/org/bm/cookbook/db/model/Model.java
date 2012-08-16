package org.bm.cookbook.db.model;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.bm.cookbook.gui.Messages;
import org.bm.cookbook.utils.CBUtils;

public abstract class Model {
	protected static EntityManager em;
	static {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(null);
		em = emf.createEntityManager();
	}

	public static EntityManager getEm() {
		return em;
	}

	public synchronized void beginTransaction() {
		em.getTransaction().begin();
	}

	public synchronized void commitTransaction() {
		em.getTransaction().commit();
	}

	public final synchronized void save() {
		em.getTransaction().begin();
		em.persist(this);
		em.getTransaction().commit();
	}

	public final synchronized void remove() {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.remove(this);
		em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	protected static <T extends Model> T getSingleResult(Class<T> clazz, String namedQuery, String[] parameterNames,
			Object[] parameterValue) {
		Query query = em.createNamedQuery(namedQuery);
		if (parameterNames.length != parameterValue.length) {
			throw new IllegalArgumentException(Messages.getString("exception.QuerySameParameterNumber"));
		}

		for (int i = 0; i < parameterNames.length; i++) {
			String name = parameterNames[i];
			Object value = parameterValue[i];

			query.setParameter(name, value);
		}

		T m;
		try {
			m = (T) query.getSingleResult();
		} catch (NoResultException e) {
			m = null;
		}
		return m;
	}

	protected static Object[] getList(Object... objects) {
		return objects;
	}

	protected static String[] getList(String... string) {
		return string;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Model> Collection<T> findAll(Class<T> modelClass) {
		String s = "findAll" + modelClass.getSimpleName();
		try {
			Query namedQuery = em.createNamedQuery(s);
			return namedQuery.getResultList();
		} catch (IllegalArgumentException iae) {
			CBUtils.handleException(modelClass.getName(), iae);
		}
		return null;
	}

}
