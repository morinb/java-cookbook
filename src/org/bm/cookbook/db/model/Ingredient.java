package org.bm.cookbook.db.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the INGREDIENT database table.
 * 
 */
@Entity
public class Ingredient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INGREDIENT_OID_GENERATOR", sequenceName="INGREDIENT_DB_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INGREDIENT_OID_GENERATOR")
	@Column(name="INGREDIENT_DB_ID")
	private int oid;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE", updatable=false)
	private Date creationDate;

	private String name;

	private int quantity;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	//uni-directional many-to-one association to Image
	@ManyToOne
	@JoinColumn(name="IMAGE_DB_ID")
	private Image image;

	//bi-directional many-to-one association to Recipe
	@ManyToOne
	@JoinColumn(name="RECIPE_DB_ID")
	private Recipe recipe;

	//uni-directional many-to-one association to Unit
	@ManyToOne
	@JoinColumn(name="UNIT_DB_ID")
	private Unit unit;

	public Ingredient() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Unit getUnit() {
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}