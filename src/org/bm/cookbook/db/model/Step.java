package org.bm.cookbook.db.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RECIPE_STEP database table.
 * 
 */
@Entity
@Table(name="RECIPE_STEP")
public class Step implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StepPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE", updatable=false)
	private Date creationDate;

	private String text;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	//bi-directional many-to-one association to Recipe
	@ManyToOne
	@JoinColumn(name="RECIPE_DB_ID", insertable=false, updatable=false)
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

}