package org.bm.cookbook.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the RECIPE_STEP database table.
 * 
 */
@Entity
@NamedQueries(value = { @NamedQuery(name = "findStepByOID", query = "from Step u where u.id.oid=:oid"),
		@NamedQuery(name = "findAllStep", query = "from Step u"), })
@Table(name = "RECIPE_STEP")
public class Step extends Model implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StepPK id;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE", updatable = false)
	private Date creationDate;

	private String text;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	// bi-directional many-to-one association to Recipe
	@ManyToOne
	@JoinColumn(name = "RECIPE_DB_ID", insertable = false, updatable = false)
	private Recipe recipe;

	public Step() {
		creationDate = new Date();
		version = 1;
	}

	public StepPK getId() {
		return this.id;
	}

	public void setId(StepPK id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getUpdatingDate() {
		return this.updatingDate;
	}

	public void setUpdatingDate(Date updatingDate) {
		this.updatingDate = updatingDate;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void save() {
		em.persist(this);
	}
	@Override
	public void remove() {
		em.getTransaction().begin();
		em.remove(this);
		em.getTransaction().commit();
	}
	
}