package org.bm.cookbook.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the RECIPE database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="findAllRecipe", query="from Recipe r")
	
})
public class Recipe extends Model implements Serializable, HasImage, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "RECIPE_OID_GENERATOR", sequenceName = "RECIPE_DB_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECIPE_OID_GENERATOR")
	@Column(name = "RECIPE_DB_ID")
	private int oid;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE", updatable = false)
	private Date creationDate;

	private boolean preheat;

	private String title;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	// bi-directional many-to-one association to Ingredient
	@OneToMany(mappedBy = "recipe")
	private List<RecipeIngredient> recipeIngredients;

	// bi-directional many-to-one association to Cookbook
	@ManyToOne
	@JoinColumn(name = "COOKBOOK_DB_ID")
	private Cookbook cookbook;

	// bi-directional many-to-one association to Step
	@OneToMany(mappedBy = "recipe")
	private List<Step> steps;

	// uni-directional many-to-one association to Image
	@ManyToOne
	@JoinColumn(name = "IMAGE_DB_ID")
	private Image image;

	public Recipe() {
		creationDate = new Date();
		version = 1;
		recipeIngredients = new ArrayList<>();
		steps = new ArrayList<>();
	}

	public int getOid() {
		return this.oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean getPreheat() {
		return this.preheat;
	}

	public void setPreheat(boolean preheat) {
		this.preheat = preheat;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<RecipeIngredient> getRecipeIngredients() {
		return this.recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	public Cookbook getCookbook() {
		return this.cookbook;
	}

	public void setCookbook(Cookbook cookbook) {
		this.cookbook = cookbook;
	}

	public List<Step> getSteps() {
		return this.steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	@Override
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return title;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
				
		return o;
	}

}