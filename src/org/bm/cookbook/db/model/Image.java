package org.bm.cookbook.db.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


/**
 * The persistent class for the IMAGE database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="findImageByOID", query="from Image i where i.oid=:oid"),
	@NamedQuery(name="findImageByName", query="from Image i where i.name=:name"),
	@NamedQuery(name="findAllImage", query="from Image i"),
})
public class Image extends Model implements Serializable {
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

	@Override
	public void save() {
		em.getTransaction().begin();
		em.persist(this);
		em.getTransaction().commit();
	}
	@Override
	public void remove() {
		em.getTransaction().begin();
		em.remove(this);
		em.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Image> findAll() {
		List<Image> resultList = em.createNamedQuery("findAllImage").getResultList();
		return resultList;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}