package org.bm.cookbook.db.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the RECIPE_STEP database table.
 * 
 */
@Embeddable
public class StepPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="RECIPE_STEP_DB_ID")
	private int oid;

	@Column(name="RECIPE_DB_ID")
	private int recipeDbId;

	public StepPK() {
	}
	public int getOid() {
		return this.oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getRecipeDbId() {
		return this.recipeDbId;
	}
	public void setRecipeDbId(int recipeDbId) {
		this.recipeDbId = recipeDbId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StepPK)) {
			return false;
		}
		StepPK castOther = (StepPK)other;
		return 
			(this.oid == castOther.oid)
			&& (this.recipeDbId == castOther.recipeDbId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oid;
		hash = hash * prime + this.recipeDbId;
		
		return hash;
	}
}