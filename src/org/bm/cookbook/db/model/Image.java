package org.bm.cookbook.db.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the IMAGE database table.
 * 
 */
@Entity
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IMAGE_OID_GENERATOR", sequenceName="IMAGE_DB_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IMAGE_OID_GENERATOR")
	@Column(name="IMAGE_DB_ID")
	private int oid;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE", updatable=false)
	private Date creationDate;

	@Column(name="IMAGE_PATH")
	private String imagePath;

	private String name;

	private String type;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	public Image() {
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

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

}