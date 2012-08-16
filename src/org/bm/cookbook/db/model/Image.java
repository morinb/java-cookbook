package org.bm.cookbook.db.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.bm.cookbook.gui.Messages;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the IMAGE database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findImageByOID", query = "from Image i where i.oid=:oid"),
		@NamedQuery(name = "findImageByName", query = "from Image i where i.name=:name"),
		@NamedQuery(name = "findAllImage", query = "from Image i"), })
public class Image extends Model implements Serializable {
	public static Image nullImage = new Image(Messages.getString("IngredientFrame.nullImage"));

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "IMAGE_OID_GENERATOR", sequenceName = "IMAGE_DB_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_OID_GENERATOR")
	@Column(name = "IMAGE_DB_ID")
	private int oid;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE", updatable = false)
	private Date creationDate;

	@Column(name = "IMAGE_PATH")
	private String imagePath;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATING_DATE")
	private Date updatingDate;

	@Version
	private int version;

	public Image() {
		creationDate = new Date();
		version = 1;
	}

	private Image(String name) {
		this();
		this.name = name;
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

	public static Image findByName(String name) {
		Image i = getSingleResult(Image.class, "findImageByName", getList("name"), getList(name));
		return i;
	}

	@Override
	public String toString() {
		return name;
	}

	public BufferedImage getBufferedImage() {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			bi = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.getGraphics();
			g.setColor(Color.black);
			g.drawRect(1, 1, 150 - 2, 150 - 2);
			g.drawLine(1, 1, 150 - 2, 150 - 2);
			g.drawLine(1, 150 - 2, 150 - 2, 1);
		}
		return bi;
	}

}