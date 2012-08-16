package org.bm.cookbook.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the INGREDIENT database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findIngredientByName", query = "from Ingredient i where i.name=:name"),
		@NamedQuery(name = "findAllIngredient", query = "from Ingredient i"), })
public class Ingredient extends Model implements Serializable, HasImage {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "INGREDIENT_OID_GENERATOR", sequenceName = "INGREDIENT_DB_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INGREDIENT_OID_GENERATOR")
	@Column(name = "INGREDIENT_DB_ID")
	private int oid;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE", updatable = false)
	private Date creationDate;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	// uni-directional many-to-one association to Image
	@ManyToOne
	@JoinColumn(name = "IMAGE_DB_ID")
	private Image image;

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

	@Override
	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public static Ingredient findByName(String name) {
		Ingredient i = getSingleResult(Ingredient.class, "findIngredientByName", getList("name"), getList(name));
		return i;
	}

	@Override
	public String toString() {
		return name;
	}
}