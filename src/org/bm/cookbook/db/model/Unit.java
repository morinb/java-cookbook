package org.bm.cookbook.db.model;

import java.io.Serializable;
import java.util.Date;

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
 * The persistent class for the UNIT database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findUnitByOID", query = "from Unit u where u.oid=:oid"),
		@NamedQuery(name = "findUnitByName", query = "from Unit u where u.name=:name"),
		@NamedQuery(name = "findAllUnit", query = "from Unit u"),

})
public class Unit extends Model implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "UNIT_OID_GENERATOR", sequenceName = "UNIT_DB_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNIT_OID_GENERATOR")
	@Column(name = "UNIT_DB_ID")
	private int oid;

	private String abbreviation;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE", updatable = false)
	private Date creationDate;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	public Unit() {
		creationDate = new Date();
		version = 1;
	}

	public int getOid() {
		return this.oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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

	public static Unit findByName(String name) {
		Unit u = (Unit) getSingleResult(Unit.class,"findUnitByName", getList("name"), getList(name));
		return u;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " (" + abbreviation + ")";
	}

}