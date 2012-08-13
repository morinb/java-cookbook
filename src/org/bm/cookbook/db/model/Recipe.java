package org.bm.cookbook.db.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the RECIPE database table.
 * 
 */
@Entity
public class Recipe extends Model implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RECIPE_OID_GENERATOR", sequenceName="RECIPE_DB_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECIPE_OID_GENERATOR")
	@Column(name="RECIPE_DB_ID")
	private int oid;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE", updatable=false)
	private Date creationDate;

	private String preheat;

	private String title;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	//bi-directional many-to-one association to Ingredient
	@OneToMany(mappedBy="recipe")
	private List<Ingredient> ingredients;

	//bi-directional many-to-one association to Cookbook
	@ManyToOne
	@JoinColumn(name="COOKBOOK_DB_ID")
	private Cookbook cookbook;

	//bi-directional many-to-one association to Step
	@OneToMany(mappedBy="recipe")
	private List<Step> steps;

	public Recipe() {
		creationDate = new Date();
		version = 1;
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

	public String getPreheat() {
		return this.preheat;
	}

	public void setPreheat(String preheat) {
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

	public List<Ingredient> getIngredients() {
		return this.ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
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