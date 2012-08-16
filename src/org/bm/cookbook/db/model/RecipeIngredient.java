package org.bm.cookbook.db.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.bm.cookbook.gui.Messages;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllRecipeIngredient", query = "from RecipeIngredient r"),
		@NamedQuery(name = "findRecipeIngredientByRecipe", query = "from RecipeIngredient ri where ri.recipe.oid=:oid") })
public class RecipeIngredient extends Model {

	@Id
	@SequenceGenerator(name = "RECIPE_INGREDIENT_OID_GENERATOR", sequenceName = "RECIPE_INGREDIENT_DB_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECIPE_INGREDIENT_OID_GENERATOR")
	@Column(name = "RECIPE_INGREDIENT_DB_ID")
	private int oid;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE", updatable = false)
	private Date creationDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATING_DATE", updatable = false)
	private Date updatingDate;

	@Version
	private int version;

	// uni-directional many-to-one association to Ingredient
	@ManyToOne
	@JoinColumn(name = "INGREDIENT_DB_ID")
	private Ingredient ingredient;

	// uni-directional many-to-one association to Unit
	@ManyToOne
	@JoinColumn(name = "UNIT_DB_ID")
	private Unit unit;

	private int quantity;

	// bi-directional many-to-one association to Recipe
	@ManyToOne
	@JoinColumn(name = "RECIPE_DB_ID")
	private Recipe recipe;

	public RecipeIngredient() {
		creationDate = new Date();
		version = 1;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdatingDate() {
		return updatingDate;
	}

	public void setUpdatingDate(Date updatingDate) {
		this.updatingDate = updatingDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return quantity + " " + unit.toString() + Messages.getString("RecipeIngredient.of") + ingredient.toString(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@SuppressWarnings("unchecked")
	public static Collection<RecipeIngredient> findRecipeIngredientByRecipe(int recipeOID) {
		Query query = em.createNamedQuery("findRecipeIngredientByRecipe");
		query.setParameter("oid", recipeOID);
		return query.getResultList();

	}

}
