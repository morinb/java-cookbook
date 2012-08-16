package org.bm.cookbook.db.model;

import java.io.Serializable;
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
 * The persistent class for the COOKBOOK database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findAllCookbook", query = "from Cookbook c") })
public class Cookbook extends Model implements Serializable, HasImage {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "COOKBOOK_OID_GENERATOR", sequenceName = "COOKBOOK_DB_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COOKBOOK_OID_GENERATOR")
	@Column(name = "COOKBOOK_DB_ID")
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

	// bi-directional many-to-one association to Recipe
	@OneToMany(mappedBy = "cookbook")
	private List<Recipe> recipes;

	public Cookbook() {
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

	public List<Recipe> getRecipes() {
		return this.recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

}